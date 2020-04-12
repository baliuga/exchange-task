package com.task.exchange.model;

public enum OperationTypeEnum {
    GET("GET"),
    GIVE("GIVE");

    private String value;

    OperationTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static OperationTypeEnum getValue(String text) {
        for (OperationTypeEnum b : OperationTypeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
