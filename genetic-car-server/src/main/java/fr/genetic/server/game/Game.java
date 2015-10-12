package fr.genetic.server.game;

import fr.genetic.server.simulation.Simulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private static Map<Team, RunBoard> players = new ConcurrentHashMap<>();

    public static void clearGame() {
        LOGGER.info("delete all simulation");
        players.clear();
    }

    public static void createGame() {
        LOGGER.debug("create all run board");
        Simulation simulation = new Simulation();
        Arrays.stream(Team.values())
                .forEach(team -> initForTeam(team, simulation));
    }

    private static void initForTeam(Team team, Simulation simulation) {
        LOGGER.info("create simulation for team {}", team);
        players.put(team, new RunBoard(simulation).runSimulation(Simulation.buildGenerationZero()));
    }

    public static RunBoard getRunBoard(Team team) {
        RunBoard runBoard = players.get(team);
        if (runBoard == null) {
            throw new IllegalArgumentException("Attention, la partie n'est pas créée.");
        }
        return runBoard;
    }

    public static Map<Team, RunBoard> players() {
        return players;
    }
}
