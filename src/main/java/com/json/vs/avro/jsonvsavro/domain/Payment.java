package com.json.vs.avro.jsonvsavro.domain;

import java.math.BigDecimal;

public class Payment {
    private String description;
    private BigDecimal value;

    public Payment() {
    }

    public Payment(String description, BigDecimal value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public Payment setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Payment setValue(BigDecimal value) {
        this.value = value;
        return this;
    }
}
