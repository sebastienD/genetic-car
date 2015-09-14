package fr.genetic.server.configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("desactivated")
@Configuration
@EnableSwagger
public class SwaggerConfiguration {

    /*
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
    */
}
