package fr.seb.games.geneticcar.test;

import fr.seb.games.geneticcar.simulation.Simulation;
import org.jbox2d.testbed.framework.TestbedTest;

/**
 * Created by Sebastien on 27/01/2015.
 */
public class LaunchSimulationTest extends TestbedTest {

    @Override
    public void initTest(boolean b) {
        Simulation simulation = new Simulation(getWorld());
        simulation.runSimulationForTest(simulation.buildGenerationZero());
    }

    @Override
    public String getTestName() {
        return "Genetic car";
    }
}
