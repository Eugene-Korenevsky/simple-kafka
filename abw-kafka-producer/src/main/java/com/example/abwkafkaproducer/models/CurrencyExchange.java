package com.example.abwkafkaproducer.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyExchange {

    private Currency currencyMain;

    private Currency currencyTo;

    private BigDecimal value;
}
