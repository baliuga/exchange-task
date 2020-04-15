package com.task.exchange.dto;

import com.task.exchange.model.CurrencyEnum;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ExchangeRate {

    @NotNull(message = "Please provide fromCurrency")
    private CurrencyEnum fromCurrency;

    @NotNull(message = "Please provide toCurrency")
    private CurrencyEnum toCurrency;

    @DecimalMin(value = "0.00", message = "Rate should not be negative")
    @NotNull(message = "Please provide rate")
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
