package com.axesplit.image.model.enums;

public enum CompressionFactor {
    LOW(2),
    MEDIUM(4),
    HIGH(8);

    private final int value;

    CompressionFactor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
