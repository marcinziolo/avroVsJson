package com.json.vs.avro.jsonvsavro;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.vs.avro.jsonvsavro.domain.Event;
import com.json.vs.avro.jsonvsavro.domain.Payment;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JsonVsAvroPerformanceTest {

    public static void main(String[] args) throws IOException {
        // Create a payment event for testing
        Event<Payment> paymentEvent = new Event<>(UUID.randomUUID().toString(), new Payment("transfer one", new BigDecimal("12")));

        // Serialize and deserialize the payment event using JSON
        ObjectMapper objectMapper = new ObjectMapper();
        long jsonSerializationStartTime = System.nanoTime();
        String json = objectMapper.writeValueAsString(paymentEvent);
        long jsonSerializationEndTime = System.nanoTime();

        long jsonDeserializationStartTime = System.nanoTime();
        Event<Payment> jsonPaymentEvent = objectMapper.readValue(json, Event.class);
        long jsonDeserializationEndTime = System.nanoTime();

        // Serialize and deserialize the payment event using Avro
        InputStream schemaStream = JsonVsAvroPerformanceTest.class.getResourceAsStream("/event.avsc");
        Schema schema = new Schema.Parser().parse(schemaStream);



        ByteArrayOutputStream avroOutputStream = new ByteArrayOutputStream();
        DatumWriter<Event<Payment>> datumWriter = new ReflectDatumWriter<>(schema);
        long avroSerializationStartTime = System.nanoTime();
        Encoder avroEncoder = EncoderFactory.get().binaryEncoder(avroOutputStream, null);
        datumWriter.write(paymentEvent, avroEncoder);
        avroEncoder.flush();
        long avroSerializationEndTime = System.nanoTime();

        DatumReader<Event<Payment>> datumReader = new ReflectDatumReader<>(schema);
        ByteArrayInputStream avroInputStream = new ByteArrayInputStream(avroOutputStream.toByteArray());
        Decoder avroDecoder = DecoderFactory.get().binaryDecoder(avroInputStream, null);
        long avroDeserializationStartTime = System.nanoTime();
        Event<Payment> avroPaymentEvent = datumReader.read(null, avroDecoder);
        long avroDeserializationEndTime = System.nanoTime();

        // Print the results
        System.out.println("JSON serialization time: " + TimeUnit.NANOSECONDS.toMillis(jsonSerializationEndTime - jsonSerializationStartTime) + " ns");
        System.out.println("JSON deserialization time: " + TimeUnit.NANOSECONDS.toMillis(jsonDeserializationEndTime - jsonDeserializationStartTime) + " ns");
        System.out.println("JSON bytes length: " +  json.getBytes().length);
        System.out.println("Avro serialization time: " + TimeUnit.NANOSECONDS.toMillis(avroSerializationEndTime - avroSerializationStartTime) + " ns");
        System.out.println("Avro bytes length: " +  avroOutputStream.toByteArray().length);
        System.out.println("Avro deserialization time: " + TimeUnit.NANOSECONDS.toMillis(avroDeserializationEndTime - avroDeserializationStartTime) + " ns");
    }
}
