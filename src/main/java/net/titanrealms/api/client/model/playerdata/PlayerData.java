package net.titanrealms.api.client.model.playerdata;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.UUID;

public record PlayerData(UUID id, Instant lastUpdate, String username) {
    public static final Type TYPE = new TypeToken<PlayerData>(){}.getType();
}
