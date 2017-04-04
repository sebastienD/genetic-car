package fr.genetic.client.java.api;

import java.util.ArrayList;
import java.util.List;

public class CarView {

    public Chassis chassis = new Chassis();
    public Wheel wheel1 = new Wheel();
    public Wheel wheel2 = new Wheel();

    public static class Chassis {
        public List<Float> vecteurs = new ArrayList<>();
        public float densite;

        @Override
        public String toString() {
            return "Chassis{" +
                    "vecteurs=" + vecteurs +
                    ", densite=" + densite +
                    '}';
        }
    }

    public static class Wheel {
        public float radius;
        public float density;
        public int vertex;

        @Override
        public String toString() {
            return "Wheel{" +
                    "radius=" + radius +
                    ", density=" + density +
                    ", vertex=" + vertex +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarView{" +
                "chassis=" + chassis +
                ", wheel1=" + wheel1 +
                ", wheel2=" + wheel2 +
                '}';
    }
}
