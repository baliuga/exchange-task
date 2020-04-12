package com.task.exchange.service;

import com.task.exchange.model.Commission;
import com.task.exchange.model.ExchangeRate;
import com.task.exchange.model.ExchangeRequest;

import java.util.List;

public interface ExchangeService {

    List<Commission> retrieveCommissions();
    Commission saveCommission(Commission commission);

    List<ExchangeRate> retrieveExchangeRates();
    ExchangeRate saveExchangeRate(ExchangeRate exchangeRate);

    ExchangeRequest exchange(ExchangeRequest exchangeRequest);

}
