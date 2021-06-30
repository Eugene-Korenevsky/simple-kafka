package com.example.abwkafkaproducer.services;


import com.example.abwkafkaproducer.kafka.KafkaSender;
import com.example.abwkafkaproducer.models.CurrencyExchangeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
@EnableAsync
@PropertySource("classpath:kafka.properties")
public class KafkaCurrencyExchangeProducerService {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    @Autowired
    private KafkaSender kafkaSender;

    @Value("${currency.topic}")
    private String currencyTopic;

    @Scheduled(fixedDelay = 86400000)
    @Async
    public void currencyExchange() throws ExecutionException, InterruptedException, TimeoutException {
        List<CurrencyExchangeDTO> currencyExchangeDTOS = currencyExchangeService.getCurrencyExchanges();
        for (CurrencyExchangeDTO currencyExchange : currencyExchangeDTOS) {
            kafkaSender.send(currencyExchange,currencyTopic);
        }
    }
}
