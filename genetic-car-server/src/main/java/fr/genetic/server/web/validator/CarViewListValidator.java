package fr.genetic.server.web.validator;

import fr.genetic.server.simulation.Team;
import fr.genetic.server.web.view.CarView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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
        if (count > 20) {
            throw new IllegalArgumentException(team + " - La liste ne doit pas contenir plus de 20 voitures ("+count+")");
        }
    }

}
