package fr.genetic.server.simulation;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class SimulationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationTest.class);

    @Test
    public void should_generate_random_cars() {
        Simulation simulation = new Simulation();
        List<Car> cars = simulation.runSimulation(Arrays.asList(CarDefinition.createRandomCar()));
        cars.stream()
                .forEach(car -> LOGGER.info("score car {} : {}", car.getScore(), car));
    }

    @Test
    public void should_generate_best_car() {
        Simulation simulation = new Simulation();
        List<Car> cars = simulation.runSimulation(Arrays.asList(CarDefinition.createMyBestCar()));
        cars.stream()
                .forEach(car -> LOGGER.info("score car {} : {}", car.getScore(), car));
    }

    @Test
    public void temp () {
        String message = String.format("%s - la liste ne doit pas contenir plus de %s voitures (%s)", "team", 20, 1);
        System.out.println(message);
    }
}