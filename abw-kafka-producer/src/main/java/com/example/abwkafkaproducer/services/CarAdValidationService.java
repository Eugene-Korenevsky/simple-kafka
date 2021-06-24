package com.example.abwkafkaproducer.services;

import com.example.abwkafkaproducer.kafka_producers.KafkaSender;
import com.example.abwkafkaproducer.models.CarAdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CarAdValidationService {

    @Autowired
    private KafkaSender kafkaSender;
    private final static ArrayList<String> cars = new ArrayList<>(Arrays.asList("Audi", "BMW"));

    public void validateCarAd(CarAdDTO carAdDTO) {
        if (cars.contains(carAdDTO.getCarBrandName())) carAdDTO.setCorrect(true);
        else {
            carAdDTO.setCorrect(false);
            carAdDTO.setErrorMessage("not valid car brand name");
        }
        kafkaSender.sendCurrencyExchange(carAdDTO, "car_ad-result");
    }
}
