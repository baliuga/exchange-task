package com.task.exchange.repository;

import com.task.exchange.model.Commission;
import com.task.exchange.model.CurrencyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long> {

    @Query(value = "SELECT c FROM Commission c WHERE c.fromCurrency = :fromCurrency AND c.toCurrency = :toCurrency")
    Commission findCommissionByCurrency(@Param("fromCurrency") CurrencyEnum fromCurrency, @Param("toCurrency") CurrencyEnum toCurrency);

    @Query(value = "SELECT c.commissionPct FROM Commission c WHERE c.fromCurrency = :fromCurrency AND c.toCurrency = :toCurrency")
    BigDecimal findCommissionAmountByCurrency(@Param("fromCurrency") CurrencyEnum fromCurrency, @Param("toCurrency") CurrencyEnum toCurrency);
}
