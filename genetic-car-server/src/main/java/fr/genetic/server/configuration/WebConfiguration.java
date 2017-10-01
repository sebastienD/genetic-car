package fr.genetic.server.configuration;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class WebConfiguration {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {

        return (container -> {
            // TODO a tester: src/main/resource/public/error/404.html
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            container.addErrorPages(error404Page);
        });
    }

}
