package ru.practicum.geoclient.client;

public class GeoClientBuilder implements GeoClient.Builder {
    private String apikey;
    private String geoServerUrl;

    @Override
    public GeoClient.Builder apikey(String apikey) {
        this.apikey = apikey;
        return this;
    }

    @Override
    public GeoClient.Builder baseUrl(String url) {
        this.geoServerUrl = url;
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
