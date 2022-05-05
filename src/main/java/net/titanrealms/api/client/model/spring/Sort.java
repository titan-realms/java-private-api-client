package net.titanrealms.api.client.model.spring;

import org.jetbrains.annotations.NotNull;

public record Sort(@NotNull Sort.Direction direction,
                   @NotNull String property) {

    public Sort(@NotNull Direction direction, @NotNull String property) {
        this.direction = direction;
        this.property = property;
    }

    public static Sort by(@NotNull Direction direction, @NotNull String property) {
        return new Sort(direction, property);
    }

    public enum Direction {
        ASC,
        DESC
    }
}
