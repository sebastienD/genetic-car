package fr.genetic.server.game;

import fr.genetic.server.simulation.Simulation;
import fr.genetic.server.simulation.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private static Map<Team, Simulation> players = new ConcurrentHashMap<>();

    public static void clearAll() {
        LOGGER.info("delete all simulation");
        players.clear();
    }

    public static void clear(Team team) {
        LOGGER.info("delete simulation for team {}", team);
        players.remove(team);
    }

    public static void createAllSimulation() {
        LOGGER.debug("create all simulations");
        Arrays.stream(Team.values())
                .forEach(Game::createSimulation);
    }

    public static void createSimulation(Team team) {
        LOGGER.info("create simulation for team {}", team);
        Simulation simulation = new Simulation();
        simulation.runSimulation(simulation.buildGenerationZero());
        players.put(team, simulation);
    }

    public static Map<Team, Simulation> players() {
        return players;
    }

    public static Simulation getSimulation(Team team) {
        Simulation simulation = players.get(team);
        if (simulation == null) {
            throw new IllegalArgumentException("Attention, la partie n'est pas créée.");
        }
        return simulation;
    }
}
