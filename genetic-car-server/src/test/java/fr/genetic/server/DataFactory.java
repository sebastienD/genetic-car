package fr.genetic.server;

import fr.genetic.server.simulation.CarDefinition;
import fr.genetic.server.web.view.CarView;

public class DataFactory {

    public static CarView carView() {
        CarView carView = new CarView();

        carView.chassis = new CarView.Chassis();
        carView.chassis.densite = 30F;
        carView.chassis.vecteurs.add(0.1F);
        carView.chassis.vecteurs.add(0F);
        carView.chassis.vecteurs.add(0.8F);
        carView.chassis.vecteurs.add(0.3F);
        carView.chassis.vecteurs.add(0F);
        carView.chassis.vecteurs.add(0.5F);
        carView.chassis.vecteurs.add(-1F);
        carView.chassis.vecteurs.add(1.05F);
        carView.chassis.vecteurs.add(-0.4F);
        carView.chassis.vecteurs.add(0F);
        carView.chassis.vecteurs.add(-0.1F);
        carView.chassis.vecteurs.add(-0.7F);
        carView.chassis.vecteurs.add(0F);
        carView.chassis.vecteurs.add(-0.6F);
        carView.chassis.vecteurs.add(1.09F);
        carView.chassis.vecteurs.add(-1F);

        carView.wheel1 = new CarDefinition.WheelDefinition();
        carView.wheel1.radius = 0.2F;
        carView.wheel1.density = 50F;
        carView.wheel1.vertex = 0;

        carView.wheel2 = new CarDefinition.WheelDefinition();
        carView.wheel2.radius = 0.5F;
        carView.wheel2.density = 100F;
        carView.wheel2.vertex = 5;

        return carView;
    }
}
