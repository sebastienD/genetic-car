package fr.genetic.server.simulation;

import java.security.SecureRandom;

public class Random {

    private static final SecureRandom RAND = new SecureRandom();

    private Random() {

    }

    public static float next() {
        return RAND.nextFloat();
    }

    public static float next(float maxValue) {
        return RAND.nextFloat() * maxValue;
    }

    public static float next(float minValue, float maxValue) {
        return (RAND.nextFloat() * (maxValue - minValue)) + minValue;
    }

    public static int nextInt(int maxValue) {
        return RAND.nextInt(maxValue);
    }

}
