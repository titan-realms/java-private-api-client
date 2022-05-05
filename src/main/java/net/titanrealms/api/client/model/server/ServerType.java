package net.titanrealms.api.client.model.server;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum ServerType {

    GLOBAL("global"), // used for lang classification
    LOBBY("lobby"),
    PROXY("proxy"),
    PLAYER_CELL("player-cell");

    @NotNull
    private final String identifier;

    ServerType(@NotNull String identifier) {
        this.identifier = identifier;
    }

    @NotNull
    public String getIdentifier() {
        return this.identifier;
    }

    @Nullable
    public ServerType fromId(String identifier) {
        for (ServerType serverType : ServerType.values())
            if (serverType.getIdentifier().equals(identifier))
                return serverType;
        return null;
    }
}