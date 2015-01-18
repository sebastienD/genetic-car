package fr.seb.games.geneticcar;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sebastien on 18/01/2015.
 */
@Configuration
@ComponentScan("fr.seb.games.geneticcar")
@EnableAutoConfiguration
@EnableSwagger
public class ServeurWeb {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(
                apiInfo()).includePatterns(".*");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Genetic car API", "API for Genetic car",
                "API terms of service", "sebastien.descamps@@gmail.com",
                "API Licence Type", "API License URL");
        return apiInfo;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServeurWeb.class, args);
    }
}
