package fr.genetic.server.simulation;

import java.security.SecureRandom;

/**
 * Created by sebastien on 18/01/2015.
 */
public class Random {

    private Random() {

    }

    public static float next() {
        return new SecureRandom().nextFloat();
    }

    public static float next(float maxValue) {
        return new SecureRandom().nextFloat() * maxValue;
    }

    public static float next(float minValue, float maxValue) {
        return (new SecureRandom().nextFloat() * (maxValue - minValue)) + minValue;
    }

    public static int nextInt(int maxValue) {
        return new SecureRandom().nextInt(maxValue);
    }

}
