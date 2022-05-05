package net.titanrealms.api.client.utils.api;

public enum HttpMethod {
    GET(false),
    DELETE(false),
    POST(true),
    PUT(true);

    private final boolean bodySupport;

    HttpMethod(boolean bodySupport) {
        this.bodySupport = bodySupport;
    }

    public boolean isBodySupport() {
        return this.bodySupport;
    }
}
