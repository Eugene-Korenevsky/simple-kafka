package com.example.abwkafkaproducer.models.clients.abw.car_ad;

import com.example.abwkafkaproducer.models.clients.abw.currency.Currency;
import com.example.abwkafkaproducer.models.clients.abw.user.UserDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class CarAdDTO {
    private long id;
    private boolean isCorrect;
    private Timestamp publicationDate;
    private BigDecimal price;
    private Currency priceCurrency;
    private String carBrandName;
    private String carBrand;
    private UserDTO userDTO;
    private String errorMessage;
}
