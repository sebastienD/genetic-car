package fr.genetic.server.simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.genetic.server.web.view.CarScoreView;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SimulationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationTest.class);

    @Test
    public void should_generate_random_cars() {
        Simulation simulation = new Simulation();
        List<Car> cars = simulation.run(Arrays.asList(CarDefinition.createRandomCar()));
        cars.stream()
                .forEach(car -> LOGGER.info("score car {} : {}", car.getScore(), car));
    }

    @Test
    public void should_generate_best_car() {
        Simulation simulation = new Simulation();
        List<Car> cars = simulation.run(Arrays.asList(CarDefinition.createMyBestCar()));
        cars.stream()
                .forEach(car -> LOGGER.info("score car {} : {}", car.getScore(), car));
    }

    @Test
    @Ignore
    public void should_generate_same_score_when_car_doesnt_change() throws IOException {
        Simulation simulation = new Simulation();
        ObjectMapper mapper = new ObjectMapper();

        String def1 = "{\"car\":{\"chassis\":{\"vecteurs\":[1.0524477,0,0.48183152,0.56480825,0,0.3274586,-0.3776758,0.5775818,-0.55395633,0,-0.8949374,-0.12054062,0,-0.8672349,0.46732175,-0.28825176],\"densite\":79.377144},\"wheel1\":{\"radius\":0.44368792,\"density\":64.14404,\"vertex\":2},\"wheel2\":{\"radius\":0.40008456,\"density\":95.26903,\"vertex\":5}},\"score\":63}";
        CarScoreView val1 = mapper.readValue(def1, CarScoreView.class);
        Car car1 = simulation.run(Arrays.asList(val1.car.toCarDefintion())).get(0);

        String def2 = "{\"car\":{\"chassis\":{\"vecteurs\":[1.0524477,0,0.48183152,0.56480825,0,0.3274586,-0.3776758,0.5775818,-0.55395633,0,-0.8949374,-0.12054062,0,-0.8672349,0.46732175,-0.28825176],\"densite\":79.377144},\"wheel1\":{\"radius\":0.44368792,\"density\":64.14404,\"vertex\":2},\"wheel2\":{\"radius\":0.40008456,\"density\":95.26903,\"vertex\":5}},\"score\":42}";
        CarScoreView val2 = mapper.readValue(def2, CarScoreView.class);
        Car car2 = simulation.run(Arrays.asList(val2.car.toCarDefintion())).get(0);

        IntStream.range(0, 50)
                .mapToObj(i -> simulation.run(Arrays.asList(val2.car.toCarDefintion())).get(0).getScore())
                .forEach(score -> System.out.println("score: "+score));


        Assertions.assertThat(car1.getScore()).isEqualTo(val1.score);
        Assertions.assertThat(car2.getScore()).isEqualTo(val2.score);
    }

}