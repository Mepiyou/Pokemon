package com.example.pokemon.Game;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class testeimg extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crée une barre de progression (ProgressBar)
        ArrayList<String> type2 = new ArrayList<>();
        type2.add("Dark");
        var pokemon = new Character(2,"Squirtle", 1000,60, 75, 45, 55, 64, "file:/path/to/squirtle.png", type2);

        VBox pokemonBox = new VBox(10);
        pokemonBox.getStyleClass().add("pokemon-box");
        pokemonBox.setPrefWidth(100);
        pokemonBox.setPrefHeight(120);
        pokemonBox.setAlignment(Pos.CENTER);

        ProgressBar hpProgressBar = new ProgressBar();
        hpProgressBar.getStyleClass().add("hp-progress-bar");
        hpProgressBar.setProgress(pokemon.getHP() / 1000.0);


        ImageView pokemonImageView = new ImageView(new Image(pokemon.getCharImg()));
        pokemonImageView.setFitHeight(50);
        pokemonImageView.setFitWidth(50);
        pokemonImageView.getStyleClass().add("pokemon-image");

        Label nameLabel = new Label(pokemon.getName());
        nameLabel.getStyleClass().add("pokemon-name-label");

        pokemonBox.getChildren().addAll(hpProgressBar, pokemonImageView, nameLabel);
        StackPane root = new StackPane();
        root.getChildren().addAll(hpProgressBar, pokemonImageView, nameLabel);

        // Crée la scène et l'associe à la fenêtre
        Scene scene = new Scene(root, 400, 200);







        // Configure la fenêtre
        primaryStage.setTitle("Barre de Vie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
