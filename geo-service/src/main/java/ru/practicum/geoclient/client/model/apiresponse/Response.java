package ru.practicum.geoclient.client.model.apiresponse; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Response{
    @JsonProperty("GeoObjectCollection") 
    public GeoObjectCollection geoObjectCollection;
}
