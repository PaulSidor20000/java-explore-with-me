package ru.practicum.geoclient.client;

import reactor.core.publisher.Flux;

public interface GeoClient {

    Flux<String> get(String name);

    Flux<String> get(float lon, float lat);

    static GeoClient.Builder builder() {
        return new GeoClientBuilder();
    }

    interface Builder {

        <T> Builder client(T client);

        <T> Builder mapper(T mapper);

        Builder apikey(String apikey);

        Builder baseUrl(String baseUrl);

        GeoClient build();
    }

}

