package com.task.exchange.model;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class ExchangeRequest {

    @DecimalMin(value = "0.00", message = "amountFrom should not be negative")
    private BigDecimal amountFrom;

    @DecimalMin(value = "0.00", message = "amountTo should not be negative")
    private BigDecimal amountTo;

    private CurrencyEnum currencyFrom;

    private CurrencyEnum currencyTo;

    private OperationTypeEnum operationType;

    public ExchangeRequest() {
    }

    public ExchangeRequest(BigDecimal amountFrom, BigDecimal amountTo, CurrencyEnum currencyFrom, CurrencyEnum currencyTo, OperationTypeEnum operationType) {
        this.amountFrom = amountFrom;
        this.amountTo = amountTo;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.operationType = operationType;
    }

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
    }

    public CurrencyEnum getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(CurrencyEnum currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public CurrencyEnum getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(CurrencyEnum currencyTo) {
        this.currencyTo = currencyTo;
    }

    public OperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }
}
