package fr.genetic.server.web.view;

import fr.genetic.server.simulation.Car;
import fr.genetic.server.simulation.CarDefinition;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;

public class CarView {

    public Chassis chassis;
    public CarDefinition.WheelDefinition wheel1;
    public CarDefinition.WheelDefinition wheel2;

    public static CarView create(Car car) {
        CarView carView = new CarView();
        carView.chassis = Chassis.createFromDefinition(car.carDefinition);
        carView.wheel1 = car.carDefinition.wheelDefinition1;
        carView.wheel2 = car.carDefinition.wheelDefinition2;
        return carView;
    }

    public CarDefinition toCarDefintion() {
        CarDefinition carDefinition = new CarDefinition();
        carDefinition.wheelDefinition1 = wheel1;
        carDefinition.wheelDefinition2 = wheel2;
        carDefinition.chassisDensity = chassis.densite;
        carDefinition.vertexList = chassis.toVecteurs();
        return carDefinition;
    }

    public static class Chassis {
        public List<Float> vecteurs = new ArrayList<>();
        public float densite;

        public static Chassis createFromDefinition(CarDefinition defintion) {
            Chassis chassis = new Chassis();
            defintion.vertexList.stream().forEach(chassis::addVecteur);
            chassis.densite = defintion.chassisDensity;
            return chassis;
        }

        public Chassis addVecteur(Vec2 vec) {
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
