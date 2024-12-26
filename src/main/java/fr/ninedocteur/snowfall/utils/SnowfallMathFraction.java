package fr.ninedocteur.snowfall.utils;

public class SnowfallMathFraction {
    private int numerator;
    private int denominator;

    public SnowfallMathFraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("The denominator can't be 0");
        }

        int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;

        if (this.denominator < 0) {
            this.numerator = -this.numerator;
            this.denominator = -this.denominator;
        }
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public SnowfallMathFraction add(SnowfallMathFraction other) {
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new SnowfallMathFraction(newNumerator, newDenominator);
    }

    public SnowfallMathFraction subtract(SnowfallMathFraction other) {
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new SnowfallMathFraction(newNumerator, newDenominator);
    }

    public SnowfallMathFraction multiply(SnowfallMathFraction other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new SnowfallMathFraction(newNumerator, newDenominator);
    }

    public SnowfallMathFraction divide(SnowfallMathFraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Can't devide by 0");
        }

        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new SnowfallMathFraction(newNumerator, newDenominator);
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
