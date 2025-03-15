package com.example.pokemon.Game;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharacterLoader  {
    public static List<Character> loadCharactersFromCSV(String filePath) {
        List<Character> characters = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                int id = Integer.parseInt(record[0]);
                String name = record[1];
                int hp = Integer.parseInt(record[2]);
                int attack = Integer.parseInt(record[3]);
                int defense = Integer.parseInt(record[4]);
                int speed = Integer.parseInt(record[5]);
                int specialAttack = Integer.parseInt(record[6]);
                int specialDefense = Integer.parseInt(record[7]);
                String charImg = record[8];
                ArrayList<String> type = new ArrayList<>();
                for (String t : record[9].split(";")) {
                    type.add(t);
                }

                Character character = new Character(id,name, hp, attack, defense, speed, specialAttack, specialDefense, charImg, type);
                characters.add(character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return characters;
    }
}





