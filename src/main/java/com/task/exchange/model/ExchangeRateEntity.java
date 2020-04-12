package com.task.exchange.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "EXCHANGE_RATE")
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FROM_CURRENCY")
    @NotNull(message = "Please provide fromCurrency")
    private CurrencyEnum fromCurrency;

    @Column(name = "TO_CURRENCY")
    @NotNull(message = "Please provide toCurrency")
    private CurrencyEnum toCurrency;

    @Column(name = "RATE")
    @NotNull(message = "Please provide rate")
    private BigDecimal rate;

    public ExchangeRateEntity() {
    }

    public ExchangeRateEntity(CurrencyEnum fromCurrency, CurrencyEnum toCurrency, BigDecimal rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
