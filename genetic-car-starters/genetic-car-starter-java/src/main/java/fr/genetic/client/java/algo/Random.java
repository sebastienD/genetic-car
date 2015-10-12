package fr.genetic.client.java.algo;

import java.security.SecureRandom;

/**
 * Created by sebastien on 18/01/2015.
 */
public class Random {

    private static final float CHASSIS_MIN_AXIS = 0.1F;
    private static final float CHASSIS_MAX_AXIS = 1.1F;

    private static final float CHASSIS_MIN_DENSITY = 30F;
    private static final float CHASSIS_MAX_DENSITY = 300F;

    private static final float WHEEL_MIN_RADIUS = 0.2F;
    private static final float WHEEL_MAX_RADIUS = 0.5F;

    private static final float WHEEL_MIN_DENSITY = 40F;
    private static final float WHEEL_MAX_DENSITY = 100F;

    private static final int VERTEX_MAX_VALUE = 7;

    private Random() {

    }

    private static float next(float minValue, float maxValue) {
        return (new SecureRandom().nextFloat() * (maxValue - minValue)) + minValue;
    }

    public static float nextChassisAxis() {
        return next(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS);
    }

    public static float nextChassisDensity() {
        return next(CHASSIS_MIN_DENSITY, CHASSIS_MAX_DENSITY);
    }

    public static float nextWheelRadius() {
        return next(WHEEL_MIN_RADIUS, WHEEL_MAX_RADIUS);
    }

    public static float nextWheelDensity() {
        return next(WHEEL_MIN_DENSITY, WHEEL_MAX_DENSITY);
    }

    public static int nextVertex() {
        return new SecureRandom().nextInt(VERTEX_MAX_VALUE + 1);
    }

}
