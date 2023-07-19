package ru.practicum.geoclient.client.model.apiresponse; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class MetaDataProperty{
    @JsonProperty("GeocoderResponseMetaData") 
    public GeocoderResponseMetaData geocoderResponseMetaData;
    @JsonProperty("GeocoderMetaData") 
    public GeocoderMetaData geocoderMetaData;
}
