package com.example.kafkaconsumer;

import com.example.kafkaconsumer.models.User;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@EnableKafka
@Component
public class KafkaListener {
    private final CountDownLatch greetingLatch = new CountDownLatch(1);

    @org.springframework.kafka.annotation.KafkaListener(topics = "first",
            containerFactory = "userKafkaListenerContainerFactory")
    public void userListening(User user) {
        System.out.println("final user " + user);
        this.greetingLatch.countDown();
    }
}
