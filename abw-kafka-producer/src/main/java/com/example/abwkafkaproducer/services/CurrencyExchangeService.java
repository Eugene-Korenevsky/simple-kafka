package com.example.abwkafkaproducer.services;

import com.example.abwkafkaproducer.models.clients.abw.currency.Currency;
import com.example.abwkafkaproducer.models.clients.abw.currency.CurrencyExchangeDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class CurrencyExchangeService {

    public List<CurrencyExchangeDTO> getCurrencyExchanges() {
        List<CurrencyExchangeDTO> currencyExchangeDTOS = new ArrayList<>();
        EnumSet<Currency> currencies = EnumSet.allOf(Currency.class);
        for (Currency currency : currencies) {
            for (Currency currencyTo : currencies) {
                if (!currency.equals(currencyTo)) {
                    CurrencyExchangeDTO currencyExchangeDTO = new CurrencyExchangeDTO();
                    currencyExchangeDTO.setCurrencyMain(currency);
                    currencyExchangeDTO.setCurrencyTo(currencyTo);
                    currencyExchangeDTO.setValue(BigDecimal.valueOf(22.22));
                    currencyExchangeDTOS.add(currencyExchangeDTO);
                }
            }
        }
        return currencyExchangeDTOS;
    }
}
