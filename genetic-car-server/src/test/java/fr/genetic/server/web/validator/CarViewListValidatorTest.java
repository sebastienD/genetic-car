package fr.genetic.server.web.validator;

import fr.genetic.server.DataFactory;
import fr.genetic.server.game.Team;
import fr.genetic.server.web.view.CarView;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CarViewListValidatorTest {

    @Test
    public void should_validate_cars() {
        CarView carView = DataFactory.carView();

        CarViewListValidator validator = new CarViewListValidator();
        validator.validate(Arrays.asList(carView), Team.BLUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_list_too_big() {
        List<CarView> cars = IntStream.range(0, 21)
                .mapToObj(i -> new CarView())
                .collect(Collectors.toList());

        CarViewListValidator validator = new CarViewListValidator();
        validator.validate(cars, Team.BLUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_list_contains_bad_cars() {
        CarView carView = DataFactory.carView();
        carView.chassis.vecteurs.set(5, 5.2F);

        List<CarView> cars = IntStream.range(0, 2)
                .mapToObj(i -> DataFactory.carView())
                .collect(Collectors.toList());

        cars.add(carView);

        CarViewListValidator validator = new CarViewListValidator();
        validator.validate(cars, Team.BLUE);
    }
}