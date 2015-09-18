package fr.genetic.server.web.dto;

import fr.genetic.server.simulation.Simulation;
import fr.genetic.server.simulation.Team;

public class ChampionDto {

    public CarDto car;
    public Statistic statistic;

    public ChampionDto(Team team, Simulation simulation) {
        this.car = CarDto.create(team, simulation.leader.car);
        this.statistic = new Statistic(simulation.nbRunSimulation);
    }

    public static class Statistic {
        public int nbRunSimulation;

        public Statistic(int nbRunSimulation) {
            this.nbRunSimulation = nbRunSimulation;
        }
    }
}
