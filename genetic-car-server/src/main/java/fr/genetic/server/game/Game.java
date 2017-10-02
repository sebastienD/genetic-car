package fr.genetic.server.game;

import fr.genetic.server.simulation.CarDefinition;
import fr.genetic.server.simulation.Simulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.genetic.server.simulation.Simulation.DEFAULT_GENERATION_SIZE;

public class Game {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private static Map<Team, RunBoard> players = new ConcurrentHashMap<>();

    public static void clearGame() {
        LOGGER.info("Delete all simulation");
        players.clear();
    }

    public static void createGame() {
        LOGGER.info("Create all run board");
        Simulation simulation = new Simulation();
        Arrays.stream(Team.values())
                .forEach(team -> initForTeam(team, simulation));
        LOGGER.info("Run board created");
    }

    private static void initForTeam(Team team, Simulation simulation) {
        List<CarDefinition> cars = IntStream.range(0, DEFAULT_GENERATION_SIZE / 2)
                .mapToObj(i -> CarDefinition.createRandomCar())
                .collect(Collectors.toList());
        RunBoard runBoard = new RunBoard(simulation).runSimulation(cars);
        LOGGER.info("Simulation created for team {} ({})", team, runBoard.champion.getScore());
        players.put(team, runBoard);
    }

    public static RunBoard getRunBoard(Team team) {
        return players.get(team);
    }

    public static Map<Team, RunBoard> players() {
        return players;
    }
}
