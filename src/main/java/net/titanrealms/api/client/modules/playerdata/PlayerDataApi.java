package net.titanrealms.api.client.modules.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.titanrealms.api.client.model.playerdata.PlayerData;
import net.titanrealms.api.client.utils.gson.InstantConverter;
import net.titanrealms.api.client.utils.gson.PlayerDataConverter;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerDataApi {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(InstantConverter.TYPE, new InstantConverter())
            .registerTypeAdapter(PlayerData.TYPE, new PlayerDataConverter())
            .create();

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public CompletableFuture<PlayerData> updatePlayerData(PlayerData playerData) {
        return this.httpClient.sendAsync(PlayerDataApiRoutes.UPDATE_PLAYER.compile(GSON).withBody(playerData).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PlayerData.TYPE));
    }

    public CompletableFuture<PlayerData> retrievePlayerDataById(UUID id) {
        return this.httpClient.sendAsync(PlayerDataApiRoutes.GET_PLAYER.compile(GSON, id.toString()).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PlayerData.TYPE));
    }

    public CompletableFuture<PlayerData> retrievePlayerDataByUsername(String username) {
        return this.httpClient.sendAsync(PlayerDataApiRoutes.SEARCH_PLAYER.compile(GSON, username).toRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> GSON.fromJson(response.body(), PlayerData.TYPE));
    }

}
