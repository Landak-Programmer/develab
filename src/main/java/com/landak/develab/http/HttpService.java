package com.landak.develab.http;

public interface HttpService {

    boolean execute(HttpRequest request);

    <R> R execute(HttpRequest request, Class<R> rClazz);

}
