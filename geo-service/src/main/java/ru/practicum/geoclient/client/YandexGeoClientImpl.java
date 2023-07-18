package ru.practicum.geoclient.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

class YandexGeoClientImpl implements GeoClient {
    private final WebClient client;
    private final ObjectMapper mapper;

    // https://geocode-maps.yandex.ru/1.x
    //  ? apikey=<string>
    //  & geocode=<string>

//    @Value("${geo.apikey}")
    private final String apikey;

//    @Value("${geo.server.url}")
    private final String geoServerUrl;

    public YandexGeoClientImpl(String geoServerUrl, String apikey) {
        this.client = WebClient.builder().baseUrl(geoServerUrl).build();
        this.mapper = new ObjectMapper();
        this.geoServerUrl = geoServerUrl;
        this.apikey = apikey;
    }

    @Override
    public Flux<String> get(String name) {
        return get(geoServerUrl, apikey, name);
    }

    @Override
    public Flux<String> get(float lat, float lon) {
        return get(geoServerUrl, apikey, String.format("%s, %s", lat, lon));
    }

    public Flux<String> get(String geoServerUrl, String apikey, String geocode) {
        return client.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(geoServerUrl)
                                .queryParam("apikey", apikey)
                                .queryParam("geocode", geocode)
                                .build())
                .retrieve()
                .bodyToFlux(String.class);
    }

}
