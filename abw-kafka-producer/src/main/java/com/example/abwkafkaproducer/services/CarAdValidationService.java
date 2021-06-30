package com.example.abwkafkaproducer.services;

import com.example.abwkafkaproducer.kafka.KafkaSender;
import com.example.abwkafkaproducer.models.CarAdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@PropertySource("classpath:kafka.properties")
public class CarAdValidationService {

    @Autowired
    private KafkaSender kafkaSender;

    @Value("${car.ad.topic.result}")
    private String resultTopic;

    private final static ArrayList<String> cars = new ArrayList<>(Arrays.asList("Audi", "BMW"));

    public void validateCarAd(CarAdDTO carAdDTO) {
        if (cars.contains(carAdDTO.getCarBrandName())) carAdDTO.setCorrect(true);
        else {
            carAdDTO.setCorrect(false);
            carAdDTO.setErrorMessage("not valid car brand name");
        }
        kafkaSender.send(carAdDTO, resultTopic);
    }
}
