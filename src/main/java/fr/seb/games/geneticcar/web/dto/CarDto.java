package fr.seb.games.geneticcar.web.dto;

import fr.seb.games.geneticcar.simulation.Car;
import fr.seb.games.geneticcar.simulation.Simulation;
import fr.seb.games.geneticcar.simulation.Team;

import java.util.List;

/**
 * Created by sebastien on 18/01/2015.
 */
public class CarDto {

    public Team team;
    public Chassi chassi;
    public Wheel wheel1;
    public Wheel wheel2;
    public float score;

    public CarDto() {

    }

    public static CarDto create(Team team, Car car) {
        CarDto carDto = new CarDto();
        carDto.score = car.getScore();
        carDto.team = team;
        return carDto;
    }

    public static class Chassi {
        public List<Float> vecteurs;
        public int densite;
    }

    public static class Wheel {
        public float rayon;
        public int densite;
        public int sommet;
    }
}
