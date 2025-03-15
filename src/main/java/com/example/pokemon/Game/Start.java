package com.example.pokemon.Game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(20));

            Image logoImage = new Image(getClass().getResourceAsStream("/asset/pokemon-logo-home.png"));
            ImageView logoView = new ImageView(logoImage);
            logoView.setPreserveRatio(true);
            logoView.setFitWidth(300);

            Text title = new Text("Pokémon \nCombat Simulator");
            title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

            Button startHomeButton = new Button("New Game");
            startHomeButton.setOnAction(e -> {
                PokemonTeamBuilder teamBuilder = new PokemonTeamBuilder();
                try {
                    teamBuilder.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            startHomeButton.getStyleClass().add("start-button-home");

            Text credits = new Text("© Pokemon Yassine Evrad Not affiliated with Nintendo");
            credits.setFont(Font.font("Arial", 14));

            VBox centerBox = new VBox(20, logoView, title, startHomeButton);
            centerBox.setAlignment(Pos.CENTER);

            root.setCenter(centerBox);
            root.setBottom(credits);
            BorderPane.setAlignment(credits, Pos.CENTER);
            BorderPane.setMargin(credits, new Insets(0, 0, 20, 0));

            Scene scene = new Scene(root, 800, 700);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Pokémon Combat Simulator");
            primaryStage.show();

            title.getStyleClass().add("title-start");
            startHomeButton.getStyleClass().add("button-start");
            credits.getStyleClass().add("credits-start");
            // Main application class
            primaryStage.getScene().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}