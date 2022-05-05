package net.titanrealms.api.client.utils.api;

import com.google.gson.Gson;

public record Route(String baseUrl, HttpMethod httpMethod,
                    String route, boolean pageable) {

    public static Route create(String baseUrl, HttpMethod httpMethod, String route, boolean pageable) {
        return new Route(baseUrl, httpMethod, route, pageable);
    }

    public static Route create(String baseUrl, HttpMethod httpMethod, String route) {
        return new Route(baseUrl, httpMethod, route, false);
    }

    public CompiledRoute compile(Gson gson, Object... params) {
        String[] args = this.route.split("/");
        StringBuilder builder = new StringBuilder(this.baseUrl);
        int paramsIndex = 0;
        for (String arg : args) {
            if (arg.charAt(0) == '{' && arg.charAt(arg.length() - 1) == '}') {
                builder.append(params[paramsIndex].toString()).append("/");
                paramsIndex++;
            } else {
                builder.append(arg).append("/");
            }
        }
        return new CompiledRoute(gson, this.httpMethod, builder.toString());
    }
}
