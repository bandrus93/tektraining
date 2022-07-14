package com.luv2code.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class FizzBuzzTest {

    @Test
    void testForDivisibleByThree() {
        String expected = "Fizz";
        Assertions.assertEquals(expected, FizzBuzz.compute(3), "Output: 'Fizz' expected");
    }

    @Test
    void testForDivisibleByFive() {
        String expected = "Buzz";
        Assertions.assertEquals(expected, FizzBuzz.compute(5), "Output: 'Buzz' expected");
    }

    @Test
    void testForDivisibleByThreeAndFive() {
        String expected = "FizzBuzz";
        Assertions.assertEquals(expected, FizzBuzz.compute(15), "Output: 'FizzBuzz' expected");
    }

    @Test
    void testForNotDivisibleByThreeOrFive() {
        String expected = "1";
        Assertions.assertEquals(expected, FizzBuzz.compute(1), "Output: '1' expected");
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @DisplayName("Testing with small data file")
    void testUsingSmallCsvValues(int value, String expected) {
        Assertions.assertEquals(expected, FizzBuzz.compute(value));
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @DisplayName("Testing with medium data file")
    void testUsingMediumCsvValues(int value, String expected) {
        Assertions.assertEquals(expected, FizzBuzz.compute(value));
    }

}
