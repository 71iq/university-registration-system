package com.swer348;

/**
 * The `Grade` class represents a student's grade in a course.
 * It includes information about the grade value and provides methods for accessing and generating grades.
 * The class also overrides the `toString` method to provide a formatted string representation of the grade.
 *
 * @author Ehab, Maamoun
 * @version 1.0
 * @since 2023-12-29
 */
public class Grade {

    /**
     * The numerical value of the grade.
     */
    private final double value;

    /**
     * Constructs a `Grade` object with the specified numerical value.
     *
     * @param value The numerical value of the grade.
     */
    public Grade(double value) {
        this.value = value;
    }

    /**
     * Retrieves the numerical value of the grade.
     *
     * @return The numerical value of the grade.
     */
    public double getValue() {
        return value;
    }

    /**
     * Generates and returns a random grade.
     *
     * @return A `Grade` object with a randomly generated value.
     */
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

    /**
     * Overrides the `toString` method to provide a formatted string representation of the grade.
     *
     * @return A formatted string representation of the grade.
     */
    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}
