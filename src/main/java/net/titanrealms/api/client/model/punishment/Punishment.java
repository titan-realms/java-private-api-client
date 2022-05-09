package net.titanrealms.api.client.model.punishment;

import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Punishment {
    public static final Type TYPE = new TypeToken<Punishment>(){}.getType();

    private final ObjectId id;

    private final PunishmentType punishmentType;

    private final Instant expiry;

    private final UUID punisher;
    private final UUID target;

    private final String reason;

    private final boolean notified;
    private final ReversalInfo reversalInfo;

    public Punishment(PunishmentType punishmentType, Instant expiry, UUID punisher, UUID target, String reason, boolean notified) {
        this.punishmentType = punishmentType;
        this.id = null;
        this.expiry = expiry;
        this.punisher = punisher;
        this.target = target;
        this.reason = reason;
        this.notified = notified;
        this.reversalInfo = null;
    }

    public @Nullable Date getTimestamp() {
        return this.id == null ? null : this.id.getDate();
    }
}
