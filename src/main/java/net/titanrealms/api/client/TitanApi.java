package net.titanrealms.api.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.titanrealms.api.client.modules.language.LanguageApi;
import net.titanrealms.api.client.modules.playerdata.PlayerDataApi;
import net.titanrealms.api.client.modules.punishments.PunishmentApi;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.net.http.HttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TitanApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(TitanApi.class);
    private final HttpClient httpClient = HttpClient.newBuilder().executor(this.createExecutor()).build();
    private final Jedis jedis = new Jedis();

    private final @NotNull LanguageApi languageApi = new LanguageApi(this.httpClient, this.jedis);
    private final @NotNull PlayerDataApi playerDataApi = new PlayerDataApi(this.httpClient);
    private final @NotNull PunishmentApi punishmentApi = new PunishmentApi(this.httpClient, this.jedis);

    public @NotNull LanguageApi getLanguageApi() {
        return this.languageApi;
    }

    public @NotNull PlayerDataApi getPlayerDataApi() {
        return this.playerDataApi;
    }

    public @NotNull PunishmentApi getPunishmentApi() {
        return this.punishmentApi;
    }

    private ExecutorService createExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("TitanApiClient-%d")
                .setUncaughtExceptionHandler((thread, ex) -> {
                    LOGGER.error("Error with TitanApi request: ", ex);
                })
                .build();
        return new ThreadPoolExecutor(3, 50, 2, TimeUnit.MINUTES, new SynchronousQueue<>(), threadFactory);
    }
}
