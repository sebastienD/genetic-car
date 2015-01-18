package fr.seb.games.geneticcar.web;

import fr.seb.games.geneticcar.simulation.Car;
import fr.seb.games.geneticcar.simulation.CarDefinition;
import fr.seb.games.geneticcar.simulation.Simulation;
import fr.seb.games.geneticcar.simulation.Team;
import fr.seb.games.geneticcar.web.dto.CarDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by sebastien on 18/01/2015.
 */
@RestController
public class SimulationController {

    private static final Map<Team, Simulation> simulations = new ConcurrentHashMap<>();

    @RequestMapping(value = "/simulation/teams", method = RequestMethod.GET)
    Team[] teams() {
        return Team.values();
    }

    @RequestMapping(value="/simulation/evaluate/{team}", method = RequestMethod.POST)
    List<CarDto> evaluatePopulation(List<CarDto> defintions, @PathVariable("team") Team team) {
        return null;
    }

    @RequestMapping(value="/simulation/champions", method = RequestMethod.GET)
    List<CarDto> getChampions() {
        return simulations.entrySet().stream().map(entry -> {
            //entry.getKey()
            return CarDto.create(entry.getKey(), 1F);
        }).collect(Collectors.toList());
    }

    @RequestMapping(value="/simulation/champions/{team}", method = RequestMethod.GET)
    Car getChampion(@PathVariable("team") Team team) {
        return null;
    }
}
