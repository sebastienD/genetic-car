package fr.seb.games.geneticcar.simulation;

import org.junit.Test;

public class SimulationTest {

    @Test
    public void should_work() {
        Simulation simulation = new Simulation();
        simulation.runSimulation(simulation.buildGenerationZero());
        simulation.showAllScores();
    }

}