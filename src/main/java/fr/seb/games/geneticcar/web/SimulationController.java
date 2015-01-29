package fr.seb.games.geneticcar.web;

import fr.seb.games.geneticcar.game.Game;
import fr.seb.games.geneticcar.simulation.Car;
import fr.seb.games.geneticcar.simulation.CarDefinition;
import fr.seb.games.geneticcar.simulation.Simulation;
import fr.seb.games.geneticcar.simulation.Team;
import fr.seb.games.geneticcar.web.dto.CarDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sebastien on 18/01/2015.
 */
@RestController
public class SimulationController {

    @RequestMapping(value="/simulation/evaluate/{team}", method = RequestMethod.POST)
    List<CarDto> evaluatePopulation(@RequestBody ArrayList<CarDto> carsDto, @PathVariable("team") Team team) {
        validate(carsDto);

        List<CarDefinition> definitions = carsDto.stream()
                .map(carDto -> carDto.toCarDefintion())
                .collect(Collectors.toList());

        Simulation simulation = Game.getSimulation(team);
        simulation.runSimulation(definitions);

        return simulation.allCars.stream()
                .map(car -> CarDto.create(team, car))
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/simulation/champions", method = RequestMethod.GET)
    List<CarDto> getChampions() {
        return Game.players().entrySet().stream()
                .map(entry -> CarDto.create(entry.getKey(), entry.getValue().leader.car))
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/simulation/champions/{team}", method = RequestMethod.GET)
    CarDto getChampion(@PathVariable("team") Team team) {
        Car car = Game.getSimulation(team).leader.car;
        return CarDto.create(team, car);
    }

    private void validate(ArrayList<CarDto> carsDto) {
        carsDto.forEach(carDto -> validate(carDto));
    }

    private void validate(CarDto carDto) {
        validate(carDto.wheel2);
        validate(carDto.wheel1);
        validate(carDto.chassi);
    }

    private void validate(CarDto.Chassi chassi) {
        Float[] chassiTab = chassi.vecteurs.toArray(new Float[]{});
        validateRangeCoordChassi(Math.abs(chassiTab[0]));
        mustBePositif(chassiTab[0]);
        mustBeZero(chassiTab[1]);
        validateRangeCoordChassi(Math.abs(chassiTab[2]));
        mustBePositif(chassiTab[2]);
        validateRangeCoordChassi(Math.abs(chassiTab[3]));
        mustBePositif(chassiTab[3]);
        mustBeZero(chassiTab[4]);
        validateRangeCoordChassi(Math.abs(chassiTab[5]));
        mustBePositif(chassiTab[5]);
        validateRangeCoordChassi(Math.abs(chassiTab[6]));
        mustBeNegatif(chassiTab[6]);
        validateRangeCoordChassi(Math.abs(chassiTab[7]));
        mustBePositif(chassiTab[7]);
        validateRangeCoordChassi(Math.abs(chassiTab[8]));
        mustBeNegatif(chassiTab[8]);
        mustBeZero(chassiTab[9]);
        validateRangeCoordChassi(Math.abs(chassiTab[10]));
        mustBeNegatif(chassiTab[10]);
        validateRangeCoordChassi(Math.abs(chassiTab[11]));
        mustBeNegatif(chassiTab[11]);
        mustBeZero(chassiTab[12]);
        validateRangeCoordChassi(Math.abs(chassiTab[13]));
        mustBeNegatif(chassiTab[13]);
        validateRangeCoordChassi(Math.abs(chassiTab[14]));
        mustBePositif(chassiTab[14]);
        validateRangeCoordChassi(Math.abs(chassiTab[15]));
        mustBeNegatif(chassiTab[15]);

        if (chassi.densite < 30F || chassi.densite > 300F) {
            throw new RuntimeException("La densite du chassi n'est pas comprise entre 30 et 300 : "+chassi.densite);
        }
    }

    private void mustBeNegatif(Float coord) {
        if (coord > 0) {
            throw new RuntimeException("La coordonnee doit être positive : "+ coord);
        }
    }

    private void mustBePositif(Float coord) {
        if (coord < 0) {
            throw new RuntimeException("La coordonnee doit être positive : "+coord);
        }
    }

    private void mustBeZero(Float coord) {
        if (coord != 0) {
            throw new RuntimeException("La coordonnee doit valeur 0 : "+ coord);
        }
    }

    private void validateRangeCoordChassi(Float coord) {
        if (coord < 0.1F || coord > 1.1F) {
            throw new RuntimeException("La coordonnee n'est pas comprise entre 0.1 et 1.1 : "+coord);
        }
    }

    private void validate(CarDefinition.WheelDefinition wheel) {
        if (wheel.radius < 0.2 || wheel.radius > 0.5) {
            throw new RuntimeException("Le rayon doit être compris entre 0.2 et 0.5 : "+wheel.radius);
        }
        if (wheel.density < 40 || wheel.density > 100) {
            throw new RuntimeException("La densite doit être comprise entre 40 et 100 : "+ wheel.density);
        }
        if (wheel.vertex < 0 || wheel.vertex > 7) {
            throw new RuntimeException("Le sommet doit être compris entre 0 et 7 : "+ wheel.vertex);
        }

    }

}
