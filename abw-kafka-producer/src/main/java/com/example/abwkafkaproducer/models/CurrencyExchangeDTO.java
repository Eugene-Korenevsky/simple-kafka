package com.example.abwkafkaproducer.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyExchangeDTO {

    private Currency currencyMain;

    private Currency currencyTo;

    private BigDecimal value;
}
