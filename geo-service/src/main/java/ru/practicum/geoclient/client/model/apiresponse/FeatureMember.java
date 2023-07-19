package ru.practicum.geoclient.client.model.apiresponse; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class FeatureMember{
    @JsonProperty("GeoObject") 
    public GeoObject geoObject;
}
