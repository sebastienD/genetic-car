package fr.seb.games.geneticcar;

import fr.seb.games.geneticcar.simulation.Simulation;
import fr.seb.games.geneticcar.simulation.Team;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sebastien on 20/01/2015.
 */
public class Game {

    private static Map<Team, Simulation> players = new ConcurrentHashMap<>();

    public static void clearAll() {
        players.clear();
    }

    public static void clear(Team team) {
        players.remove(team);
    }

    public static void createAllSimulation() {
        Arrays.asList(Team.values()).stream()
                .forEach(team -> {
                    Simulation simulation = new Simulation();
                    simulation.runSimulation(simulation.buildGenerationZero());
                    players.put(team, simulation);
                });
    }

    public static void createSimulation(Team team) {
        Simulation simulation = new Simulation();
        simulation.runSimulation(simulation.buildGenerationZero());
        players.put(team, simulation);
    }

    public static Map<Team, Simulation> players() {
        return players;
    }

    public static Simulation getSimulation(Team team) {
        return players.get(team);
    }
}
