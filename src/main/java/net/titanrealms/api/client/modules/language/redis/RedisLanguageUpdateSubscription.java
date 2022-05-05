package net.titanrealms.api.client.modules.language.redis;

import com.google.gson.Gson;
import net.titanrealms.api.client.model.language.Language;
import net.titanrealms.api.client.modules.language.LanguageApi;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.function.Consumer;

public class RedisLanguageUpdateSubscription extends JedisPubSub {
    private final Consumer<Map<Language, Map<String, String>>> resultConsumer;
    private final Gson gson;


    public RedisLanguageUpdateSubscription(Consumer<Map<Language, Map<String, String>>> resultConsumer, Gson gson) {
        this.resultConsumer = resultConsumer;
        this.gson = gson;
    }

    @Override
    public void onMessage(String channel, String message) {
        Map<Language, Map<String, String>> data = this.gson.fromJson(message, LanguageApi.LANGUAGE_PACK_TYPE);
        this.resultConsumer.accept(data);
    }
}
