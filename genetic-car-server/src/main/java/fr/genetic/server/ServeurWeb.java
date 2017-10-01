package fr.genetic.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/**
 * http://localhost:8080/swagger-ui.html
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class ServeurWeb {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServeurWeb.class, args);
    }

}
