package com.example.abwkafkaproducer.kafka_consumers;

import com.example.abwkafkaproducer.models.clients.abw.car_ad.CarAdDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Component
public class CarAdListener {

    @Autowired
    private KafkaTemplate<String, CarAdDTO> carAdDTOKafkaTemplate;

    private final static ArrayList<String> cars = new ArrayList<>(Arrays.asList("Audi", "BMW"));

    @KafkaListener(topics = "car_ad", groupId = "car_adGroup",
            containerFactory = "carAdDTOKafkaListenerContainerFactory")
    public void carAdListener(ConsumerRecord<String, CarAdDTO> consumerRecord) {
        if (cars.contains(consumerRecord.value().getCarBrandName())) consumerRecord.value().setCorrect(true);
        else {
            consumerRecord.value().setCorrect(false);
            consumerRecord.value().setErrorMessage("not valid car brand name");
        }
        ProducerRecord<String, CarAdDTO> record = new ProducerRecord<>("car_ad-result", consumerRecord.value());
        carAdDTOKafkaTemplate.send(record);
    }
}
