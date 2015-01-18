package fr.seb.games.geneticcar;

import fr.seb.games.geneticcar.web.SimulationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Created by sebastien on 18/01/2015.
 */
@EnableAutoConfiguration
public class ServeurWeb {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServeurWeb.class, args);
    }
}
