package ca.jbrains.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddFractionsTest {
    @Test
    void zeroPlusZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(0));
        Assertions.assertEquals(new Fraction(0), sum);
    }

    @Test
    void notZeroPlusZero() throws Exception {
        Fraction sum = new Fraction(4).plus(new Fraction(0));
        Assertions.assertEquals(new Fraction(4), sum);
    }

    @Test
    void zeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(7));
        Assertions.assertEquals(new Fraction(7), sum);
    }

    @Test
    void notZeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(5).plus(new Fraction(8));
        Assertions.assertEquals(new Fraction(13), sum);
    }

    @Test
    void sameDenominator() throws Exception {
        Fraction sum = new Fraction(1, 3).plus(new Fraction(1, 3));
        Assertions.assertEquals(new Fraction(2, 3), sum);
    }

    @Test
    void relativelyPrimeDenominators() throws Exception {
        Fraction sum = new Fraction(3, 4).plus(new Fraction(2, 3));
        Assertions.assertEquals(new Fraction(17, 12), sum);
    }

    public static class Fraction {
        private int numerator;
        private int denominator;

        public Fraction(int integerValue) {
            this(integerValue, 1);
        }

        public Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction plus(Fraction that) {
            if (this.denominator == that.denominator)
                return new Fraction(
                        this.numerator + that.numerator,
                        this.denominator);
            else
                return new Fraction(
                        this.numerator * that.denominator
                                + that.numerator * this.denominator,
                        this.denominator * that.denominator);
        }

        public boolean equals(Object other) {
            if (other instanceof Fraction) {
                Fraction that = (Fraction) other;
                return this.numerator * that.denominator
                        == that.numerator * this.denominator;
            }
            else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return -762;
        }

        @Override
        public String toString() {
            return String.format("%d/%d", numerator, denominator);
        }
    }
}
