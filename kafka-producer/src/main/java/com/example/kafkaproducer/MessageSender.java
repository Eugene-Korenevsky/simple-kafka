package com.example.kafkaproducer;

import com.example.kafkaproducer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class MessageSender {
    @Autowired
    private KafkaTemplate<String, Object> userKafkaTemplate;

    public void sendUser(Object user) {
        ListenableFuture<SendResult<String, Object>> future = userKafkaTemplate.send("first", user);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("successful sending " + result);
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("error " + ex.getMessage());
            }
        });
    }
}
