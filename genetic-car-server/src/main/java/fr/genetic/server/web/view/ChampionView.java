package fr.genetic.server.web.view;

import fr.genetic.server.game.RunBoard;
import fr.genetic.server.simulation.Simulation;
import fr.genetic.server.game.Team;

public class ChampionView {

    public CarScoreView carScore;
    public Statistic statistic;

    public ChampionView(Team team, RunBoard runBoard) {
        this.carScore = CarScoreView.create(runBoard.champion);
        this.statistic = new Statistic(team, runBoard.nbRunSimulation);
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
