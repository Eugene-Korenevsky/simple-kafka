package com.example.abwkafkaproducer.services;


import com.example.abwkafkaproducer.kafka_producers.CurrencyExchangeSender;
import com.example.abwkafkaproducer.models.CurrencyExchangeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
@EnableAsync
public class KafkaCurrencyExchangeProducerService {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    @Autowired
    private CurrencyExchangeSender currencyExchangeSender;

    @Scheduled(fixedDelay = 86400000)
    @Async
    public void currencyExchange() throws ExecutionException, InterruptedException, TimeoutException {
        List<CurrencyExchangeDTO> currencyExchangeDTOS = currencyExchangeService.getCurrencyExchanges();
        for (CurrencyExchangeDTO currencyExchange : currencyExchangeDTOS) {
            currencyExchangeSender.sendCurrencyExchange(currencyExchange);
        }
    }
}
