package ru.practicum.geoclient.client;

import reactor.core.publisher.Mono;
import ru.practicum.geoclient.client.model.GeoData;

public interface GeoClient {

    Mono<GeoData> get(String name);

    Mono<GeoData> get(float lon, float lat);

    static GeoClient.Builder builder() {
        return new GeoClientBuilder();
    }

    interface Builder {

        Builder apikey(String apikey);

        Builder baseUrl(String baseUrl);

        GeoClient build();
    }

}

