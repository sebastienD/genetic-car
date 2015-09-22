package fr.genetic.server.web.controller;

import fr.genetic.server.game.Game;
import fr.genetic.server.simulation.Team;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @RequestMapping(value = "/game/teams", method = RequestMethod.GET)
    Team[] teams() {
        return Team.values();
    }

    @RequestMapping(value="/game/{team}", method = RequestMethod.DELETE)
    void resetPopulationForTeam(@PathVariable("team") Team team) {
        Game.clear(team);
    }

    @RequestMapping(value="/game", method = RequestMethod.DELETE)
    void resetAllPopulation() {
        Game.clearAll();
    }

    @RequestMapping(value="/game/{team}", method = RequestMethod.POST)
    void createSimulationForTeam(@PathVariable("team") Team team) {
        Game.createSimulation(team);
    }

    @RequestMapping(value="/game", method = RequestMethod.POST)
    void createSimulation() {
        Game.createAllSimulation();
    }
}
