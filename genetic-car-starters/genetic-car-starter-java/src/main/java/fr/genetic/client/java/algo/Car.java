package fr.genetic.client.java.algo;

import fr.genetic.client.java.api.CarView;
import org.springframework.util.Assert;

import java.util.stream.IntStream;

public class Car {

    private float[] coords = new float[23];

    public static Car createFrom(CarView carView) {
        Assert.notNull(carView);

        Car car = new Car();
        IntStream.range(0, 16)
                .forEach(i -> car.coords[i] = carView.chassi.vecteurs.get(i));
        car.coords[16] = carView.chassi.densite;

        car.coords[17] = carView.wheel1.density;
        car.coords[18] = carView.wheel1.radius;
        car.coords[19] = carView.wheel1.vertex;

        car.coords[20] = carView.wheel2.density;
        car.coords[21] = carView.wheel2.radius;
        car.coords[22] = carView.wheel2.vertex;

        return car;
    }

    public static Car random() {
        Car car = new Car();
        car.coords[0] = Random.nextChassisAxis();
        car.coords[1] = 0F;
        car.coords[2] = Random.nextChassisAxis();
        car.coords[3] = Random.nextChassisAxis();
        car.coords[4] = 0F;
        car.coords[5] = Random.nextChassisAxis();
        car.coords[6] = -Random.nextChassisAxis();
        car.coords[7] = Random.nextChassisAxis();
        car.coords[8] = -Random.nextChassisAxis();
        car.coords[9] = 0F;
        car.coords[10] = -Random.nextChassisAxis();
        car.coords[11] = -Random.nextChassisAxis();
        car.coords[12] = 0;
        car.coords[13] = -Random.nextChassisAxis();
        car.coords[14] = Random.nextChassisAxis();
        car.coords[15] = -Random.nextChassisAxis();
        car.coords[16] = Random.nextChassisDensity();
        car.coords[17] = Random.nextWheelDensity();
        car.coords[18] = Random.nextWheelRadius();
        car.coords[19] = Random.nextVertex();
        car.coords[20] = Random.nextWheelDensity();
        car.coords[21] = Random.nextWheelRadius();
        car.coords[22] = Random.nextVertex();
        return car;
    }

    public CarView toCarView() {
        CarView carView = new CarView();

        IntStream.range(0, 16)
                .forEach(i -> carView.chassi.vecteurs.add(coords[i]));
        carView.chassi.densite = coords[16];

        carView.wheel1.density = coords[17];
        carView.wheel1.radius = coords[18];
        carView.wheel1.vertex = Float.valueOf(coords[19]).intValue();

        carView.wheel2.density = coords[20];
        carView.wheel2.radius = coords[21];
        carView.wheel2.vertex = Float.valueOf(coords[22]).intValue();

        return carView;
    }

}
