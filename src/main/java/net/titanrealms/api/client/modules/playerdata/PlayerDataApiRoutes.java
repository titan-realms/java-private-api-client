package net.titanrealms.api.client.modules.playerdata;

import net.titanrealms.api.client.utils.api.HttpMethod;
import net.titanrealms.api.client.utils.api.Route;

public class PlayerDataApiRoutes {
    private static final String BASE_URL = "http://localhost:30003/";

    public static final Route UPDATE_PLAYER = Route.create(BASE_URL, HttpMethod.PUT, "playerData/");
    public static final Route GET_PLAYER = Route.create(BASE_URL, HttpMethod.GET, "playerData/{playerId}/");
    public static final Route SEARCH_PLAYER = Route.create(BASE_URL, HttpMethod.GET, "playerData/search/{username}/");
}
