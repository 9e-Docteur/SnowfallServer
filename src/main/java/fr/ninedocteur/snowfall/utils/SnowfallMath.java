package fr.ninedocteur.snowfall.utils;

import java.util.Random;

public class SnowfallMath {
    public static float minus(float start, float minus){
        return start - minus;
    }

    public static float add(float start, float toAdd){
        return start + toAdd;
    }

    public static float multiply(float start, float multiplyer){
        return start * multiplyer;
    }

    public static float devide(float start, float devider){
        if(devider == 0){
            throw new ArithmeticException("Devider can't be equals to 0");
        }
        return start / devider;
    }

    public static float getHighestValue(float... values){
        float highestValue = 0f;
        for(float val : values){
            if(val >= highestValue){
                highestValue = val;
            }
        }
        return highestValue;
    }

    public static float getLowestValue(float... values){
        float lowestValue = 0f;
        for(float val : values){
            if(val <= lowestValue){
                lowestValue = val;
            }
        }
        return lowestValue;
    }

    public static float power(float start, float exponent) {
        if (start == 0.0f && exponent <= 0.0f) {
            throw new ArithmeticException("Power can't be equals to 0");
        }
        return (float) Math.pow(start, exponent);
    }

    //TODO: Use those 4 methods for heart progressbar or else if we want to make something like a progress bar
    public static float getValueFromAValueWithFraction(SnowfallMathFraction fraction, float fromValue){ //For instance get 1/3 from 900 (=300)
        return (fromValue / fraction.getDenominator()) * fraction.getNumerator();
    }

    public static float getValueFromAValue(float purcentage, float fromValue){ //For instance get 20% from 900 (=180)
        if(purcentage > 100 || purcentage < 0){
            throw new ArithmeticException("Purcentage must be between 0 and 100");
        }
        return fromValue * (purcentage / 100);
    }

    public static float getPourcentageFromAValueWithWantedValue(float valueWanted, float fromValue){
        //from value = 100%, valuewanted = ?% -- We are looking for how many % from our base value (fromValue) is our valueWanted
        return (valueWanted / fromValue) * 100;
    }

    public static float getValueFromAValueWithWantedValueAsPurcentage(float valueWanted, float fromValue){
        //from value = 100%, valuewanted ?? = purcentagewanted% -- We are looking for what is the value for (for instance) 20% from our base value fromValue
        return (valueWanted / 100) * fromValue;
    }

    //-------------------------
    public static int generateRandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be higher than min");
        }

        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
