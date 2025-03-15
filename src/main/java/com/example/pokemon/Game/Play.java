package com.example.pokemon.Game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Play extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
      /* Platform.runLater(() -> {
            String filePath = "src/main/resources/asset/Pokemon.csv";
            List<Character> characters = CharacterLoader.loadCharactersFromCSV(filePath);

            for (Character character : characters) {
                System.out.println("Name: " + character.getName());
                System.out.println("HP: " + character.getHP());
                System.out.println("Defense: " + character.getDefense());
                System.out.println("Speed: " + character.getSpeed());
                System.out.println("Special Attack: " + character.getSpecialAttack());
                System.out.println("Special Defense: " + character.getSpecialDefense());
                System.out.println("Image: " + character.getCharImg());
                System.out.println("Type: " + String.join(", ", character.getType()));
                System.out.println();
            }
        });*/

        ArrayList<String> type = new ArrayList<>();
        type.add("Fighting");
        type.add("Water");
        ArrayList<String> type2 = new ArrayList<>();
        type2.add("Dark");
        var p = new Character(1,"Squirtle", 1000,55, 65, 43, 50, 64, "file:/path/to/squirtle.png", type);
        var p2 = new Character(2,"Squirtle", 1000,60, 75, 45, 55, 64, "file:/path/to/squirtle.png", type2);
        System.out.println("dabord: " + p2.getHP());
        p.specialHit(p2);
        System.out.println("dabord: " + p2.getHP());

    }
}