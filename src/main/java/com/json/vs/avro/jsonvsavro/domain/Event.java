package com.json.vs.avro.jsonvsavro.domain;

import java.util.UUID;

public class Event<T> {
    private String id;
    private T payload;

    public Event() {
    }

    public Event(String id, T payload) {
        this.id = id;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public Event<T> setId(String id) {
        this.id = id;
        return this;
    }

    public T getPayload() {
        return payload;
    }

    public Event<T> setPayload(T payload) {
        this.payload = payload;
        return this;
    }
}
