package fr.seb.games.geneticcar.web.dto;

import fr.seb.games.geneticcar.simulation.CarDefinition;
import fr.seb.games.geneticcar.simulation.Team;

import javax.smartcardio.Card;
import java.util.List;

/**
 * Created by sebastien on 18/01/2015.
 */
public class CarDto {

    public float score;
    public List<Float> defintion;
    public Team team;

    public CarDto() {

    }

    public static CarDto create(Team team, float score) {
        CarDto car = new CarDto();
        car.score = score;
        car.team = team;
        return car;
    }
}
