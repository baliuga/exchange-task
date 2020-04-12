package com.task.exchange.service;

import com.task.exchange.model.Commission;
import com.task.exchange.model.ExchangeRate;
import com.task.exchange.model.ExchangeRequest;
import com.task.exchange.model.OperationTypeEnum;
import com.task.exchange.repository.CommissionRepository;
import com.task.exchange.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private CommissionRepository commissionRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    final static private BigDecimal ONE = BigDecimal.valueOf(1);
    final static private BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    final static private String COMMISSION_PCT_ERR_MESSAGE = "Please add commission percent for this pair";
    final static private String AMOUNT_FROM_ERR_MESSAGE = "Please specify amountFrom value";
    final static private String AMOUNT_TO_ERR_MESSAGE = "Please specify amountTo value";

    @Override
    public List<Commission> retrieveCommissions() {
        return commissionRepository.findAll();
    }

    @Override
    public Commission saveCommission(Commission newCommission) {
        Commission commission = commissionRepository
                .findCommissionByCurrency(newCommission.getFromCurrency(), newCommission.getToCurrency());
        if (commission != null) {
            return updateCommission(commission, newCommission.getCommissionPct());
        }

        return commissionRepository.save(newCommission);
    }

    private Commission updateCommission(Commission commission, BigDecimal commissionPct) {
        commission.setCommissionPct(commissionPct);
        return commissionRepository.save(commission);
    }

    @Override
    public List<ExchangeRate> retrieveExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public ExchangeRate saveExchangeRate(ExchangeRate newExchangeRate) {
        ExchangeRate exchangeRate = exchangeRateRepository
                .findExchangeRateByCurrency(newExchangeRate.getFromCurrency(), newExchangeRate.getToCurrency());
        BigDecimal newBackRate = ONE.divide(newExchangeRate.getRate(), 2, BigDecimal.ROUND_HALF_UP);
        if (exchangeRate != null) {
            return updateExchangeRate(newExchangeRate, exchangeRate, newBackRate);
        }

        ExchangeRate backExchangeRate = new ExchangeRate(newExchangeRate.getToCurrency(), newExchangeRate.getFromCurrency(), newBackRate);
        exchangeRateRepository.save(backExchangeRate);

        return exchangeRateRepository.save(newExchangeRate);
    }

    private ExchangeRate updateExchangeRate(ExchangeRate newExchangeRate, ExchangeRate exchangeRate, BigDecimal newBackRate) {
        ExchangeRate backExchangeRate = exchangeRateRepository
                .findExchangeRateByCurrency(newExchangeRate.getToCurrency(), newExchangeRate.getFromCurrency());
        backExchangeRate.setRate(newBackRate);
        exchangeRateRepository.save(backExchangeRate);

        exchangeRate.setRate(newExchangeRate.getRate());
        return exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public ExchangeRequest exchange(ExchangeRequest exchangeRequest) {
        BigDecimal commissionPct = commissionRepository
                .findCommissionAmountByCurrency(exchangeRequest.getCurrencyFrom(), exchangeRequest.getCurrencyTo());
        if (commissionPct == null) {
            throw new RuntimeException(COMMISSION_PCT_ERR_MESSAGE);
        }

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

        BigDecimal exchangeRate = exchangeRateRepository
                .findRateAmountByCurrency(exchangeRequest.getCurrencyFrom(), exchangeRequest.getCurrencyTo());
        exchangeRequest.setAmountTo(calculateDirectExchange(exchangeRate, commissionPct, amountFrom));
    }

    private BigDecimal calculateDirectExchange(BigDecimal exchangeRate, BigDecimal commissionPct, BigDecimal amountFrom) {
        BigDecimal convertedCurrency = exchangeRate.multiply(amountFrom);
        BigDecimal commission = amountFrom.multiply(commissionPct).divide(ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);

        return convertedCurrency.subtract(commission);
    }

    private void setAmountFrom(ExchangeRequest exchangeRequest, BigDecimal commissionPct) {
        BigDecimal amountTo = exchangeRequest.getAmountTo();
        if (amountTo == null) {
            throw new RuntimeException(AMOUNT_TO_ERR_MESSAGE);
        }

        BigDecimal exchangeRate = exchangeRateRepository
                .findRateAmountByCurrency(exchangeRequest.getCurrencyTo(), exchangeRequest.getCurrencyFrom());
        exchangeRequest.setAmountFrom(calculateBackExchange(exchangeRate, commissionPct, amountTo));
    }

    private BigDecimal calculateBackExchange(BigDecimal exchangeRate, BigDecimal commissionPct, BigDecimal amountTo) {
        BigDecimal convertedCurrency = exchangeRate.multiply(amountTo);
        BigDecimal commission = convertedCurrency.multiply(commissionPct).divide(ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);

        return convertedCurrency.add(commission);
    }
}
