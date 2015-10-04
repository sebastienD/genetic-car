package fr.genetic.client.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class Launch implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launch.class);

    @Value("genetic.server.host")
    private String host;

    private Team team = Team.BLUE;


    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
    }

    private String evaluationUrl() {
        return host + "/simulation/evaluate/" + team.name();
    }

    @Override
    public void run(String... args) throws Exception {
        final RestTemplate restTemplate = new RestTemplate();

        List<CarView> cars = IntStream.range(0, 20)
                .mapToObj(i -> new CarView())
                .collect(Collectors.toList());

        // TODO URL à corriger
        // TODO init des voitures
        List<CarScoreView> carScores = restTemplate.postForObject(evaluationUrl(), cars, List.class);

        Optional<CarScoreView> champion = carScores.stream()
                .max((o1, o2) -> Float.compare(o1.score, o2.score));

        LOGGER.info("Mon champion est {}", champion);
    }
}
