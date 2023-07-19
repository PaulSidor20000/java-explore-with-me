package ru.practicum.geoclient.client.model.apiresponse; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class AddressDetails{
    @JsonProperty("Address") 
    public String address;
}
