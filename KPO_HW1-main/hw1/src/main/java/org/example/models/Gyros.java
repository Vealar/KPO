package org.example.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Gyros implements Shawarma {
    private String name = "gyros";
    private int tasty = 4;
    private String type = "Gyros";

    @Override
    public int getTasty() {
        return tasty;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
