package com.example.abwkafkaproducer.models.clients.abw.currency;

import com.example.abwkafkaproducer.models.clients.abw.currency.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyExchangeDTO {

    private Currency currencyMain;

    private Currency currencyTo;

    private BigDecimal value;
}
