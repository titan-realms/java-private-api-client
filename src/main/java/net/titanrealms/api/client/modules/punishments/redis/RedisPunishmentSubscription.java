package net.titanrealms.api.client.modules.punishments.redis;

import com.google.gson.Gson;
import net.titanrealms.api.client.model.punishment.Punishment;
import redis.clients.jedis.JedisPubSub;

import java.util.function.Consumer;

public class RedisPunishmentSubscription extends JedisPubSub {
    private final Consumer<Punishment> resultConsumer;
    private final Gson gson;

    public RedisPunishmentSubscription(Consumer<Punishment> resultConsumer, Gson gson) {
        this.resultConsumer = resultConsumer;
        this.gson = gson;
    }

    @Override
    public void onMessage(String channel, String message) {
        Punishment data = this.gson.fromJson(message, Punishment.TYPE);
        this.resultConsumer.accept(data);
    }
}
