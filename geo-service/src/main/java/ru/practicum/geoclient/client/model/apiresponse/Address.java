package ru.practicum.geoclient.client.model.apiresponse; 
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
public class Address{
    public String formatted;
    @JsonProperty("Components") 
    public ArrayList<Component> components;
}
