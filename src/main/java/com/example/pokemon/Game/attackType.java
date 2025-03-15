package com.example.pokemon.Game;

import java.util.ArrayList;
import java.util.List;


public class attackType {
    private List<String> physicalAttacks;

    private List<String> statusAttacks;

    public attackType() {
        this.physicalAttacks = new ArrayList<>();

        this.statusAttacks = new ArrayList<>();
    }

    public void addPhysicalAttack(String attack) {
        physicalAttacks.add(attack);
    }



    public void addStatusAttack(String attack) {
        statusAttacks.add(attack);
    }

    public List<String> getPhysicalAttacks() {
        return physicalAttacks;
    }

    public List<String> getStatusAttacks() {
        return statusAttacks;
    }
}