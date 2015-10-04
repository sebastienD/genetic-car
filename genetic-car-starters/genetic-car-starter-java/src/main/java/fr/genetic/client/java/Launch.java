package fr.genetic.client.java;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class Launch implements CommandLineRunner {

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


        restTemplate.postForObject(evaluationUrl(), new CarView(), List.class);
    }
}
