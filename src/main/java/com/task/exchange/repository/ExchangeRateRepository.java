package com.task.exchange.repository;

import com.task.exchange.model.CurrencyEnum;
import com.task.exchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query(value = "SELECT er FROM ExchangeRate er WHERE er.fromCurrency = :fromCurrency AND er.toCurrency = :toCurrency")
    ExchangeRate findExchangeRateByCurrency(@Param("fromCurrency") CurrencyEnum fromCurrency, @Param("toCurrency") CurrencyEnum toCurrency);

    @Query(value = "SELECT er.rate FROM ExchangeRate er WHERE er.fromCurrency = :fromCurrency AND er.toCurrency = :toCurrency")
    BigDecimal findRateAmountByCurrency(@Param("fromCurrency") CurrencyEnum fromCurrency, @Param("toCurrency") CurrencyEnum toCurrency);
}
