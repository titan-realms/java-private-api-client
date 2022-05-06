package net.titanrealms.api.client.utils.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.titanrealms.api.client.model.playerdata.PlayerData;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.UUID;

public class PlayerDataConverter implements JsonSerializer<PlayerData>, JsonDeserializer<PlayerData> {

    @Override
    public PlayerData deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        UUID uuid = UUID.fromString(json.get("id").getAsString());
        Instant lastUpdate = context.deserialize(json.get("lastUpdate"), InstantConverter.TYPE);
        String username = json.get("username").getAsString();

        return new PlayerData(uuid, lastUpdate, username);
    }

    @Override
    public JsonElement serialize(PlayerData playerData, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        json.addProperty("id", playerData.id().toString());
        json.add("lastUpdate", context.serialize(playerData.lastUpdate()));
        json.addProperty("username", playerData.username());
        return json;
    }
}
