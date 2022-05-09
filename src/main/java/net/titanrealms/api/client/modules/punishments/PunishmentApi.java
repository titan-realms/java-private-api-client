package net.titanrealms.api.client.modules.punishments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import net.titanrealms.api.client.model.punishment.Punishment;
import net.titanrealms.api.client.model.punishment.ReversalInfo;
import net.titanrealms.api.client.model.spring.Page;
import net.titanrealms.api.client.model.spring.Pageable;
import net.titanrealms.api.client.model.spring.Sort;
import net.titanrealms.api.client.modules.punishments.redis.RedisPunishmentSubscription;
import net.titanrealms.api.client.utils.gson.InstantConverter;
import net.titanrealms.api.client.utils.gson.PageConverter;
import net.titanrealms.api.client.utils.gson.ReversalInfoConverter;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PunishmentApi {
    private static final Type PUNISHMENT_PAGE_TYPE = new TypeToken<Page<Punishment>>() {}.getType();
    private static final Type PUNISHMENT_LIST_TYPE = new TypeToken<ArrayList<Punishment>>() {}.getType();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(InstantConverter.TYPE, new InstantConverter())
            .registerTypeAdapter(ReversalInfoConverter.TYPE, new ReversalInfoConverter())
            .registerTypeAdapter(PUNISHMENT_PAGE_TYPE, new PageConverter<Punishment>(new TypeToken<ArrayList<Punishment>>(){}.getType()))
            .create();

    private final @NotNull HttpClient httpClient;
    private final @NotNull Jedis jedis;

    public PunishmentApi(@NotNull HttpClient httpClient, @NotNull Jedis jedis) {
        this.httpClient = httpClient;
        this.jedis = jedis;
    }

    public @NotNull CompletableFuture<Punishment> createPunishment(@NotNull Punishment punishment) {
        return this.httpClient.sendAsync(PunishmentApiRoutes.CREATE_PUNISHMENT.compile(GSON).withBody(punishment).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), Punishment.TYPE));
    }

    public @NotNull CompletableFuture<Punishment> reversePunishment(@NotNull String punishmentId, @NotNull ReversalInfo reversalInfo) {
        return this.httpClient.sendAsync(PunishmentApiRoutes.REVERSE_PUNISHMENT.compile(GSON, punishmentId).withBody(reversalInfo).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), Punishment.TYPE));
    }

    @SneakyThrows
    public @NotNull CompletableFuture<Page<Punishment>> retrievePlayerPunishments(@NotNull UUID target, @NotNull Pageable pageable, @NotNull Sort sort) {
        return this.httpClient.sendAsync(PunishmentApiRoutes.Player.GET_PUNISHMENTS.compile(GSON, target).withPageable(pageable).withSort(sort).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PUNISHMENT_PAGE_TYPE));
    }

    public @NotNull CompletableFuture<List<Punishment>> retrievePlayerNonNotifiedPunishments(@NotNull UUID target) {
        return this.httpClient.sendAsync(PunishmentApiRoutes.Player.GET_NON_NOTIFIED_PUNISHMENTS.compile(GSON, target).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PUNISHMENT_LIST_TYPE));
    }

    public CompletableFuture<Punishment> retrievePlayerActiveBan(@NotNull UUID target) {
        return this.httpClient.sendAsync(PunishmentApiRoutes.Player.GET_ACTIVE_BAN.compile(GSON, target).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), Punishment.TYPE));
    }

    public void subscribeToPunishments(Consumer<Punishment> consumer) {
        this.jedis.subscribe(new RedisPunishmentSubscription(consumer, GSON), "punishment_api_punishments");
    }

    public void subscribeToPunishmentReversals(Consumer<Punishment> consumer) {
        this.jedis.subscribe(new RedisPunishmentSubscription(consumer, GSON), "punishment_api_reversals");
    }
}
