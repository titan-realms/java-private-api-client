package net.titanrealms.api.client.utils.api;

import com.google.gson.Gson;
import net.titanrealms.api.client.model.spring.Pageable;
import net.titanrealms.api.client.model.spring.Sort;
import net.titanrealms.api.client.utils.ImmutablePair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashSet;
import java.util.Set;

public class CompiledRoute {
    private final @NotNull Gson gson;
    private final @NotNull Set<ImmutablePair<String, String>> parameters = new HashSet<>();
    private final @NotNull String route;
    private final @NotNull HttpMethod method;
    private HttpRequest.BodyPublisher bodyPublisher;

    public CompiledRoute(@NotNull Gson gson, @NotNull HttpMethod method, @NotNull String route) {
        this.gson = gson;
        this.method = method;
        this.route = route;
    }

    public @NotNull CompiledRoute addParameter(@NotNull String key, @NotNull String value) {
        this.parameters.add(new ImmutablePair<>(key, value));
        return this;
    }

    public @NotNull CompiledRoute withPageable(@NotNull Pageable pageable) {
        return this.addParameter("page", String.valueOf(pageable.page()))
                .addParameter("size", String.valueOf(pageable.size()));
    }

    public @NotNull CompiledRoute withSort(@NotNull Sort sort) {
        return this.addParameter("sort", sort.property() + "." + sort.direction().toString().toLowerCase());
    }

    public @NotNull CompiledRoute withBodyPublisher(HttpRequest.BodyPublisher bodyPublisher) {
        if (!this.method.isBodySupport())
            throw new UnsupportedOperationException("Cannot set body for API method type " + this.method);

        this.bodyPublisher = bodyPublisher;
        return this;
    }

    public @NotNull CompiledRoute withBody(Object serializableObject) {
        if (!this.method.isBodySupport())
            throw new UnsupportedOperationException("Cannot set body for API method type " + this.method);

        if (serializableObject == null)
            this.bodyPublisher = HttpRequest.BodyPublishers.noBody();
        else
            this.bodyPublisher = HttpRequest.BodyPublishers.ofString(this.gson.toJson(serializableObject));

        return this;
    }

    public @NotNull String buildFullRoute() {
        if (this.parameters.isEmpty())
            return this.route;

        StringBuilder fullRoute = new StringBuilder(this.route);

        boolean first = true; // todo -> i dont like this but cba to do better rn. Defo change later.
        for (ImmutablePair<String, String> pair : this.parameters) {
            if (first) {
                fullRoute.append('?');
                first = false;
            } else {
                fullRoute.append('&');
            }

            fullRoute.append(pair.key())
                    .append('=')
                    .append(pair.value());

        }
        return fullRoute.toString();
    }

    public HttpRequest toRequest() {
        HttpRequest.Builder request =
                switch (this.method) {
                    case GET -> HttpRequest.newBuilder(URI.create(this.buildFullRoute())).GET();
                    case DELETE -> HttpRequest.newBuilder(URI.create(this.buildFullRoute())).DELETE();
                    case POST -> HttpRequest.newBuilder(URI.create(this.buildFullRoute())).POST(this.getBodyPublisherOrThrow());
                    case PUT -> HttpRequest.newBuilder(URI.create(this.buildFullRoute())).PUT(this.getBodyPublisherOrThrow());
                };
        request.header("Content-Type", "application/json");
        return request.build();
    }

    public @NotNull HttpMethod getMethod() {
        return this.method;
    }

    public @NotNull String getRoute() {
        return this.route;
    }

    public @Nullable HttpRequest.BodyPublisher getBodyPublisher() {
        return this.bodyPublisher;
    }

    private @NotNull HttpRequest.BodyPublisher getBodyPublisherOrThrow() throws IllegalArgumentException {
        if (this.bodyPublisher != null)
            return bodyPublisher;
        throw new IllegalArgumentException("Body publisher is null for required method (" + this.method + ")");
    }
}