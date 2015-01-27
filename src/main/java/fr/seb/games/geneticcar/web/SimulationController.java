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

}
