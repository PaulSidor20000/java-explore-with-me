package ru.practicum.geoclient.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeoData {

    private String name;

    private Float lat;

    private Float lon;

}
