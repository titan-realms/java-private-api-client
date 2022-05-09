package net.titanrealms.api.client.modules.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.titanrealms.api.client.model.playerdata.PlayerData;
import net.titanrealms.api.client.model.spring.Page;
import net.titanrealms.api.client.model.spring.Pageable;
import net.titanrealms.api.client.utils.gson.InstantConverter;
import net.titanrealms.api.client.utils.gson.PageConverter;
import net.titanrealms.api.client.utils.gson.PlayerDataConverter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerDataApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDataApi.class);
    private static final Type PLAYER_DATA_PAGE_TYPE = new TypeToken<Page<PlayerData>>() {}.getType();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(InstantConverter.TYPE, new InstantConverter())
            .registerTypeAdapter(PlayerData.TYPE, new PlayerDataConverter())
            .registerTypeAdapter(PLAYER_DATA_PAGE_TYPE, new PageConverter<PlayerData>(new TypeToken<ArrayList<PlayerData>>() {
            }.getType()))
            .create();

    private final @NotNull HttpClient httpClient;

    public PlayerDataApi(@NotNull HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CompletableFuture<PlayerData> updatePlayerData(@NotNull PlayerData playerData) {
        return this.httpClient.sendAsync(PlayerDataApiRoutes.UPDATE_PLAYER.compile(GSON).withBody(playerData).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PlayerData.TYPE));
    }

    public CompletableFuture<PlayerData> retrievePlayerDataById(@NotNull UUID id) {
        return this.httpClient.sendAsync(PlayerDataApiRoutes.GET_PLAYER.compile(GSON, id.toString()).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PlayerData.TYPE));
    }

    public CompletableFuture<PlayerData> retrievePlayerDataByUsername(@NotNull String username) {
        return this.httpClient.sendAsync(PlayerDataApiRoutes.FIND_PLAYER_BY_USERNAME.compile(GSON, username).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PlayerData.TYPE));
    }

    public CompletableFuture<Page<PlayerData>> searchPlayerDataByUsername(@NotNull String username, @NotNull Pageable pageable) {
        if (username.isEmpty()) return CompletableFuture.completedFuture(new Page<>(List.of(), true, true, -1, -1, 0, 0));

        return this.httpClient.sendAsync(PlayerDataApiRoutes.SEARCH_PLAYER_BY_USERNAME.compile(GSON, username).withPageable(pageable).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PLAYER_DATA_PAGE_TYPE));
    }
}
