package com.example.pokemon.Game;

import java.util.HashMap;
import java.util.Map;

public class TypeEffectiveness {
    private static final Map<String, Map<String, Double>> effectivenessMap = new HashMap<>();

    static {
        // Initialize the effectiveness map with values for Normal, Fire, Water, Grass, Electric, Ice, Fighting, and Poison
        addEffectiveness("Normal", "Rock", 0.5);
        addEffectiveness("Normal", "Ghost", 0.0);
        addEffectiveness("Normal", "Steel", 0.5);

        addEffectiveness("Fire", "Fire", 0.5);
        addEffectiveness("Fire", "Water", 0.5);
        addEffectiveness("Fire", "Grass", 2.0);
        addEffectiveness("Fire", "Ice", 2.0);
        addEffectiveness("Fire", "Bug", 2.0);
        addEffectiveness("Fire", "Rock", 0.5);
        addEffectiveness("Fire", "Dragon", 0.5);
        addEffectiveness("Fire", "Steel", 2.0);

        addEffectiveness("Water", "Fire", 2.0);
        addEffectiveness("Water", "Water", 0.5);
        addEffectiveness("Water", "Grass", 0.5);
        addEffectiveness("Water", "Ground", 2.0);
        addEffectiveness("Water", "Rock", 2.0);
        addEffectiveness("Water", "Dragon", 0.5);

        addEffectiveness("Grass", "Fire", 0.5);
        addEffectiveness("Grass", "Water", 2.0);
        addEffectiveness("Grass", "Grass", 0.5);
        addEffectiveness("Grass", "Poison", 0.5);
        addEffectiveness("Grass", "Ground", 2.0);
        addEffectiveness("Grass", "Flying", 0.5);
        addEffectiveness("Grass", "Bug", 0.5);
        addEffectiveness("Grass", "Rock", 2.0);
        addEffectiveness("Grass", "Dragon", 0.5);
        addEffectiveness("Grass", "Steel", 0.5);

        addEffectiveness("Electric", "Water", 2.0);
        addEffectiveness("Electric", "Electric", 0.5);
        addEffectiveness("Electric", "Grass", 0.5);
        addEffectiveness("Electric", "Ground", 0.0);
        addEffectiveness("Electric", "Flying", 2.0);
        addEffectiveness("Electric", "Dragon", 0.5);

        addEffectiveness("Ice", "Fire", 0.5);
        addEffectiveness("Ice", "Water", 0.5);
        addEffectiveness("Ice", "Grass", 2.0);
        addEffectiveness("Ice", "Ice", 0.5);
        addEffectiveness("Ice", "Ground", 2.0);
        addEffectiveness("Ice", "Flying", 2.0);
        addEffectiveness("Ice", "Dragon", 2.0);
        addEffectiveness("Ice", "Steel", 0.5);

        addEffectiveness("Fighting", "Normal", 2.0);
        addEffectiveness("Fighting", "Ice", 2.0);
        addEffectiveness("Fighting", "Poison", 0.5);
        addEffectiveness("Fighting", "Flying", 0.5);
        addEffectiveness("Fighting", "Psychic", 0.5);
        addEffectiveness("Fighting", "Bug", 0.5);
        addEffectiveness("Fighting", "Rock", 2.0);
        addEffectiveness("Fighting", "Ghost", 0.0);
        addEffectiveness("Fighting", "Dark", 2.0);
        addEffectiveness("Fighting", "Steel", 2.0);
        addEffectiveness("Fighting", "Fairy", 0.5);

        addEffectiveness("Poison", "Grass", 2.0);
        addEffectiveness("Poison", "Poison", 0.5);
        addEffectiveness("Poison", "Ground", 0.5);
        addEffectiveness("Poison", "Rock", 0.5);
        addEffectiveness("Poison", "Ghost", 0.5);
        addEffectiveness("Poison", "Steel", 0.0);
        addEffectiveness("Poison", "Fairy", 2.0);
    }

    private static void addEffectiveness(String attackType, String defenseType, double multiplier) {
        effectivenessMap.computeIfAbsent(attackType, k -> new HashMap<>()).put(defenseType, multiplier);
    }

    public static double getEffectiveness(String attackType, String targetType) {
        return effectivenessMap.getOrDefault(attackType, new HashMap<>()).getOrDefault(targetType, 1.0);
    }
}