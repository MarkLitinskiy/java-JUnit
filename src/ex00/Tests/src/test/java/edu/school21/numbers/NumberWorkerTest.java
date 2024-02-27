package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import edu.school21.numbers.NumberWorker;

class NumberWorkerTest {
    NumberWorker numberWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {3, 7, 11, 17, 19, 31, 41})
    void isPrimeForPrimes(int numb) throws IllegalNumberException {
        assertTrue(numberWorker.isPrime(numb));
    }

    @ParameterizedTest
    @ValueSource(ints = {169, 9, 10, 14, 21, 200, 504})
    void isPrimeForNotPrimes(int numb) throws IllegalNumberException {
        assertFalse(numberWorker.isPrime(numb));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -200, 0, -214})
    void isPrimeForIncorrectNumbers(int numb) {
        IllegalNumberException thrown = Assertions.assertThrows(IllegalNumberException.class, () -> {
            numberWorker.isPrime(numb);
        });

        Assertions.assertEquals("Illegal argument!", thrown.getMessage());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int numb, int sum) {
        assertEquals(numberWorker.digitsSum(numb), sum);
    }
}