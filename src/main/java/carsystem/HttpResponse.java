package carsystem;

import com.google.gson.JsonElement;

public class HttpResponse {

    private final String response;
    private final int statusCode;

    public HttpResponse(int statusCode, String response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
