package com.example.abwkafkaproducer.kafka;

import com.example.abwkafkaproducer.models.CarAdDTO;
import com.example.abwkafkaproducer.services.CarAdValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @Autowired
    private CarAdValidationService carAdValidationService;


    @KafkaListener(topics = "car_ad", groupId = "car_adGroup",
            containerFactory = "kafkaJsonListenerContainerFactory")
    public void carAdListener(CarAdDTO carAdDTO) {
        carAdValidationService.validateCarAd(carAdDTO);
    }
}
