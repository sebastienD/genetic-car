package fr.genetic.server.web;

import fr.genetic.server.game.Game;
import fr.genetic.server.simulation.Team;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sebastien on 20/01/2015.
 */
@RestController
public class GameController {

    @RequestMapping(value = "/game/teams", method = RequestMethod.GET)
    Team[] teams() {
        return Team.values();
    }

    @RequestMapping(value="/game/clear/{team}", method = RequestMethod.POST)
    void resetPopulationForTeam(@PathVariable("team") Team team) {
        Game.clear(team);
    }

    @RequestMapping(value="/game/clear", method = RequestMethod.POST)
    void resetAllPopulation() {
        Game.clearAll();
    }

    @RequestMapping(value="/game/createSimulation/{team}", method = RequestMethod.POST)
    void createSimulationForTeam(@PathVariable("team") Team team) {
        Game.createSimulation(team);
    }

    @RequestMapping(value="/game/createSimulation", method = RequestMethod.POST)
    void createSimulation() {
        Game.createAllSimulation();
    }
}
