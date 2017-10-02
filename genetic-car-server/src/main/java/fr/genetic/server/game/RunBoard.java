package fr.genetic.server.game;

import fr.genetic.server.simulation.Car;
import fr.genetic.server.simulation.CarDefinition;
import fr.genetic.server.simulation.Simulation;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class RunBoard {

    public Simulation simulation;
    public List<Car> cars;
    public Car champion;

    public int nbRunSimulation = 0;

    public RunBoard(Simulation simulation) {
        Objects.requireNonNull(simulation);
        this.simulation = simulation;
    }

    public RunBoard runSimulation(List<CarDefinition> definitions) {
        List<Car> cars = simulation.run(definitions);
        this.champion = findChampion(cars);
        this.cars = cars;
        this.nbRunSimulation ++;
        return this;
    }

    private Car findChampion(List<Car> cars) {
        return cars.stream()
                .max(Comparator.comparing(car -> car.getScore()))
                .orElse(champion);
    }
}
