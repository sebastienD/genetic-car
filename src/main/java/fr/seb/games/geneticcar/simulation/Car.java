package fr.seb.games.geneticcar.simulation;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.JointDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastien on 12/01/2015.
 */
public class Car {

    private static float chassisMinAxis = 0.1F;
    private static float chassisMaxAxis = 1.1F;

    private static float chassisMinDensity = 30;
    private static float chassisMaxDensity = 300;

    private static float wheelMaxRadius = 0.5F;
    private static float wheelMinRadius = 0.2F;
    private static float wheelMaxDensity = 100;
    private static float wheelMinDensity = 40;

    private float motorSpeed = 20;

    private static int wheelCount = 2;

    private List<Wheel> wheels = new ArrayList<>();


    public Car() {

    }

    public static Car createRandomCar() {
        Car car = new Car();

        for (int i = 0; i < wheelCount; i++) {
            Wheel wheel = new Wheel();
            wheel.radius = new Float(Math.random() * wheelMaxRadius + wheelMinRadius);
            wheel.density = new Float(Math.random() * wheelMaxDensity + wheelMinDensity);
            car.wheels.add(wheel);
        }

        return car;
    }


    public static class Wheel {

        public float radius;
        public float density;
        public Vec2 vertex;

    }
}
