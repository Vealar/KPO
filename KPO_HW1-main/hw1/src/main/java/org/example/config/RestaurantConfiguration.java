package org.example.config;

import org.example.condition.ModeCondition;
import org.example.models.Burrito;
import org.example.models.Kebab;
import org.example.models.Shawarma;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example")
public class RestaurantConfiguration {
    @Bean(name = "kebabShawarmaBean")
    public Shawarma kebabShawarma(){
        return new Kebab();
    }
    @Bean(name = "burritoShawarmaBean")
    @Conditional(ModeCondition.class)
    public Shawarma burritoShawarma(){
        return new Burrito();
    }

}
