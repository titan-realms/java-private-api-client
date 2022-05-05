package net.titanrealms.api.client.model.punishment;

import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Punishment {
    public static final Type TYPE = new TypeToken<Punishment>(){}.getType();

    private final String id;

    private final Instant timestamp;
    private final Instant expiry;

    private final UUID punisher;
    private final UUID target;

    private final String reason;

    private final boolean notified;
    private final ReversalInfo reversalInfo;

    public Punishment(Instant timestamp, Instant expiry, UUID punisher, UUID target, String reason, boolean notified) {
        this.id = null;
        this.timestamp = timestamp;
        this.expiry = expiry;
        this.punisher = punisher;
        this.target = target;
        this.reason = reason;
        this.notified = notified;
        this.reversalInfo = null;
    }
}
