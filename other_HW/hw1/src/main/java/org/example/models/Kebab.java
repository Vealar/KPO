package org.example.models;


public class Kebab implements Shawarma {
    private int tasty = 3;
    private String type = "Kebab";

    @Override
    public int getTasty() {
        return tasty;
    }

    @Override
    public String getType() {
        return type;
    }
}
