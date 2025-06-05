package org.example.models;

public class Burrito implements Shawarma{
    private int tasty = 5;
    private String type = "Burrito";
    @Override
    public int getTasty() {
        return tasty;
    }

    @Override
    public String getType() {
        return type;
    }
}
