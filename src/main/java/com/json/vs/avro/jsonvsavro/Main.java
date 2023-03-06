package com.json.vs.avro.jsonvsavro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.vs.avro.jsonvsavro.domain.Event;
import com.json.vs.avro.jsonvsavro.domain.Payment;

import java.math.BigDecimal;
import java.util.UUID;

public class Main
{
    public static void main(String[] args) {


        ObjectMapper objectMapper = new ObjectMapper();

        Event event = new Event<Payment>(UUID.randomUUID().toString(), new Payment("transfer one", new BigDecimal("12")));

        String payment = objectMapper.valueToTree(event).toString();

        System.out.println(payment);

    }




}
