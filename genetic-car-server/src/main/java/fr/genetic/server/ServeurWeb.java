package fr.genetic.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ServeurWeb {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServeurWeb.class, args);
    }

}
