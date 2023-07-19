package ru.practicum.geoclient.client.model.apiresponse; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class GeocoderResponseMetaData{
    @JsonProperty("Point") 
    public Point point;
    public String request;
    public String results;
    public String found;
}
