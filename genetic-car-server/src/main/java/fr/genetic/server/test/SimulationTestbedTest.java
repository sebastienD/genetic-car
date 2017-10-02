package fr.genetic.server.test;

import fr.genetic.server.simulation.CarDefinition;
import fr.genetic.server.simulation.Simulation;
import org.jbox2d.testbed.framework.TestbedTest;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.genetic.server.simulation.Simulation.DEFAULT_GENERATION_SIZE;

public class SimulationTestbedTest extends TestbedTest {

    @Override
    public void initTest(boolean b) {
        Simulation simulation = new Simulation(getWorld());
        simulation.runForTest(
                IntStream.range(0, DEFAULT_GENERATION_SIZE)
                .mapToObj(i -> CarDefinition.createRandomCar())
                .collect(Collectors.toList()));
    }

    @Override
    public String getTestName() {
        return "Genetic car";
    }
}