package fr.genetic.client.java.api;

public class CarScoreView {
    public CarView car;
    public float score;

    @Override
    public String toString() {
        return "CarScoreView{" +
                "car=" + car +
                ", score=" + score +
                '}';
    }
}
