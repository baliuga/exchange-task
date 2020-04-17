package com.task.exchange.service;

import com.task.exchange.model.*;
import com.task.exchange.repository.CommissionRepository;
import com.task.exchange.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private CommissionRepository commissionRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    final static private BigDecimal ONE = BigDecimal.valueOf(1);
    final static private BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    final static private String COMMISSION_PCT_ERR_MESSAGE = "Please add commission percent for this pair";
    final static private String EXCHANGE_RATE_ERR_MESSAGE = "Please add exchange rate for this pair";
    final static private String AMOUNT_FROM_ERR_MESSAGE = "Please specify amountFrom value";
    final static private String AMOUNT_TO_ERR_MESSAGE = "Please specify amountTo value";

    @Override
    public List<CommissionEntity> retrieveCommissions() {
        return commissionRepository.findAll();
    }

    @Override
    public CommissionEntity saveCommission(CommissionEntity newCommission) {
        Optional<CommissionEntity> commission = commissionRepository
                .findByFromCurrencyAndToCurrency(newCommission.getFromCurrency(), newCommission.getToCurrency());
        if (commission.isPresent()) {
            return updateCommission(commission.get(), newCommission.getCommissionPct());
        }

        return commissionRepository.save(newCommission);
    }

    private CommissionEntity updateCommission(CommissionEntity commission, BigDecimal commissionPct) {
        commission.setCommissionPct(commissionPct);
        return commissionRepository.save(commission);
    }

    @Override
    public List<ExchangeRateEntity> retrieveExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public ExchangeRateEntity saveExchangeRate(ExchangeRateEntity newExchangeRate) {
        Optional<ExchangeRateEntity> exchangeRate = exchangeRateRepository
                .findByFromCurrencyAndToCurrency(newExchangeRate.getFromCurrency(), newExchangeRate.getToCurrency());
        BigDecimal newBackRate = ONE.divide(newExchangeRate.getRate(), 2, BigDecimal.ROUND_HALF_UP);
        if (exchangeRate.isPresent()) {
            return updateExchangeRate(newExchangeRate, exchangeRate.get(), newBackRate);
        }

        ExchangeRateEntity backExchangeRate = new ExchangeRateEntity(newExchangeRate.getToCurrency(), newExchangeRate.getFromCurrency(), newBackRate);
        exchangeRateRepository.save(backExchangeRate);

        return exchangeRateRepository.save(newExchangeRate);
    }

    private ExchangeRateEntity updateExchangeRate(ExchangeRateEntity newExchangeRate, ExchangeRateEntity exchangeRate, BigDecimal newBackRate) {
        ExchangeRateEntity backExchangeRate = exchangeRateRepository
                .findByFromCurrencyAndToCurrency(newExchangeRate.getToCurrency(), newExchangeRate.getFromCurrency()).get();
        backExchangeRate.setRate(newBackRate);
        exchangeRateRepository.save(backExchangeRate);

        exchangeRate.setRate(newExchangeRate.getRate());
        return exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public ExchangeRequest exchange(ExchangeRequest exchangeRequest) {
        Optional<CommissionEntity> commission = commissionRepository
                .findByFromCurrencyAndToCurrency(exchangeRequest.getCurrencyFrom(), exchangeRequest.getCurrencyTo());
        if (!commission.isPresent()) {
            throw new RuntimeException(COMMISSION_PCT_ERR_MESSAGE);
        }

        BigDecimal commissionPct = commission.get().getCommissionPct();
        if (OperationTypeEnum.GIVE == exchangeRequest.getOperationType()) {
            setAmountTo(exchangeRequest, commissionPct);
        } else {
            setAmountFrom(exchangeRequest, commissionPct);
        }

        return exchangeRequest;
    }

    private void setAmountTo(ExchangeRequest exchangeRequest, BigDecimal commissionPct) {
        BigDecimal amountFrom = exchangeRequest.getAmountFrom();
        if (amountFrom == null) {
            throw new RuntimeException(AMOUNT_FROM_ERR_MESSAGE);
        }

        BigDecimal rate = getRateForCurrencyPair(exchangeRequest.getCurrencyFrom(), exchangeRequest.getCurrencyTo());
        exchangeRequest.setAmountTo(calculateDirectExchange(rate, commissionPct, amountFrom));
    }

    private BigDecimal calculateDirectExchange(BigDecimal exchangeRate, BigDecimal commissionPct, BigDecimal amountFrom) {
        BigDecimal convertedCurrency = exchangeRate.multiply(amountFrom);
        BigDecimal commission = convertedCurrency.multiply(commissionPct).divide(ONE_HUNDRED);

        return convertedCurrency.subtract(commission).setScale(2, BigDecimal.ROUND_DOWN);
    }

    private void setAmountFrom(ExchangeRequest exchangeRequest, BigDecimal commissionPct) {
        BigDecimal amountTo = exchangeRequest.getAmountTo();
        if (amountTo == null) {
            throw new RuntimeException(AMOUNT_TO_ERR_MESSAGE);
        }

        BigDecimal rate = getRateForCurrencyPair(exchangeRequest.getCurrencyTo(), exchangeRequest.getCurrencyFrom());
        exchangeRequest.setAmountFrom(calculateBackExchange(rate, commissionPct, amountTo));
    }

    private BigDecimal calculateBackExchange(BigDecimal exchangeRate, BigDecimal commissionPct, BigDecimal amountTo) {
        BigDecimal commission = (amountTo.multiply(commissionPct)).divide((ONE_HUNDRED.subtract(commissionPct)), 2, BigDecimal.ROUND_UP);
        BigDecimal currencyAmount = amountTo.add(commission);

        return currencyAmount.multiply(exchangeRate).setScale(2, BigDecimal.ROUND_UP);
    }

    private BigDecimal getRateForCurrencyPair(CurrencyEnum fromCurrency, CurrencyEnum toCurrency) {
        Optional<ExchangeRateEntity> exchangeRate = exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        if (!exchangeRate.isPresent()) {
            throw new RuntimeException(EXCHANGE_RATE_ERR_MESSAGE);
        }

        return exchangeRate.get().getRate();
    }
}
