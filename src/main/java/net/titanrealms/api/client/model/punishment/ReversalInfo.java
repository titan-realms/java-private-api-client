package net.titanrealms.api.client.model.punishment;

import java.time.Instant;
import java.util.UUID;

public record ReversalInfo(UUID reverser, Instant timestamp, String reason) {
}
