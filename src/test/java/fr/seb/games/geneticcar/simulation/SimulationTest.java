package fr.seb.games.geneticcar.simulation;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.j2d.DebugDrawJ2D;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;
import org.jbox2d.testbed.framework.j2d.TestbedSidePanel;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

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