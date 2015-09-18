package fr.genetic.server.web.dto;

import fr.genetic.server.simulation.Car;
import fr.genetic.server.simulation.CarDefinition;
import fr.genetic.server.simulation.Team;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;

public class CarDto {

    public Team team;
    public Chassi chassi;
    public CarDefinition.WheelDefinition wheel1;
    public CarDefinition.WheelDefinition wheel2;
    public float score;

    public CarDto() {

    }

    public static CarDto create(Team team, Car car) {
        CarDto carDto = new CarDto();
        carDto.score = car.getScore();
        carDto.team = team;
        carDto.chassi = Chassi.createFromDefinition(car.carDefinition);
        carDto.wheel1 = car.carDefinition.wheelDefinition1;
        carDto.wheel2 = car.carDefinition.wheelDefinition2;
        return carDto;
    }

    public CarDefinition toCarDefintion() {
        CarDefinition carDefinition = new CarDefinition();
        carDefinition.wheelDefinition1 = wheel1;
        carDefinition.wheelDefinition2 = wheel2;
        carDefinition.chassisDensity = chassi.densite;
        carDefinition.vertexList = chassi.toVecteurs();
        return carDefinition;
    }

    public static class Chassi {
        public List<Float> vecteurs = new ArrayList<>();
        public float densite;

        public static Chassi createFromDefinition(CarDefinition defintion) {
            Chassi chassi = new Chassi();
            defintion.vertexList.stream().forEach(chassi::addVecteur);
            chassi.densite = defintion.chassisDensity;
            return chassi;
        }

        public Chassi addVecteur(Vec2 vec) {
            vecteurs.add(vec.x);
            vecteurs.add(vec.y);
            return this;
        }

        public List<Vec2> toVecteurs() {
            List<Vec2> vec2liste = new ArrayList<>(vecteurs.size()/2);

            int loop = 0;
            boolean isAbscisse = true;
            for (Float coord : vecteurs) {
                if (isAbscisse) {
                    Vec2 vec2 = new Vec2();
                    vec2.x = coord;
                    vec2liste.add(vec2);
                    isAbscisse = false;
                } else {
                    vec2liste.get(loop).y = coord;
                    loop++;
                    isAbscisse = true;
                }
            }
            return vec2liste;
        }
    }

}
