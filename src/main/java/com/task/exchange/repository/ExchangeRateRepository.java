package com.task.exchange.repository;

import com.task.exchange.model.CurrencyEnum;
import com.task.exchange.model.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {
    Optional<ExchangeRateEntity> findByFromCurrencyAndToCurrency(CurrencyEnum fromCurrency, CurrencyEnum toCurrency);
}
