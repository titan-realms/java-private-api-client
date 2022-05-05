package net.titanrealms.api.client.modules.language;

import net.titanrealms.api.client.utils.api.HttpMethod;
import net.titanrealms.api.client.utils.api.Route;

public class LanguageApiRoutes {
    private static final String BASE_URL = "http://localhost:30001/";

    public static final Route GET_LANGUAGE = Route.create(BASE_URL, HttpMethod.GET, "{serverId}/");
}
