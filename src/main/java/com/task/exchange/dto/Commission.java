package com.task.exchange.dto;

import com.task.exchange.model.CurrencyEnum;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Commission {

    @NotNull(message = "Please provide fromCurrency")
    private CurrencyEnum fromCurrency;

    @NotNull(message = "Please provide toCurrency")
    private CurrencyEnum toCurrency;

    @NotNull(message = "Please provide a commission percent")
    @DecimalMin(value = "0.00", message = "Commission should not be negative")
    @DecimalMax(value = "100.00", message = "Commission should be less than 100")
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
