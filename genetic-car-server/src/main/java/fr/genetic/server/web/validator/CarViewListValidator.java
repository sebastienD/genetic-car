package fr.genetic.server.web.validator;

import fr.genetic.server.game.Team;
import fr.genetic.server.web.view.CarView;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static fr.genetic.server.simulation.Simulation.DEFAULT_GENERATION_SIZE;

@Component
public class CarViewListValidator {

    private CarViewValidator carValidator;

    public CarViewListValidator() {
        this.carValidator = new CarViewValidator();
    }

    public void validate(List<CarView> carViewList, Team team) {
        if (!CollectionUtils.isEmpty(carViewList)) {
            validateSize(carViewList, team);
            validateCars(carViewList, team);
        }
    }

    private void validateCars(List<CarView> carViewList, Team team) {
        String errors = carViewList.stream()
                .map(carView -> carValidator.validate(carView))
                .flatMap(matters -> matters.stream())
                .collect(Collectors.joining(","));

        if (!StringUtils.isEmpty(errors)) {
            throw new IllegalArgumentException(team + " - " + errors);
        }
    }

    private void validateSize(List<CarView> carViewList, Team team) {
        long count = carViewList.stream().count();
        if (count > DEFAULT_GENERATION_SIZE) {
            String message = String.format("%s - la liste ne doit pas contenir plus de %s voitures (%s)", team, DEFAULT_GENERATION_SIZE, count);
            throw new IllegalArgumentException(message);
        }
    }

}
