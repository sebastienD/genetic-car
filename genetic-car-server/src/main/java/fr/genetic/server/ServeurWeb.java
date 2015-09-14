package fr.genetic.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/swagger-ui.html
 */
@SpringBootApplication
public class ServeurWeb {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServeurWeb.class, args);
    }

}
