package ru.practicum.statclient.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;

public class StatClientBuilder implements StatClient.Builder {
    private Object mapper;
    private Object client;

    @Override
    public <T> StatClient.Builder client(T client) {
        this.client = client;
        return this;
    }

    @Override
    public <T> StatClient.Builder mapper(T mapper) {
        this.mapper = mapper;
        return this;
    }

    @Override
    public StatClient build() {
        if (mapper instanceof ObjectMapper && client instanceof WebClient) {
            return new StatClientImpl((WebClient) client, (ObjectMapper) mapper);
        } else {
            throw new IllegalArgumentException("Sorry, the other client was not ready");
        }
    }
}
