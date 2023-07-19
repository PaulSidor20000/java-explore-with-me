package ru.practicum.geoclient.client.model.apiresponse; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class GeoObject{
    public MetaDataProperty metaDataProperty;
    public String name;
    public BoundedBy boundedBy;
    @JsonProperty("Point") 
    public Point point;
}
