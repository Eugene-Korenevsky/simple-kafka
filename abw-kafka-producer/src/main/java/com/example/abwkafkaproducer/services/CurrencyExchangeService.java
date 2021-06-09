package com.example.abwkafkaproducer.services;

import com.example.abwkafkaproducer.models.Currency;
import com.example.abwkafkaproducer.models.CurrencyExchange;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class CurrencyExchangeService {

    public List<CurrencyExchange> getCurrencyExchanges() {
        List<CurrencyExchange> currencyExchanges = new ArrayList<>();
        EnumSet<Currency> currencies = EnumSet.allOf(Currency.class);
        for (Currency currency : currencies) {
            for (Currency currencyTo : currencies) {
                if (!currency.equals(currencyTo)) {
                    CurrencyExchange currencyExchange = new CurrencyExchange();
                    currencyExchange.setCurrencyMain(currency);
                    currencyExchange.setCurrencyTo(currencyTo);
                    currencyExchange.setValue(BigDecimal.valueOf(22.22));
                    currencyExchanges.add(currencyExchange);
                }
            }
        }
        return currencyExchanges;
    }
}
