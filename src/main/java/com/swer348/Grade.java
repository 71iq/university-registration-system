package com.swer348;

public class Grade {
    private double value;

    public Grade(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}
