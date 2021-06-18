package com.example.abwkafkaproducer.kafka_producers;

import com.example.abwkafkaproducer.models.clients.abw.currency.CurrencyExchangeDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class CurrencyExchangeSender {
    @Autowired
    private ReplyingKafkaTemplate<String, CurrencyExchangeDTO, CurrencyExchangeDTO> replyingKafkaTemplate;

    public void sendCurrencyExchange(CurrencyExchangeDTO currencyExchangeDTO) throws ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<String, CurrencyExchangeDTO> record =
                new ProducerRecord<String, CurrencyExchangeDTO>("currency_exchange", currencyExchangeDTO);
       // record.headers().add(KafkaHeaders.REPLY_TOPIC,"currency_exchange-result".getBytes());
        RequestReplyFuture<String, CurrencyExchangeDTO, CurrencyExchangeDTO> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
        SendResult<String, CurrencyExchangeDTO> sendResult = replyFuture.getSendFuture().get(20, TimeUnit.SECONDS);
        System.out.println("Sent ok: " + sendResult.getRecordMetadata());
        ConsumerRecord<String, CurrencyExchangeDTO> consumerRecord = replyFuture.get(20, TimeUnit.SECONDS);
        System.out.println("Return value: " + consumerRecord.value());
        System.out.println("hello");
       // record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "currency_exchange-result".getBytes()));
        /*RequestReplyFuture<String, CurrencyExchange, CurrencyExchange> sendAndReceive =
                replyingKafkaTemplate.sendAndReceive(record);
        // ListenableFuture<SendResult<String, CurrencyExchange>> future
        //  = userKafkaTemplate.send("currency_exchange", currencyExchange);

        sendAndReceive.addCallback(new ListenableFutureCallback<ConsumerRecord<String, CurrencyExchange>>() {

            @Override
            public void onSuccess(ConsumerRecord<String, CurrencyExchange> result) {
                System.out.println("successful sending " + result.value());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("error " + ex.getMessage());
            }
        });

        try {
            ConsumerRecord<String,CurrencyExchange> sendResult = sendAndReceive.get();
            System.out.println(sendResult);
            System.out.println(sendResult);
            System.out.println("--------------------");
            System.out.println("--------------------");
            System.out.println("--------------------");
            System.out.println("--------------------");
            System.out.println(sendResult.value());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }
}
