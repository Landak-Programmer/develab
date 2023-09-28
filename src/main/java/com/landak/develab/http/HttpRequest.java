package com.landak.develab.http;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Data
public class HttpRequest {

    private final String url;
    private final HttpMethod method;
    private Map<String, String> header = new HashMap<>();
    private Object body;

    public HttpRequest(final String url, final HttpMethod method) {
        this.url = url;
        this.method = method;
        header.put(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    }

}
