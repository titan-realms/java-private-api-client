package net.titanrealms.api.client.modules.punishments;

import net.titanrealms.api.client.utils.api.HttpMethod;
import net.titanrealms.api.client.utils.api.Route;

public class PunishmentApiRoutes {
    private static final String BASE_URL = "http://localhost:30002/";

    public static final Route CREATE_PUNISHMENT = Route.create(BASE_URL, HttpMethod.POST, "punishment/");
    public static final Route REVERSE_PUNISHMENT = Route.create(BASE_URL, HttpMethod.PUT, "punishment/{punishmentId}/reverse");

    public static class Player {
        public static final Route GET_PUNISHMENTS = Route.create(BASE_URL, HttpMethod.GET, "player/{uuid}/", true);
        public static final Route GET_NON_NOTIFIED_PUNISHMENTS = Route.create(BASE_URL, HttpMethod.GET, "player/{uuid}/nonNotified/");
        public static final Route GET_ACTIVE_BAN = Route.create(BASE_URL, HttpMethod.GET, "player/{uuid}/activeBan/");
    }
}
