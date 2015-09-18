package fr.genetic.server.web.view;

import fr.genetic.server.simulation.Car;

public class CarScoreView {

    public CarView car;
    public float score;

    public static CarScoreView create(Car car) {
        CarScoreView carScoreView = new CarScoreView();
        carScoreView.car = CarView.create(car);
        carScoreView.score = car.getScore();
        return carScoreView;
    }
}
