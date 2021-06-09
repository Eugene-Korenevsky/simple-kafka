package com.example.abwkafkaproducer.services;

import com.example.abwkafkaproducer.kafka_producers.CurrencyExchangeSender;
import com.example.abwkafkaproducer.models.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableAsync
public class KafkaCurrencyExchangeProducerService {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    @Autowired
    private CurrencyExchangeSender currencyExchangeSender;

    @Scheduled(fixedDelay = 30000)
    @Async
    public void currencyExchange() {
        List<CurrencyExchange> currencyExchanges = currencyExchangeService.getCurrencyExchanges();
        for (CurrencyExchange currencyExchange : currencyExchanges){
            System.out.println(currencyExchange);
            currencyExchangeSender.sendCurrencyExchange(currencyExchange);
        }
    }
}
