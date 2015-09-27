package fr.genetic.server.web.validator;

import fr.genetic.server.simulation.CarDefinition;
import fr.genetic.server.simulation.Team;
import fr.genetic.server.web.view.CarView;
import org.junit.Test;

import java.util.Arrays;

public class CarViewListValidatorTest {

    @Test
    public void should_validate_cars() {
        CarView carView = new CarView();

        carView.chassi = new CarView.Chassi();
        carView.chassi.densite = 30F;
        carView.chassi.vecteurs.add(0.1F);
        carView.chassi.vecteurs.add(0F);
        carView.chassi.vecteurs.add(0.8F);
        carView.chassi.vecteurs.add(0.3F);
        carView.chassi.vecteurs.add(0F);
        carView.chassi.vecteurs.add(0.5F);
        carView.chassi.vecteurs.add(-1F);
        carView.chassi.vecteurs.add(1.05F);
        carView.chassi.vecteurs.add(-0.4F);
        carView.chassi.vecteurs.add(0F);
        carView.chassi.vecteurs.add(-0.1F);
        carView.chassi.vecteurs.add(-0.7F);
        carView.chassi.vecteurs.add(0F);
        carView.chassi.vecteurs.add(-0.6F);
        carView.chassi.vecteurs.add(1.09F);
        carView.chassi.vecteurs.add(-1F);

        carView.wheel1 = new CarDefinition.WheelDefinition();
        carView.wheel1.radius = 0.2F;
        carView.wheel1.density = 50F;
        carView.wheel1.vertex = 0;

        carView.wheel2 = new CarDefinition.WheelDefinition();
        carView.wheel2.radius = 0.5F;
        carView.wheel2.density = 100F;
        carView.wheel2.vertex = 5;

        CarViewListValidator validator = new CarViewListValidator();
        validator.validate(Arrays.asList(carView), Team.BLUE);
    }


    @Test
    public void should_throw_exception_when_list_too_big() {

    }

    @Test
    public void should_throw_exception_when_list_contains_bad_cars() {

    }
}