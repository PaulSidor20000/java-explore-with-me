package ru.practicum.geoclient.client.model.apiresponse; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class BoundedBy{
    @JsonProperty("Envelope") 
    public Envelope envelope;
}
