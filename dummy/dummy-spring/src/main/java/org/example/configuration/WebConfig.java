package org.example.configuration;

import org.example.controllers.api.HandlerFilterResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc // This is just to enable CORS calls for local development.
public class WebConfig implements WebMvcConfigurer {

    /** This is just to enable CORS calls for local development. */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerFilterResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
