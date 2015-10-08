package fr.genetic.client.java;

import fr.genetic.client.java.algo.Car;
import fr.genetic.client.java.api.CarScoreView;
import fr.genetic.client.java.api.CarView;
import fr.genetic.client.java.api.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class Launch implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launch.class);

    @Value("${genetic.server.host}")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    private Team team = Team.BLUE;


    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            doMyAlgo();
        } catch (RestClientException restException) {
            LOGGER.error(restException.getMessage());
        }
    }

    private List<CarScoreView> evaluate(List<CarView> cars) {
        String url = host + "/simulation/evaluate/" + team.name();
        // TODO game non créé
        return restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity(cars), new ParameterizedTypeReference<List<CarScoreView>>() {}).getBody();
    }

    protected void doMyAlgo() {
        List<CarView> cars = IntStream.range(0, 20)
                .mapToObj(i -> Car.random().toCarView())
                .collect(Collectors.toList());

        List<CarScoreView> carScores = evaluate(cars);

        CarScoreView champion = carScores.stream()
                .max((carScore1, carScore2) -> Float.compare(carScore1.score, carScore2.score))
                .get();

        LOGGER.info("Mon champion est {}", champion);
    }

}
