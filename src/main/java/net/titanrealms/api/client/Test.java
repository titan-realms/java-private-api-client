package net.titanrealms.api.client;

import net.titanrealms.api.client.model.playerdata.PlayerData;
import net.titanrealms.api.client.model.server.ServerType;
import net.titanrealms.api.client.model.spring.Pageable;

import java.time.Instant;
import java.util.UUID;

public class Test {

    public static void main(String... args) {
        TitanApi titanApi = new TitanApi();

        titanApi.getPlayerDataApi().updatePlayerData(new PlayerData(UUID.fromString("8d36737e-1c0a-4a71-87de-9906f577845e"), Instant.now(), "Expectational")).join();
        titanApi.getPlayerDataApi().searchPlayerDataByUsername("I", Pageable.of(0, 10))
                .thenAccept(System.out::println)
                .join();
    }
}
