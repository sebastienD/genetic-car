package fr.genetic.server.simulation;

import org.junit.Test;

public class SimulationTest {

    @Test
    public void should_generate_random_cars() {
        Simulation simulation = new Simulation();
        simulation.runSimulation(simulation.buildGenerationZero());
        simulation.showAllScores();
    }

    @Test
    public void should_generate_best_car() {
        Simulation simulation = new Simulation();
        simulation.runSimulation(simulation.buildBestGenerationZero());
        simulation.showAllScores();
    }
}