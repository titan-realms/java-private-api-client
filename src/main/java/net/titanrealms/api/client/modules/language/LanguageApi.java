package net.titanrealms.api.client.modules.language;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.titanrealms.api.client.model.language.Language;
import net.titanrealms.api.client.model.server.ServerType;
import net.titanrealms.api.client.modules.language.redis.RedisLanguageUpdateSubscription;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LanguageApi {
    public static final Type LANGUAGE_PACK_TYPE = new TypeToken<EnumMap<Language, HashMap<String, String>>>(){}.getType();

    private static final Gson GSON = new Gson();

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Jedis jedis = new Jedis();

    public CompletableFuture<Map<Language, Map<String, String>>> retrieveLanguagePack(ServerType serverType) {
        return this.httpClient.sendAsync(LanguageApiRoutes.GET_LANGUAGE.compile(GSON, serverType.toString()).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), LANGUAGE_PACK_TYPE));
    }

    public void subscribeToLanguageUpdates(ServerType serverType, Consumer<Map<Language, Map<String, String>>> consumer) {
        this.jedis.subscribe(new RedisLanguageUpdateSubscription(consumer, GSON), "language_api_change_" + serverType.toString());
    }
}
