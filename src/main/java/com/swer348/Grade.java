package com.swer348;

public class Grade {
    private final double value;

    public Grade(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public static Grade assignRandomGrade() {
        double gradeValue;

        double randomProbability = Math.random();

        // equal probability for getting above 3.95, 3.5, 2.0, and under 2.0 for more realisticality
        if (randomProbability < 0.25)
            gradeValue = 3.95 + Math.random() * (4.0 - 3.95);
        else if (randomProbability < 0.5)
            gradeValue = 3.5 + Math.random() * (3.95 - 3.5);
        else if (randomProbability < 0.75)
            gradeValue = 1.0 + Math.random() * (3.5 - 1.0);
        else
            gradeValue = Math.random() * 2.0;
        return new Grade(gradeValue);
    }


    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}
