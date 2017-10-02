package fr.genetic.server.test;

import fr.genetic.server.simulation.Car;
import fr.genetic.server.simulation.CarDefinition;
import fr.genetic.server.simulation.Simulation;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.genetic.server.simulation.Simulation.DEFAULT_GENERATION_SIZE;

public class SimulationTestbedTest extends TestbedTest {

    private Simulation simulation;
    private List<Car> cars;

    @Override
    public void initTest(boolean b) {
        simulation = new Simulation(getWorld());
        cars = simulation.runForTest(
                IntStream.range(0, DEFAULT_GENERATION_SIZE)
                .mapToObj(i -> CarDefinition.createRandomCar())
                .collect(Collectors.toList()));
    }

    @Override
    public synchronized void step(TestbedSettings settings) {
        if (!simulation.allCarsDead(cars)) {
            simulation.simulationStep(cars);
        }
        super.step(settings);
    }

    @Override
    public String getTestName() {
        return "Genetic car";
    }
}