package com.task.exchange.service;

import com.task.exchange.model.CommissionEntity;
import com.task.exchange.model.ExchangeRateEntity;
import com.task.exchange.model.ExchangeRequest;

import java.util.List;

public interface ExchangeService {

    List<CommissionEntity> retrieveCommissions();
    CommissionEntity saveCommission(CommissionEntity commission);

    List<ExchangeRateEntity> retrieveExchangeRates();
    ExchangeRateEntity saveExchangeRate(ExchangeRateEntity exchangeRate);

    ExchangeRequest exchange(ExchangeRequest exchangeRequest);

}
