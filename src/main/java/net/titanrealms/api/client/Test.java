package net.titanrealms.api.client;

import net.titanrealms.api.client.model.punishment.Punishment;
import net.titanrealms.api.client.model.spring.Pageable;

import java.util.UUID;

public class Test {

    public static void main(String... args) {
        TitanApi titanApi = new TitanApi();


        titanApi.getPunishmentApi().retrievePlayerPunishments(UUID.fromString("8d36737e-1c0a-4a71-87de-9906f577845e"), Pageable.of(0, 100), null)
                .thenAccept(result -> {
                    for (Punishment punishment : result.content()) {
                        System.out.println(punishment.getId() + " : " + punishment.getId().getDate());
                    }
                })
                .join();
    }
}
