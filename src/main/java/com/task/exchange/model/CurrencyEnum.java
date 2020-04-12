package com.task.exchange.model;

public enum CurrencyEnum {
    EUR("EUR"),
    USD("USD"),
    UAH("UAH"),
    RUB("RUB");

    private String value;

    CurrencyEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CurrencyEnum getValue(String text) {
        for (CurrencyEnum b : CurrencyEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
