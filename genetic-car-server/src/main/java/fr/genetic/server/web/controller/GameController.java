package fr.genetic.server.web.controller;

import fr.genetic.server.game.Game;
import fr.genetic.server.game.Team;
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

    @RequestMapping(value="/game", method = RequestMethod.DELETE)
    void resetAllPlayers() {
        Game.clearGame();
    }

    @RequestMapping(value="/game", method = RequestMethod.POST)
    void createGame() {
        Game.createGame();
    }
}
