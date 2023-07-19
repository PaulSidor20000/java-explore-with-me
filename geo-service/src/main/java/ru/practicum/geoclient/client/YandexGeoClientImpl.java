package ru.practicum.geoclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.geoclient.client.model.GeoData;

class YandexGeoClientImpl implements GeoClient {
    private final WebClient client;
    private final ObjectMapper mapper;
    private final String apikey;
    private static final String ARRAY_POINTS = "/response/GeoObjectCollection/featureMember";
    private static final String POINT_NAME = "/GeoObject/metaDataProperty/GeocoderMetaData/text";
    private static final String POINT_COORDINATES = "/GeoObject/Point/pos";

    public YandexGeoClientImpl(String geoServerUrl, String apikey) {
        this.client = WebClient.builder().baseUrl(geoServerUrl).build();
        this.mapper = new ObjectMapper();
        this.apikey = apikey;
    }

    @Override
    public Mono<GeoData> get(String name) {
        return get(apikey, name)
                .map(this::getGeoData);
    }

    @Override
    public Mono<GeoData> get(float lat, float lon) {
        return get(apikey, String.format("%s, %s", lat, lon))
                .map(this::getGeoData);
    }

    public Mono<String> get(String apikey, String geocode) {
        return client.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .queryParam("apikey", apikey)
                                .queryParam("geocode", geocode)
                                .queryParam("format", "json")
                                .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    private GeoData getGeoData(String geoJson) {
        JsonNode root;
        try {
            root = mapper.readTree(geoJson);
        } catch (JsonProcessingException e) {
            throw new UnreadableGeoJsonException(e.getMessage());
        }
        return getGeoData(root);
    }

    private GeoData getGeoData(JsonNode root) {
        if (root == null) {
            return null;
        }
        JsonNode featureMemberRoot = root.at(ARRAY_POINTS);
        String[] coordinates;
        String name;

        if (featureMemberRoot.isArray()) {
            name = featureMemberRoot.get(0).at(POINT_NAME).asText();
            coordinates = featureMemberRoot.get(0).at(POINT_COORDINATES).asText().split(" ");
        } else {
            throw new UnreadableGeoJsonException("Unreadable GeoObject, seems like Api data was changed");
        }
        return GeoData.builder()
                .lon(Float.parseFloat(coordinates[0]))
                .lat(Float.parseFloat(coordinates[1]))
                .name(name)
                .build();
    }

//    private GeoData toGeo(String geoJson) {
//        Root root;
//        try {
//            root = mapper.readValue(geoJson, new TypeReference<>() {
//            });
//        } catch (JsonProcessingException e) {
//            throw new UnreadableGeoJsonException(e.getMessage());
//        }
//        return null;
//    }

}
