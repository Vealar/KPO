package org.example;

import org.example.config.RestaurantConfiguration;
import org.example.models.Burrito;
import org.example.models.Gyros;
import org.example.models.Kebab;
import org.example.models.Shawarma;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Conditional;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        ApplicationContext manager = new AnnotationConfigApplicationContext(RestaurantConfiguration.class);
        //@Component
        var gyros = manager.getBean("gyros", Gyros.class);
        System.out.println(gyros.getTasty());

        //@Configuration + @Bean
        Kebab kebab = (Kebab) manager.getBean("kebabShawarmaBean");
        System.out.println(kebab.getTasty());

        //@Conditional
        try {
            Burrito burrito = (Burrito) manager.getBean("burritoShawarmaBean");
            System.out.println(burrito.getType());
        } catch (Exception e) {
            System.out.println("Bean 'burritoShawarmaBean' не создан, так как шаурма ресторан закрыт.");
            // Обработка исключения
        }

        //Non Singleton
        var gyros1 = manager.getBean("gyros", Gyros.class);
        var gyros2 = manager.getBean("gyros", Gyros.class);

        gyros1.setName("first_gyros");
        gyros2.setName("second_gyros");

        if(!Objects.equals(gyros1.getName(), gyros2.getName())){
            System.out.println("Non Singleton");
        }else{
            System.out.println("Singleton");
        }
    }
}