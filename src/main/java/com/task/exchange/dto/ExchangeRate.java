package com.task.exchange.dto;

import com.task.exchange.model.CurrencyEnum;

import java.math.BigDecimal;

public class ExchangeRate {

    private CurrencyEnum fromCurrency;

    private CurrencyEnum toCurrency;

    private BigDecimal rate;

    public CurrencyEnum getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(CurrencyEnum fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public CurrencyEnum getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(CurrencyEnum toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
