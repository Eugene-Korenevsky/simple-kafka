package com.example.abwkafkaproducer.kafka_producers;

import com.example.abwkafkaproducer.models.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class CurrencyExchangeSender {
    @Autowired
    private KafkaTemplate<String, CurrencyExchange> userKafkaTemplate;

    public void sendCurrencyExchange(CurrencyExchange currencyExchange) {
        ListenableFuture<SendResult<String, CurrencyExchange>> future
                = userKafkaTemplate.send("currency_exchange", currencyExchange);

        future.addCallback(new ListenableFutureCallback<SendResult<String, CurrencyExchange>>() {

            @Override
            public void onSuccess(SendResult<String, CurrencyExchange> result) {
                System.out.println("successful sending " + result);
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("error " + ex.getMessage());
            }
        });
    }
}
