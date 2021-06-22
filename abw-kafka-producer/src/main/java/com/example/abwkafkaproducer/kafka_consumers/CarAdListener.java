package com.example.abwkafkaproducer.kafka_consumers;

import com.example.abwkafkaproducer.models.CarAdDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class CarAdListener {

    @Autowired
    private KafkaTemplate<String, CarAdDTO> kafkaTemplate;

    private final static ArrayList<String> cars = new ArrayList<>(Arrays.asList("Audi", "BMW"));

    @KafkaListener(topics = "car_ad", groupId = "car_adGroup",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void carAdListener(CarAdDTO carAdDTO) {
            if (cars.contains(carAdDTO.getCarBrandName())) carAdDTO.setCorrect(true);
            else {
                carAdDTO.setCorrect(false);
                carAdDTO.setErrorMessage("not valid car brand name");
            }
            ProducerRecord<String, CarAdDTO> record = new ProducerRecord<>("car_ad-result", carAdDTO);
            kafkaTemplate.send(record);
    }
}
