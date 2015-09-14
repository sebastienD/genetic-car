package fr.genetic.server.test;

import org.jbox2d.testbed.framework.*;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import javax.swing.*;

/**
 * Created by Sebastien on 27/01/2015.
 */
public class SimulationTest {

    public static void main(String[] args) {
        TestbedModel model = new TestbedModel();

        TestList.populateModel(model);
        model.addCategory("Algo Genetic");
        model.addTest(new LaunchSimulationTest());

        model.getSettings().addSetting(new TestbedSetting("My Range Setting", TestbedSetting.SettingType.ENGINE, 10, 0, 20));

        TestbedPanel panel = new TestPanelJ2D(model);

        JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED); // put both into our testbed frame

        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
