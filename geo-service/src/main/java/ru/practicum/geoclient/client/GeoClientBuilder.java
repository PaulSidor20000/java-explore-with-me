package ru.practicum.geoclient.client;

import org.springframework.web.reactive.function.client.WebClient;

public class GeoClientBuilder implements GeoClient.Builder {
    private Object client;
    private Object mapper;
    private String apikey;
    private String geoServerUrl;

    @Override
    public <T> GeoClient.Builder client(T client) {
        this.client = client;
        return this;
    }

    @Override
    public <T> GeoClient.Builder mapper(T mapper) {
        this.mapper = mapper;
        return this;
    }

    @Override
    public GeoClient.Builder apikey(String apikey) {
        this.apikey = apikey;
        return this;
    }

    @Override
    public GeoClient.Builder baseUrl(String baseUrl) {
        this.geoServerUrl = baseUrl;
        return this;
    }

    @Override
    public GeoClient build() {
        if (geoServerUrl.contains("yandex")) {
            return new YandexGeoClientImpl(geoServerUrl, apikey);
        } else {
            throw new IllegalArgumentException("Sorry, the other client was not ready");
        }
    }
}
