package com.task.exchange.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "COMMISSIONS")
public class CommissionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FROM_CURRENCY")
    @NotNull(message = "Please provide fromCurrency")
    private CurrencyEnum fromCurrency;

    @Column(name = "TO_CURRENCY")
    @NotNull(message = "Please provide toCurrency")
    private CurrencyEnum toCurrency;

    @Column(name = "COMMISSION_PERCENT")
    @DecimalMin("0.00")
    @DecimalMax("100.00")
    @NotNull(message = "Please provide a commission percent")
    private BigDecimal commissionPct;

    public CommissionEntity() {
    }

    public CommissionEntity(CurrencyEnum fromCurrency, CurrencyEnum toCurrency, BigDecimal commissionPct) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.commissionPct = commissionPct;
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

    public BigDecimal getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(BigDecimal commissionPct) {
        this.commissionPct = commissionPct;
    }
}
