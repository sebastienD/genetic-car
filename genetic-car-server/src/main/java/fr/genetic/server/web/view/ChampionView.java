package fr.genetic.server.web.view;

import fr.genetic.server.simulation.Simulation;
import fr.genetic.server.simulation.Team;

public class ChampionView {

    public CarView car;
    public Statistic statistic;

    public ChampionView(Team team, Simulation simulation) {
        this.car = CarView.create(simulation.leader.car);
        this.statistic = new Statistic(team, simulation.nbRunSimulation);
    }

    public static class Statistic {
        public Team team;
        public int nbRunSimulation;

        public Statistic(Team team, int nbRunSimulation) {
            this.nbRunSimulation = nbRunSimulation;
            this.team = team;
        }
    }
}
