package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) throws IllegalNumberException {
        int steps = 0;
        if (number <= 1) {
            throw new IllegalNumberException("Illegal argument!");
        }

        for (steps = 2; steps <= Math.sqrt(number); steps++) {
            if (number % steps == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        int result = 0;

        while (number > 0) {
            result += number % 10;
            number /= 10;
        }
        return result;
    }

}