package net.titanrealms.api.client.utils.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.titanrealms.api.client.model.spring.Page;

import java.lang.reflect.Type;
import java.util.List;

public class PageConverter<T> implements JsonSerializer<Page<T>>, JsonDeserializer<Page<T>> {
    private final Type listType;

    public PageConverter(Type listType) {
        this.listType = listType;
    }

    @Override
    public Page<T> deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();
        List<T> content = context.deserialize(json.get("content"), listType);
        boolean last = json.get("last").getAsBoolean();
        boolean first = json.get("first").getAsBoolean();
        long totalPages = json.get("totalPages").getAsLong();
        long totalElements = json.get("totalElements").getAsLong();
        int size = json.get("size").getAsInt();
        int number = json.get("number").getAsInt();
        return new Page<>(content, last, first, totalPages, totalElements, size, number);
    }

    @Override
    public JsonElement serialize(Page<T> page, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.add("content", context.serialize(page.content()));
        json.addProperty("last", page.last());
        json.addProperty("first", page.first());
        json.addProperty("totalPages", page.totalPages());
        json.addProperty("totalElements", page.totalElements());
        json.addProperty("size", page.size());
        json.addProperty("number", page.number());
        return json;
    }
}
