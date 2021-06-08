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
    private KafkaTemplate<String, User> userKafkaTemplate;

    public void sendUser(User user) {
        ListenableFuture<SendResult<String, User>> future = userKafkaTemplate.send("first", user);

        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {

            @Override
            public void onSuccess(SendResult<String, User> result) {
                System.out.println("successful sending " + result);
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("error " + ex.getMessage());
            }
        });
    }
}
