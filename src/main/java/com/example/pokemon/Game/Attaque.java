package com.example.pokemon.Game;

import java.util.List;

public class Attaque {
    private String name;
    private int damage;
    private List<String> availableTo;

    public Attaque(String name, int damage, List<String> availableTo) {
        this.name = name;
        this.damage = damage;
        this.availableTo = availableTo;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public List<String> getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(List<String> availableTo) {
        this.availableTo = availableTo;
    }
}