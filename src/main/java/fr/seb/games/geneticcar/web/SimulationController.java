package fr.seb.games.geneticcar.web;

import fr.seb.games.geneticcar.simulation.Car;
import fr.seb.games.geneticcar.simulation.CarDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sebastien on 18/01/2015.
 */
@RestController
public class SimulationController {

    @RequestMapping(value = "/simulation/teams", method = RequestMethod.GET)
    List<String> teams() {
        return null;
    }

    @RequestMapping(value="/simulation/evaluate/{teamId}", method = RequestMethod.POST)
    List<Car> evaluatePopulation(List<CarDefinition> defintions, @PathVariable("teamId") String teamId) {
        return null;
    }

    @RequestMapping(value="/simulation/champions", method = RequestMethod.GET)
    List<Car> getChampions() {
        return null;
    }

    @RequestMapping(value="/simulation/champions/{teamId}", method = RequestMethod.GET)
    Car getChampion(@PathVariable("teamId") String teamId) {
        return null;
    }
}
