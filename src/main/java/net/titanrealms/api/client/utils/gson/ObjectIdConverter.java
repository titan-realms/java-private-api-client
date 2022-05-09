package net.titanrealms.api.client.utils.gson;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

public class ObjectIdConverter implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {
    public static final Type TYPE = new TypeToken<ObjectId>(){}.getType();

    @Override
    public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ObjectId(json.getAsString());
    }

    @Override
    public JsonElement serialize(ObjectId objectId, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(objectId.toHexString());
    }
}
