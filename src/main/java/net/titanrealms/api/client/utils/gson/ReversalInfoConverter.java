package net.titanrealms.api.client.utils.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import net.titanrealms.api.client.model.punishment.ReversalInfo;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.UUID;

public class ReversalInfoConverter implements JsonSerializer<ReversalInfo>, JsonDeserializer<ReversalInfo> {
    public static final Type TYPE = new TypeToken<ReversalInfo>(){}.getType();

    @Override
    public ReversalInfo deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        UUID reverser = context.deserialize(json.get("reverser"), new TypeToken<UUID>(){}.getType());
        Instant timestamp = context.deserialize(json.get("timestamp"), InstantConverter.TYPE);
        String reason = json.get("reason").getAsString();

        return new ReversalInfo(reverser, timestamp, reason);
    }

    @Override
    public JsonElement serialize(ReversalInfo reversalInfo, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.add("reverser", context.serialize(reversalInfo.reverser()));
        json.add("timestamp", context.serialize(reversalInfo.timestamp()));
        json.addProperty("reason", reversalInfo.reason());
        return json;
    }
}
