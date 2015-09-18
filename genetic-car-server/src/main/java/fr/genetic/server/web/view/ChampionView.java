package fr.genetic.server.web.view;

import fr.genetic.server.simulation.Simulation;
import fr.genetic.server.simulation.Team;

public class ChampionView {

    public CarScoreView carScore;
    public Statistic statistic;

    public ChampionView(Team team, Simulation simulation) {
        this.carScore = CarScoreView.create(simulation.leader.car);
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
