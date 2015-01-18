package fr.seb.games.geneticcar.simulation;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by sebastien on 12/01/2015.
 */
public class CarDefinition {

    public static float MOTOR_SPEED = 20;

    private static float chassisMinAxis = 0.1F;

    private static float chassisMaxAxis = 1.1F;
    private static float chassisMinDensity = 30;

    private static float chassisMaxDensity = 300;
    private static float wheelMaxRadius = 0.5F;
    private static float wheelMinRadius = 0.2F;
    private static float wheelMaxDensity = 100;

    private static float wheelMinDensity = 40;

    private static int wheelCount = 2;

    public List<WheelDefinition> wheelDefinitions = new ArrayList<>();
    public float chassisDensity;
    public List<Vec2> vertexList = new ArrayList<>(8);

    public CarDefinition() {

    }

    public CarDefinition addWheel(float radius, float density) {
        this.wheelDefinitions.add(new WheelDefinition(radius, density));
        return this;
    }

    public WheelDefinition wheel1() {
        return wheelDefinitions.iterator().next();
    }

    public WheelDefinition wheel2() {
        return wheelDefinitions.iterator().next();
    }

    public static CarDefinition createRandomCar() {
        CarDefinition carDefinition = new CarDefinition();

        for (int i = 0; i < wheelCount; i++) {
            WheelDefinition wheelDefinition = new WheelDefinition();
            wheelDefinition.radius = Random.next(wheelMinRadius, wheelMaxRadius);
            wheelDefinition.density = Random.next(wheelMinDensity, wheelMaxDensity); 
            carDefinition.wheelDefinitions.add(wheelDefinition);
        }

        carDefinition.chassisDensity = Random.next(chassisMinDensity, chassisMaxDensity);

        carDefinition.vertexList = new ArrayList<>(8);
        carDefinition.vertexList.add(new Vec2(Random.next(chassisMinAxis, chassisMaxAxis), 0F));
        carDefinition.vertexList.add(new Vec2(Random.next(chassisMinAxis, chassisMaxAxis), Random.next(chassisMinAxis, chassisMaxAxis)));
        carDefinition.vertexList.add(new Vec2(0F, Random.next(chassisMinAxis, chassisMaxAxis)));
        carDefinition.vertexList.add(new Vec2(-Random.next(chassisMinAxis, chassisMaxAxis), Random.next(chassisMinAxis, chassisMaxAxis)));
        carDefinition.vertexList.add(new Vec2(-Random.next(chassisMinAxis, chassisMaxAxis), 0F));
        carDefinition.vertexList.add(new Vec2(-Random.next(chassisMinAxis, chassisMaxAxis), -Random.next(chassisMinAxis, chassisMaxAxis)));
        carDefinition.vertexList.add(new Vec2(0F, -Random.next(chassisMinAxis, chassisMaxAxis)));
        carDefinition.vertexList.add(new Vec2(Random.next(chassisMinAxis, chassisMaxAxis), -Random.next(chassisMinAxis, chassisMaxAxis)));

        List<Integer> left = Stream.of(0,1,2,3,4,5,6,7).collect(Collectors.toList());

        int indexOfNext = Random.nextInt(left.size());
        carDefinition.wheel1().vertex = left.remove(indexOfNext);

        indexOfNext = Random.nextInt(left.size());
        carDefinition.wheel2().vertex = left.remove(indexOfNext);

        
        return carDefinition;
    }


    public static class WheelDefinition {

        public float radius;
        public float density;
        public int vertex;

        public WheelDefinition() {}

        public WheelDefinition(float radius, float density) {
            this.radius = radius;
            this.density = density;
        }

        @Override
        public String toString() {
            return "WheelDefinition{" +
                    "radius=" + radius +
                    ", density=" + density +
                    ", vertex=" + vertex +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarDefinition{" +
                "wheelDefinitions=" + wheelDefinitions +
                ", chassisDensity=" + chassisDensity +
                ", vertexList=" + vertexList +
                '}';
    }
}
