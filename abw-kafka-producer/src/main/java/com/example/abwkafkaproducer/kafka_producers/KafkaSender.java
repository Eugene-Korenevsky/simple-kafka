package com.example.abwkafkaproducer.kafka_producers;

import com.example.abwkafkaproducer.models.CurrencyExchangeDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCurrencyExchange(Object o, String topic) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, o);
        kafkaTemplate.send(record);
    }
}
