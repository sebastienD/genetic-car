package fr.genetic.client.java;

import java.util.ArrayList;
import java.util.List;

public class CarView {

    public Chassi chassi;
    public Wheel wheel1;
    public Wheel wheel2;

    public static class Chassi {
        public List<Float> vecteurs = new ArrayList<>();
        public float densite;

        @Override
        public String toString() {
            return "Chassi{" +
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
                "chassi=" + chassi +
                ", wheel1=" + wheel1 +
                ", wheel2=" + wheel2 +
                '}';
    }
}
