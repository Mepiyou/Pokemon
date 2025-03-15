package com.example.pokemon.Game;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonTeamBuilder extends Application {

    private ObservableList<Character> team = FXCollections.observableArrayList();
    private ListView<Character> teamListView = new ListView<>(team);
    private Scene initialScene;
    private ObservableList<Character> pokemonList;
    private VBox detailBox = new VBox(10);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pokémon Team Builder");

        HBox mainLayout = new HBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getStyleClass().add("root-background");

        VBox leftLayout = new VBox(10);
        leftLayout.getChildren().add(detailBox);
        detailBox.getStyleClass().add("detail-box");

        VBox rightLayout = new VBox(10);

        TextField searchField = new TextField();
        searchField.setPromptText("Search Pokémon by name");

        ComboBox<String> typeFilter = new ComboBox<>();
        typeFilter.setPromptText("Filter by type");
        typeFilter.getItems().addAll("All", "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon", "Dark", "Fairy");
        typeFilter.setValue("All");

        List<Character> allPokemon = CharacterLoader.loadCharactersFromCSV("src/main/resources/asset/Pokemon.csv");
        pokemonList = FXCollections.observableArrayList(allPokemon);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterPokemonList(newValue, typeFilter.getValue()));
        typeFilter.valueProperty().addListener((observable, oldValue, newValue) -> filterPokemonList(searchField.getText(), newValue));

        GridPane pokemonGrid = createPokemonGrid(pokemonList);

        Label teamLabel = new Label("Your Team:");
        teamListView.setPrefHeight(800);
        teamListView.getStyleClass().add("team-list");

        teamListView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label nameLabel = new Label();
            private final Label typeLabel = new Label();
            private final Button removeButton = new Button("Remove");

            private final VBox vBox = new VBox(2, nameLabel, typeLabel);
            private final HBox hBox = new HBox(5, imageView, vBox, removeButton);

            @Override
            protected void updateItem(Character character, boolean empty) {
                super.updateItem(character, empty);
                if (empty || character == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(character.getCharImg()));
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    nameLabel.setText(character.getName());
                    typeLabel.setText(String.join(", ", character.getType()));
                    removeButton.setOnAction(event -> team.remove(character));
                    setGraphic(hBox);
                }
            }
        });

        ScrollPane teamScrollPane = new ScrollPane(teamListView);
        teamScrollPane.setFitToWidth(true);
        teamScrollPane.setPrefHeight(400);
        // Next button
        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            CharacterModification nextSceneBuilder = new CharacterModification(primaryStage, initialScene, team);
            nextSceneBuilder.buildNextScene();
        });


        Button saveButton = new Button("Save Team");
        saveButton.setOnAction(e -> saveTeam());

        Button loadButton = new Button("Load Team");
        loadButton.setOnAction(e -> loadTeam());

        HBox buttonLayout = new HBox(10,  nextButton);

        rightLayout.getChildren().addAll(searchField, typeFilter, teamLabel, teamScrollPane, pokemonGrid, buttonLayout);

        mainLayout.getChildren().addAll(leftLayout, rightLayout);

        initialScene = new Scene(mainLayout, 800, 600);
        initialScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Load and play the audio clip
        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/Littleroot-Town.mp3").toExternalForm());
        audioClip.play();

        primaryStage.setScene(initialScene);
        primaryStage.show();
    }

    private void filterPokemonList(String name, String type) {
        ObservableList<Character> filteredList = FXCollections.observableArrayList();
        for (Character pokemon : pokemonList) {
            boolean matchesName = pokemon.getName().toLowerCase().contains(name.toLowerCase());
            boolean matchesType = type.equals("All") || pokemon.getType().contains(type);
            if (matchesName && matchesType) {
                filteredList.add(pokemon);
            }
        }
        GridPane pokemonGrid = createPokemonGrid(filteredList);
        ((VBox) ((HBox) initialScene.getRoot()).getChildren().get(1)).getChildren().set(4, pokemonGrid);
    }

    private GridPane createPokemonGrid(ObservableList<Character> pokemonList) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        int column = 0;
        int row = 0;

        for (Character pokemon : pokemonList) {
            VBox infoBox = new VBox(5);
            infoBox.setPadding(new Insets(5));
            infoBox.getStyleClass().add("pokemon-box");

            Label nameLabel = new Label(pokemon.getName());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label typeLabel = new Label("Type: " + String.join(", ", pokemon.getType()));
            typeLabel.setStyle("-fx-font-size: 12px;");

            ImageView imageView = new ImageView(new Image(pokemon.getCharImg()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            Button addButton = new Button("Add to Team");
            addButton.setOnAction(event -> {
                if (team.size() < 4) {
                    team.add(pokemon);
                } else {
                    showAlert("Team Full", "You can only have 4 Pokémon in your team.");
                }
            });

            Button detailsButton = new Button("Details");
            detailsButton.setOnAction(event -> showPokemonDetails(pokemon));

            infoBox.getChildren().addAll(imageView, nameLabel, typeLabel, addButton, detailsButton);

            gridPane.add(infoBox, column, row);

            column++;
            if (column == 5) {
                column = 0;
                row++;
            }
        }

        return gridPane;
    }

    private void showPokemonDetails(Character pokemon) {
        detailBox.getChildren().clear();
        detailBox.setPadding(new Insets(10));
        detailBox.getStyleClass().add("detail-box");

        Label nameLabel = new Label("Name: " + pokemon.getName());
        Label typeLabel = new Label("Type: " + String.join(", ", pokemon.getType()));

        Label hpLabel = new Label("HP:");
        ProgressBar hpBar = new ProgressBar(pokemon.getHP() / 100.0);
        hpBar.setStyle("-fx-accent: green;");

        Label attackLabel = new Label("Attack:");
        ProgressBar attackBar = new ProgressBar(pokemon.getAttackValue() / 100.0);

        Label defenseLabel = new Label("Defense:");
        ProgressBar defenseBar = new ProgressBar(pokemon.getDefense() / 100.0);

        Label speedLabel = new Label("Speed:");
        ProgressBar speedBar = new ProgressBar(pokemon.getSpeed() / 100.0);

        Label specialAttackLabel = new Label("Special Attack:");
        ProgressBar specialAttackBar = new ProgressBar(pokemon.getSpecialAttack() / 100.0);

        Label specialDefenseLabel = new Label("Special Defense:");
        ProgressBar specialDefenseBar = new ProgressBar(pokemon.getSpecialDefense() / 100.0);

        ImageView imageView = new ImageView(new Image(pokemon.getCharImg()));
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        detailBox.getChildren().addAll(
                imageView,
                nameLabel,
                typeLabel,
                new HBox(5, hpLabel, hpBar),
                new HBox(5, attackLabel, attackBar),
                new HBox(5, defenseLabel, defenseBar),
                new HBox(5, speedLabel, speedBar),
                new HBox(5, specialAttackLabel, specialAttackBar),
                new HBox(5, specialDefenseLabel, specialDefenseBar)
        );
    }

    private void saveTeam() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("team.dat"))) {
            oos.writeObject(new ArrayList<>(team));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTeam() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("team.dat"))) {
            List<Character> loadedTeam = (List<Character>) ois.readObject();
            team.setAll(loadedTeam);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}