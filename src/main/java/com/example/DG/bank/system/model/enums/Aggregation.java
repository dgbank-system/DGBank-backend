package com.example.DG.bank.system.model.enums;

import java.util.Optional;

public enum Aggregation {
    Sum("Sum"),
    Avg("Avg"),
    Min("Min"),
    Max("Max"),
    Count("Count");

    private final String value;

    Aggregation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<Aggregation> fromValue(String value) {
        for (Aggregation agg : values()) {
            if (agg.value != null && agg.value.equals(value)) {
                return Optional.of(agg);
            }
        }
        return Optional.empty();
    }

}
