package fr.genetic.client.java;

import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Car {

    private float[] coords = new float[21];
    private int[] wheelPoints = new int[2];

    public Car(CarView carView) {
        Assert.notNull(carView);

        coords[0] = carView.chassi.densite;
        IntStream.range(0, carView.chassi.vecteurs.size()) // 16
                .forEach(i -> coords[i + 1] = carView.chassi.vecteurs.get(i));

        coords[17] = carView.wheel1.density;
        coords[18] = carView.wheel1.radius;
        coords[19] = carView.wheel2.density;
        coords[20] = carView.wheel2.radius;

        wheelPoints[0] = carView.wheel1.vertex;
        wheelPoints[1] = carView.wheel2.vertex;
    }

    public CarView toCarView() {
        CarView carView = new CarView();

        carView.chassi.densite = coords[0];
        IntStream.range(0, 17)
                .forEach(i -> carView.chassi.vecteurs.add(coords[i + 1]));

        carView.wheel1.vertex = wheelPoints[0];
        carView.wheel1.density = coords[17];
        carView.wheel1.radius = coords[18];

        carView.wheel2.vertex = wheelPoints[1];
        carView.wheel2.density = coords[19];
        carView.wheel2.radius = coords[20];

        return carView;
    }

}
