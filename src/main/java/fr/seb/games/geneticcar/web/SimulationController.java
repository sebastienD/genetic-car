package fr.seb.games.geneticcar.web;

import fr.seb.games.geneticcar.Game;
import fr.seb.games.geneticcar.simulation.Car;
import fr.seb.games.geneticcar.simulation.Team;
import fr.seb.games.geneticcar.web.dto.CarDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sebastien on 18/01/2015.
 */
@RestController
public class SimulationController {

    @RequestMapping(value="/simulation/evaluate/{team}", method = RequestMethod.POST)
    List<CarDto> evaluatePopulation(List<CarDto> cars, @PathVariable("team") Team team) {
        return null;
    }

    @RequestMapping(value="/simulation/champions", method = RequestMethod.GET)
    List<CarDto> getChampions() {
        return Game.players().entrySet().stream().map(entry -> {
            //entry.getKey()
            return CarDto.create(entry.getKey(), entry.getValue().leader.car);
        }).collect(Collectors.toList());
    }

    @RequestMapping(value="/simulation/champions/{team}", method = RequestMethod.GET)
    CarDto getChampion(@PathVariable("team") Team team) {
        Car car = Game.getSimulation(team).leader.car;
        return CarDto.create(team, car);
    }

}
