package com.example.abwkafkaproducer.models;

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
