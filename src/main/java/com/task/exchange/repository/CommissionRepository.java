package com.task.exchange.repository;

import com.task.exchange.model.CommissionEntity;
import com.task.exchange.model.CurrencyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommissionRepository extends JpaRepository<CommissionEntity, Long> {
    Optional<CommissionEntity> findByFromCurrencyAndToCurrency(CurrencyEnum fromCurrency, CurrencyEnum toCurrency);
}
