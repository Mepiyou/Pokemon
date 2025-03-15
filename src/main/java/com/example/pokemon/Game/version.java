package com.example.pokemon.Game;

public class version {
}

/*  version 1 de team bulder
package com.example.pokemon.Game;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PokemonTeamBuilder extends Application {

    private ObservableList<Character> team = FXCollections.observableArrayList();
    private ListView<Character> teamListView = new ListView<>(team);
    private ComboBox<Character> pokemonComboBox = new ComboBox<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pokemon Team Builder");

        // Load Pokemon from CSV
        List<Character> allPokemon = CharacterLoader.loadCharactersFromCSV("src/main/resources/asset/Pokemon.csv");
        pokemonComboBox.setItems(FXCollections.observableArrayList(allPokemon));

        // Create the main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // Create the form to add a Pokemon
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        // ComboBox for selecting Pokemon
        Label selectLabel = new Label("Select Pokemon:");
        form.add(selectLabel, 0, 0);
        form.add(pokemonComboBox, 1, 0);

        // Add Pokemon button
        Button addButton = new Button("Add Pokemon");
        addButton.setOnAction(e -> {
            //Character selectedPokemon = pokemonComboBox.getSelectionModel().getSelectedItem();
            if (team.size() < 4) {
                Character selectedPokemon = pokemonComboBox.getSelectionModel().getSelectedItem();
                if (selectedPokemon != null) {
                    team.add(selectedPokemon);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Team Limit Reached");
                alert.setHeaderText(null);
                alert.setContentText("You can only have a maximum of 4 Pokemon in your team.");
                alert.showAndWait();
            }
        });

        // Remove Pokemon button
        Button removeButton = new Button("Remove Pokemon");
        removeButton.setOnAction(e -> {
            Character selected = teamListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                team.remove(selected);
            }
        });

        // Layout for buttons
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(addButton, removeButton);

        // Add components to the main layout
        mainLayout.getChildren().addAll(form, buttonLayout, new Label("Team:"), teamListView);

        // Set the scene and show the stage
        Scene scene = new Scene(mainLayout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

Utilise le code suivant pour tester la classe PokemonTeamBuilder.java,  le deuxiemme  est plus bien




 */


 /*vesrion 2 team builder
package com.example.pokemon.Game;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class PokemonTeamBuilder extends Application {

    private ObservableList<Character> team = FXCollections.observableArrayList();
    private ListView<Character> teamListView = new ListView<>(team);
    private TableView<Character> pokemonTableView = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pokemon Team Builder");

        // Load Pokemon from CSV
        List<Character> allPokemon = CharacterLoader.loadCharactersFromCSV("src/main/resources/asset/Pokemon.csv");
        ObservableList<Character> pokemonList = FXCollections.observableArrayList(allPokemon);
        pokemonTableView.setItems(pokemonList);

        // Create columns for the TableView
        TableColumn<Character, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Character, Number> hpColumn = new TableColumn<>("HP");
        hpColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getHP()));

        TableColumn<Character, Number> attackColumn = new TableColumn<>("Attack");
        attackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getAttackValue()));

        TableColumn<Character, Number> defenseColumn = new TableColumn<>("Defense");
        defenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getDefense()));

        TableColumn<Character, Number> speedColumn = new TableColumn<>("Speed");
        speedColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpeed()));

        TableColumn<Character, Number> specialAttackColumn = new TableColumn<>("Special Attack");
        specialAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialAttack()));

        TableColumn<Character, Number> specialDefenseColumn = new TableColumn<>("Special Defense");
        specialDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialDefense()));

        TableColumn<Character, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.join(", ", cellData.getValue().getType())));

        pokemonTableView.getColumns().addAll(nameColumn, hpColumn, attackColumn, defenseColumn, speedColumn, specialAttackColumn, specialDefenseColumn, typeColumn);

        // Add Pokemon button
        Button addButton = new Button("Add Pokemon");
        addButton.setOnAction(e -> {
            if (team.size() < 4) {
                Character selectedPokemon = pokemonTableView.getSelectionModel().getSelectedItem();
                if (selectedPokemon != null) {
                    team.add(selectedPokemon);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Team Limit Reached");
                alert.setHeaderText(null);
                alert.setContentText("You can only have a maximum of 4 Pokemon in your team.");
                alert.showAndWait();
            }
        });

        // Remove Pokemon button
        Button removeButton = new Button("Remove Pokemon");
        removeButton.setOnAction(e -> {
            Character selected = teamListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                team.remove(selected);
            }
        });

        // Layout for buttons
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(addButton, removeButton);

        // Create the main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(new Label("Available Pokemon:"), pokemonTableView, buttonLayout, new Label("Team:"), teamListView);

        // Set the scene and show the stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
} */


/*
version 3 team builder
package com.example.pokemon.Game;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class PokemonTeamBuilder extends Application {

    private ObservableList<Character> team = FXCollections.observableArrayList();
    private ListView<Character> teamListView = new ListView<>(team);
    private TableView<Character> pokemonTableView = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pokemon Team Builder");

        // Load Pokemon from CSV
        List<Character> allPokemon = CharacterLoader.loadCharactersFromCSV("src/main/resources/asset/Pokemon.csv");
        ObservableList<Character> pokemonList = FXCollections.observableArrayList(allPokemon);
        pokemonTableView.setItems(pokemonList);

        // Create columns for the TableView
        TableColumn<Character, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Character, Number> hpColumn = new TableColumn<>("HP");
        hpColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getHP()));

        TableColumn<Character, Number> attackColumn = new TableColumn<>("Attack");
        attackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getAttackValue()));

        TableColumn<Character, Number> defenseColumn = new TableColumn<>("Defense");
        defenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getDefense()));

        TableColumn<Character, Number> speedColumn = new TableColumn<>("Speed");
        speedColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpeed()));

        TableColumn<Character, Number> specialAttackColumn = new TableColumn<>("Special Attack");
        specialAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialAttack()));

        TableColumn<Character, Number> specialDefenseColumn = new TableColumn<>("Special Defense");
        specialDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialDefense()));

        TableColumn<Character, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.join(", ", cellData.getValue().getType())));

        TableColumn<Character, ImageView> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView(new Image(cellData.getValue().getCharImg()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new ReadOnlyObjectWrapper<>(imageView);
        });

        pokemonTableView.getColumns().addAll(nameColumn, hpColumn, attackColumn, defenseColumn, speedColumn, specialAttackColumn, specialDefenseColumn, typeColumn, imageColumn);

        // Add Pokemon button
        Button addButton = new Button("Add Pokemon");
        addButton.setOnAction(e -> {
            if (team.size() < 4) {
                Character selectedPokemon = pokemonTableView.getSelectionModel().getSelectedItem();
                if (selectedPokemon != null) {
                    team.add(selectedPokemon);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Team Limit Reached");
                alert.setHeaderText(null);
                alert.setContentText("You can only have a maximum of 4 Pokemon in your team.");
                alert.showAndWait();
            }
        });

        // Remove Pokemon button
        Button removeButton = new Button("Remove Pokemon");
        removeButton.setOnAction(e -> {
            Character selected = teamListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                team.remove(selected);
            }
        });

        // Layout for buttons
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(addButton, removeButton);

        // Create the main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(new Label("Available Pokemon:"), pokemonTableView, buttonLayout, new Label("Team:"), teamListView);

        // Set the scene and show the stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

*/




