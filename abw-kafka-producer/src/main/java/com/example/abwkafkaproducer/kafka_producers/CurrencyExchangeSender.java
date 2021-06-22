package com.example.abwkafkaproducer.kafka_producers;

import com.example.abwkafkaproducer.models.CurrencyExchangeDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
@PropertySource("classpath:kafka.properties")
public class CurrencyExchangeSender {

    @Value("${currency.topic}")
    private String currencyTopic;

    @Autowired
    private KafkaTemplate<String, CurrencyExchangeDTO> kafkaTemplate;

    public void sendCurrencyExchange(CurrencyExchangeDTO currencyExchangeDTO) {
        ProducerRecord<String, CurrencyExchangeDTO> record =
                new ProducerRecord<String, CurrencyExchangeDTO>(currencyTopic, currencyExchangeDTO);
        kafkaTemplate.send(record);
    }
}
