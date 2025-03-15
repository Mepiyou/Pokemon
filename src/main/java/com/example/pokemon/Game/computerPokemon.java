/*
package com.example.pokemon.Game;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class computerPokemon {

    public static ObservableList<Character> buildMachineTeam() {
        List<Character> allPokemon = CharacterLoader.loadCharactersFromCSV("src/main/resources/asset/Pokemon.csv");
        List<Attaque> allAttacks = AttaqueListLoader.loadAttacksFromCSV("src/main/resources/asset/AttaqueListe.csv");

        // Shuffle the list of Pokémon and select the first 4
        Collections.shuffle(allPokemon);
        List<Character> selectedPokemon = allPokemon.subList(0, 4);

        // Shuffle the list of attacks
        Collections.shuffle(allAttacks);

        // Assign 4 different attacks to each selected Pokémon
        for (Character pokemon : selectedPokemon) {
            List<Attaque> assignedAttacks = new ArrayList<>(allAttacks.subList(0, 4));
            pokemon.getAttackList().addAll(assignedAttacks);
            Collections.shuffle(allAttacks); // Shuffle again to ensure different attacks for each Pokémon
        }

        return FXCollections.observableArrayList(selectedPokemon);
    }
}*/

package com.example.pokemon.Game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class computerPokemon {

    public static ObservableList<Character> buildMachineTeam() {
        List<Character> allPokemon = CharacterLoader.loadCharactersFromCSV("src/main/resources/asset/Pokemon.csv");
        List<Attaque> allAttacks = AttaqueListLoader.loadAttacksFromCSV("src/main/resources/asset/AttaqueListe.csv");

        // Shuffle the list of Pokémon and select the first 4
        Collections.shuffle(allPokemon);
        List<Character> selectedPokemon = allPokemon.subList(0, 4);

        // Assign 4 different valid attacks to each selected Pokémon
        for (Character pokemon : selectedPokemon) {
            List<Attaque> validAttacks = new ArrayList<>();
            for (Attaque attack : allAttacks) {
                if (attack.getAvailableTo().contains(pokemon.getName())) {
                    validAttacks.add(attack);
                }
            }
            Collections.shuffle(validAttacks);
            List<Attaque> assignedAttacks = validAttacks.subList(0, Math.min(4, validAttacks.size()));
            pokemon.getAttackList().addAll(assignedAttacks);

            // Debug print statements
            System.out.println("Pokémon: " + pokemon.getName());
            System.out.println("Assigned Attacks: ");
            for (Attaque attaque : assignedAttacks) {
                System.out.println(" - " + attaque.getName());
            }
        }

        return FXCollections.observableArrayList(selectedPokemon);
    }
}