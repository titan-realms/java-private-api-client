package net.titanrealms.api.client;

import net.titanrealms.api.client.model.punishment.ReversalInfo;
import net.titanrealms.api.client.modules.punishments.PunishmentApi;

import java.time.Instant;
import java.util.UUID;

public class Test {

    public static void main(String... args) {
        PunishmentApi api = new PunishmentApi();

        api.reversePunishment("6272a5e8d4eef211654dc5cc", new ReversalInfo(UUID.randomUUID(), Instant.now(), "this is a shitty reason rofl"))
                .thenAccept(System.out::println)
                .join();
    }
}
