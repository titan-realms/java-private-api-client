package net.titanrealms.api.client;

import net.titanrealms.api.client.modules.playerdata.PlayerDataApi;

public class Test {

    public static void main(String... args) {
        PlayerDataApi api = new PlayerDataApi();

        api.retrievePlayerDataByUsername("7352")
                .thenAccept(System.out::println).join();
    }
}
