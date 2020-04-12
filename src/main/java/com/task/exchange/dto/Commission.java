package com.task.exchange.dto;

import com.task.exchange.model.CurrencyEnum;

import java.math.BigDecimal;

public class Commission {

    private CurrencyEnum fromCurrency;

    private CurrencyEnum toCurrency;

    private BigDecimal commissionPct;

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

    public BigDecimal getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(BigDecimal commissionPct) {
        this.commissionPct = commissionPct;
    }
}
