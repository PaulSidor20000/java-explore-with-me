package ru.practicum.statclient.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;

public class StatClientBuilder implements StatClient.Builder {
    private ObjectMapper mapper;
    private WebClient client;

    @Override
    public StatClient.Builder client(WebClient client) {
        this.client = client;
        return this;
    }

    @Override
    public StatClient.Builder mapper(ObjectMapper mapper) {
        this.mapper = mapper;
        return this;
    }

    @Override
    public StatClient build() {
        return new StatClientImpl(client, mapper);
    }
}
