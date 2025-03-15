/*

package com.example.pokemon.Game;

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

public class CharacterModification {

    private final Stage primaryStage;
    private final Scene initialScene;
    private final ObservableList<Character> team;
    private final ObservableList<Character> computerTeam;

    public CharacterModification(Stage primaryStage, Scene initialScene, ObservableList<Character> team) {
        this.primaryStage = primaryStage;
        this.initialScene = initialScene;
        this.team = team;
        this.computerTeam = computerPokemon.buildMachineTeam();
    }

    public void buildNextScene() {
        VBox nextLayout = new VBox(10);
        nextLayout.setPadding(new Insets(10));

        TableView<Character> selectedPokemonTableView = new TableView<>(team);

        TableColumn<Character, String> selectedNameColumn = new TableColumn<>("Name");
        selectedNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Character, Number> selectedHpColumn = new TableColumn<>("HP");
        selectedHpColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getHP()));

        TableColumn<Character, Number> selectedAttackColumn = new TableColumn<>("Attack");
        selectedAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getAttackValue()));

        TableColumn<Character, Number> selectedDefenseColumn = new TableColumn<>("Defense");
        selectedDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getDefense()));

        TableColumn<Character, Number> selectedSpeedColumn = new TableColumn<>("Speed");
        selectedSpeedColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpeed()));

        TableColumn<Character, Number> selectedSpecialAttackColumn = new TableColumn<>("Special Attack");
        selectedSpecialAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialAttack()));

        TableColumn<Character, Number> selectedSpecialDefenseColumn = new TableColumn<>("Special Defense");
        selectedSpecialDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialDefense()));

        TableColumn<Character, String> selectedTypeColumn = new TableColumn<>("Type");
        selectedTypeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.join(", ", cellData.getValue().getType())));

        TableColumn<Character, ImageView> selectedImageColumn = new TableColumn<>("Image");
        selectedImageColumn.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView(new Image(cellData.getValue().getCharImg()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new ReadOnlyObjectWrapper<>(imageView);
        });

        selectedPokemonTableView.getColumns().addAll(selectedImageColumn, selectedNameColumn, selectedHpColumn, selectedAttackColumn, selectedDefenseColumn, selectedSpeedColumn, selectedSpecialAttackColumn, selectedSpecialDefenseColumn, selectedTypeColumn);

        nextLayout.getChildren().add(selectedPokemonTableView);

        // ListView for displaying available objects
        ListView<String> availableObjectsListView = new ListView<>();
        List<Objet> availableObjects = ObjetListLoader.loadObjectsFromCSV("src/main/resources/asset/Objects.csv");
        ObservableList<String> availableObjectNames = FXCollections.observableArrayList();
        for (Objet objet : availableObjects) {
            availableObjectNames.add(objet.getName() + " (" + objet.getDescription() + ")");
        }
        availableObjectsListView.setItems(availableObjectNames);

        // ListView for displaying available attacks
        ListView<String> availableAttacksListView = new ListView<>();
        List<Attaque> availableAttacks = AttaqueListLoader.loadAttacksFromCSV("src/main/resources/asset/AttaqueListe.csv");
        ObservableList<String> availableAttackNames = FXCollections.observableArrayList();
        for (Attaque attack : availableAttacks) {
            availableAttackNames.add(attack.getName() + " (Damage: " + attack.getDamage() + ")");
        }
        availableAttacksListView.setItems(availableAttackNames);

        // Layout for objects and attacks
        HBox objectsAndAttacksLayout = new HBox(10);
        objectsAndAttacksLayout.getChildren().addAll(availableObjectsListView, availableAttacksListView);

        nextLayout.getChildren().add(new Label("Available Objects and Attacks:"));
        nextLayout.getChildren().add(objectsAndAttacksLayout);

        // ListView for displaying selected object
        ListView<String> selectedObjectListView = new ListView<>();
        nextLayout.getChildren().add(new Label("Selected Object:"));
        nextLayout.getChildren().add(selectedObjectListView);

        // Update selectedObjectListView when a Pokémon is selected
        selectedPokemonTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedObjectListView.getItems().clear();
                Objet selectedObject = newSelection.getObjet();
                if (selectedObject != null) {
                    selectedObjectListView.getItems().add(selectedObject.getName() + " (" + selectedObject.getDescription() + ")");
                }
            }
        });

        // Button to add selected object to the Pokémon
        Button addObjectButton = new Button("Add Object");
        addObjectButton.setOnAction(e -> {
            String selectedObjectName = availableObjectsListView.getSelectionModel().getSelectedItem();
            Character selectedPokemon = selectedPokemonTableView.getSelectionModel().getSelectedItem();
            if (selectedObjectName != null && selectedPokemon != null) {
                for (Objet objet : availableObjects) {
                    if (selectedObjectName.startsWith(objet.getName())) {
                        selectedPokemon.setObjet(objet);
                        selectedObjectListView.getItems().clear();
                        selectedObjectListView.getItems().add(objet.getName() + " (" + objet.getDescription() + ")");
                        break;
                    }
                }
            } else {
                showAlert("No Pokémon or Object Selected", "Please select a Pokémon and an object to add.");
            }
        });

        nextLayout.getChildren().add(addObjectButton);

        // ListView for displaying selected attacks
        ListView<String> selectedAttacksListView = new ListView<>();
        nextLayout.getChildren().add(new Label("Selected Attacks:"));
        nextLayout.getChildren().add(selectedAttacksListView);

        // Update selectedAttacksListView when a Pokémon is selected
        selectedPokemonTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ObservableList<String> selectedAttackNames = FXCollections.observableArrayList();
                for (Attaque attack : newSelection.getAttackList()) {
                    selectedAttackNames.add(attack.getName() + " (Damage: " + attack.getDamage() + ")");
                }
                selectedAttacksListView.setItems(selectedAttackNames);
            }
        });

        // Button to add selected attack to the Pokémon
        Button addAttackButton = new Button("Add Attack");
        addAttackButton.setOnAction(event -> {
            Character selectedPokemon = selectedPokemonTableView.getSelectionModel().getSelectedItem();
            String selectedAttack = availableAttacksListView.getSelectionModel().getSelectedItem();
            if (selectedPokemon != null && selectedAttack != null) {
                if (selectedPokemon.getAttackList().size() < 4) {
                    for (Attaque attack : availableAttacks) {
                        if ((attack.getName() + " (Damage: " + attack.getDamage() + ")").equals(selectedAttack)) {
                            selectedPokemon.getAttackList().add(attack);
                            break;
                        }
                    }
                    ObservableList<String> selectedAttackNames = FXCollections.observableArrayList();
                    for (Attaque attack : selectedPokemon.getAttackList()) {
                        selectedAttackNames.add(attack.getName() + " (Damage: " + attack.getDamage() + ")");
                    }
                    selectedAttacksListView.setItems(selectedAttackNames);
                } else {
                    showAlert("Attack Limit Reached", "A Pokémon can only have a maximum of 4 attacks.");
                }
            } else {
                showAlert("No Pokémon or Attack selected", "Please select a Pokémon and an attack to add.");
            }
        });

        nextLayout.getChildren().add(addAttackButton);

        // Button to check if all Pokémon have four attacks and change to battle scene
        Button battleButton = new Button("Start Battle");
        battleButton.setOnAction(event -> {
            if (allPokemonHaveFourAttacks(team)) {
                BattleSceneBuilder battleSceneBuilder = new BattleSceneBuilder(primaryStage, initialScene, team, computerTeam);
                battleSceneBuilder.buildBattleScene();
            } else {
                showAlert("Incomplete Attacks", "All Pokémon must have 4 attacks to start the battle.");
            }
        });
        nextLayout.getChildren().add(battleButton);

        // Return button
        Button returnButton = new Button("Return");
        returnButton.setOnAction(event -> primaryStage.setScene(initialScene));
        nextLayout.getChildren().add(returnButton);

        Scene nextScene = new Scene(nextLayout, 800, 600);
        primaryStage.setScene(nextScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean allPokemonHaveFourAttacks(List<Character> team) {
        for (Character pokemon : team) {
            if (pokemon.getAttackList().size() != 4) {
                return false;
            }
        }
        return true;
    }
}
*/
/*

package com.example.pokemon.Game;

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
import java.util.stream.Collectors;

public class CharacterModification {

    private final Stage primaryStage;
    private final Scene initialScene;
    private final ObservableList<Character> team;
    private final List<Attaque> allAttacks;

    public CharacterModification(Stage primaryStage, Scene initialScene, ObservableList<Character> team) {
        this.primaryStage = primaryStage;
        this.initialScene = initialScene;
        this.team = team;
        this.allAttacks = AttaqueListLoader.loadAttacksFromCSV("src/main/resources/asset/AttaqueListe.csv");
    }

    public void buildNextScene() {
        VBox nextLayout = new VBox(10);
        nextLayout.setPadding(new Insets(10));

        TableView<Character> selectedPokemonTableView = new TableView<>(team);

        TableColumn<Character, String> selectedNameColumn = new TableColumn<>("Name");
        selectedNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Character, Number> selectedHpColumn = new TableColumn<>("HP");
        selectedHpColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getHP()));

        TableColumn<Character, Number> selectedAttackColumn = new TableColumn<>("Attack");
        selectedAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getAttackValue()));

        TableColumn<Character, Number> selectedDefenseColumn = new TableColumn<>("Defense");
        selectedDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getDefense()));

        TableColumn<Character, Number> selectedSpeedColumn = new TableColumn<>("Speed");
        selectedSpeedColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpeed()));

        TableColumn<Character, Number> selectedSpecialAttackColumn = new TableColumn<>("Special Attack");
        selectedSpecialAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialAttack()));

        TableColumn<Character, Number> selectedSpecialDefenseColumn = new TableColumn<>("Special Defense");
        selectedSpecialDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialDefense()));

        TableColumn<Character, String> selectedTypeColumn = new TableColumn<>("Type");
        selectedTypeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.join(", ", cellData.getValue().getType())));

        TableColumn<Character, ImageView> selectedImageColumn = new TableColumn<>("Image");
        selectedImageColumn.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView(new Image(cellData.getValue().getCharImg()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new ReadOnlyObjectWrapper<>(imageView);
        });

        selectedPokemonTableView.getColumns().addAll(selectedNameColumn, selectedHpColumn, selectedAttackColumn, selectedDefenseColumn, selectedSpeedColumn, selectedSpecialAttackColumn, selectedSpecialDefenseColumn, selectedTypeColumn, selectedImageColumn);

        ListView<String> availableAttacksListView = new ListView<>();
        ObservableList<String> availableAttackNames = FXCollections.observableArrayList();
        availableAttacksListView.setItems(availableAttackNames);

        ListView<String> selectedAttacksListView = new ListView<>();
        ObservableList<String> selectedAttackNames = FXCollections.observableArrayList();
        selectedAttacksListView.setItems(selectedAttackNames);







        selectedPokemonTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<String> filteredAttacks = allAttacks.stream()
                        .filter(attack -> attack.getAvailableTo().contains(newSelection.getName()))
                        .map(Attaque::getName)
                        .collect(Collectors.toList());
                availableAttackNames.setAll(filteredAttacks);

                selectedAttackNames.setAll(newSelection.getAttackList().stream()
                        .map(Attaque::getName)
                        .collect(Collectors.toList()));
            }
        });

        Button addAttackButton = new Button("Add Attack");
        addAttackButton.setOnAction(event -> {
            String selectedAttack = availableAttacksListView.getSelectionModel().getSelectedItem();
            Character selectedPokemon = selectedPokemonTableView.getSelectionModel().getSelectedItem();
            if (selectedAttack != null && selectedPokemon != null) {
                selectedPokemon.addAttackToAttackList(selectedPokemon, new Attaque(selectedAttack, 0, List.of(selectedPokemon.getName()))); // Adjust parameters as needed
                selectedAttackNames.add(selectedAttack);
            }
        });








        nextLayout.getChildren().addAll(new Label("Selected Pokémon:"), selectedPokemonTableView, new Label("Available Attacks:"), availableAttacksListView, addAttackButton, new Label("Selected Attacks:"), selectedAttacksListView);

        Scene nextScene = new Scene(nextLayout, 800, 600);
        primaryStage.setScene(nextScene);
    }
}
*/




/*
package com.example.pokemon.Game;

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

public class CharacterModification {

    private final Stage primaryStage;
    private final Scene initialScene;
    private final ObservableList<Character> team;
    private final ObservableList<Character> computerTeam;

    public CharacterModification(Stage primaryStage, Scene initialScene, ObservableList<Character> team) {
        this.primaryStage = primaryStage;
        this.initialScene = initialScene;
        this.team = team;
        this.computerTeam = computerPokemon.buildMachineTeam();
    }

    public void buildNextScene() {
        VBox nextLayout = new VBox(10);
        nextLayout.setPadding(new Insets(10));

        TableView<Character> selectedPokemonTableView = new TableView<>(team);

        TableColumn<Character, String> selectedNameColumn = new TableColumn<>("Name");
        selectedNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Character, Number> selectedHpColumn = new TableColumn<>("HP");
        selectedHpColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getHP()));

        TableColumn<Character, Number> selectedAttackColumn = new TableColumn<>("Attack");
        selectedAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getAttackValue()));

        TableColumn<Character, Number> selectedDefenseColumn = new TableColumn<>("Defense");
        selectedDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getDefense()));

        TableColumn<Character, Number> selectedSpeedColumn = new TableColumn<>("Speed");
        selectedSpeedColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpeed()));

        TableColumn<Character, Number> selectedSpecialAttackColumn = new TableColumn<>("Special Attack");
        selectedSpecialAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialAttack()));

        TableColumn<Character, Number> selectedSpecialDefenseColumn = new TableColumn<>("Special Defense");
        selectedSpecialDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialDefense()));

        TableColumn<Character, String> selectedTypeColumn = new TableColumn<>("Type");
        selectedTypeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.join(", ", cellData.getValue().getType())));

        TableColumn<Character, ImageView> selectedImageColumn = new TableColumn<>("Image");
        selectedImageColumn.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView(new Image(cellData.getValue().getCharImg()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new ReadOnlyObjectWrapper<>(imageView);
        });

        selectedPokemonTableView.getColumns().addAll(selectedImageColumn, selectedNameColumn, selectedHpColumn, selectedAttackColumn, selectedDefenseColumn, selectedSpeedColumn, selectedSpecialAttackColumn, selectedSpecialDefenseColumn, selectedTypeColumn);

        nextLayout.getChildren().add(selectedPokemonTableView);

        // ListView for displaying available objects
        ListView<String> availableObjectsListView = new ListView<>();
        List<Objet> availableObjects = ObjetListLoader.loadObjectsFromCSV("src/main/resources/asset/Objects.csv");
        ObservableList<String> availableObjectNames = FXCollections.observableArrayList();
        for (Objet objet : availableObjects) {
            availableObjectNames.add(objet.getName() + " (" + objet.getDescription() + ")");
        }
        availableObjectsListView.setItems(availableObjectNames);

        // ListView for displaying available attacks
        ListView<String> availableAttacksListView = new ListView<>();
        List<Attaque> availableAttacks = AttaqueListLoader.loadAttacksFromCSV("src/main/resources/asset/AttaqueListe.csv");
        ObservableList<String> availableAttackNames = FXCollections.observableArrayList();
        for (Attaque attack : availableAttacks) {
            availableAttackNames.add(attack.getName() + " (Damage: " + attack.getDamage() + ")");
        }
        availableAttacksListView.setItems(availableAttackNames);

        // Layout for objects and attacks
        HBox objectsAndAttacksLayout = new HBox(10);
        objectsAndAttacksLayout.getChildren().addAll(availableObjectsListView, availableAttacksListView);

        nextLayout.getChildren().add(new Label("Available Objects and Attacks:"));
        nextLayout.getChildren().add(objectsAndAttacksLayout);

        // ListView for displaying selected object
        ListView<String> selectedObjectListView = new ListView<>();
        nextLayout.getChildren().add(new Label("Selected Object:"));
        nextLayout.getChildren().add(selectedObjectListView);

        // Update selectedObjectListView when a Pokémon is selected
        selectedPokemonTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedObjectListView.getItems().clear();
                Objet selectedObject = newSelection.getObjet();
                if (selectedObject != null) {
                    selectedObjectListView.getItems().add(selectedObject.getName() + " (" + selectedObject.getDescription() + ")");
                }
            }
        });

        // Button to add selected object to the Pokémon
        Button addObjectButton = new Button("Add Object");
        addObjectButton.setOnAction(e -> {
            String selectedObjectName = availableObjectsListView.getSelectionModel().getSelectedItem();
            Character selectedPokemon = selectedPokemonTableView.getSelectionModel().getSelectedItem();
            if (selectedObjectName != null && selectedPokemon != null) {
                for (Objet objet : availableObjects) {
                    if (selectedObjectName.startsWith(objet.getName())) {
                        selectedPokemon.setObjet(objet);
                        selectedObjectListView.getItems().clear();
                        selectedObjectListView.getItems().add(objet.getName() + " (" + objet.getDescription() + ")");
                        break;
                    }
                }
            } else {
                showAlert("No Pokémon or Object Selected", "Please select a Pokémon and an object to add.");
            }
        });

        nextLayout.getChildren().add(addObjectButton);

        // ListView for displaying selected attacks
        ListView<String> selectedAttacksListView = new ListView<>();
        nextLayout.getChildren().add(new Label("Selected Attacks:"));
        nextLayout.getChildren().add(selectedAttacksListView);

        // Update selectedAttacksListView when a Pokémon is selected
        selectedPokemonTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ObservableList<String> selectedAttackNames = FXCollections.observableArrayList();
                for (Attaque attack : newSelection.getAttackList()) {
                    selectedAttackNames.add(attack.getName() + " (Damage: " + attack.getDamage() + ")");
                }
                selectedAttacksListView.setItems(selectedAttackNames);
            }
        });

        // Button to add selected attack to the Pokémon
        Button addAttackButton = new Button("Add Attack");
        addAttackButton.setOnAction(event -> {
            Character selectedPokemon = selectedPokemonTableView.getSelectionModel().getSelectedItem();
            String selectedAttack = availableAttacksListView.getSelectionModel().getSelectedItem();
            if (selectedPokemon != null && selectedAttack != null) {
                if (selectedPokemon.getAttackList().size() < 4) {
                    for (Attaque attack : availableAttacks) {
                        if ((attack.getName() + " (Damage: " + attack.getDamage() + ")").equals(selectedAttack)) {
                            selectedPokemon.getAttackList().add(attack);
                            break;
                        }
                    }
                    ObservableList<String> selectedAttackNames = FXCollections.observableArrayList();
                    for (Attaque attack : selectedPokemon.getAttackList()) {
                        selectedAttackNames.add(attack.getName() + " (Damage: " + attack.getDamage() + ")");
                    }
                    selectedAttacksListView.setItems(selectedAttackNames);
                } else {
                    showAlert("Attack Limit Reached", "A Pokémon can only have a maximum of 4 attacks.");
                }
            } else {
                showAlert("No Pokémon or Attack selected", "Please select a Pokémon and an attack to add.");
            }
        });

        nextLayout.getChildren().add(addAttackButton);

        // Display computer team
        TableView<Character> computerPokemonTableView = new TableView<>(computerTeam);
        TableColumn<Character, String> computerNameColumn = new TableColumn<>("Name");
        computerNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

        TableColumn<Character, Number> computerHpColumn = new TableColumn<>("HP");
        computerHpColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getHP()));

        TableColumn<Character, Number> computerAttackColumn = new TableColumn<>("Attack");
        computerAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getAttackValue()));

        TableColumn<Character, Number> computerDefenseColumn = new TableColumn<>("Defense");
        computerDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getDefense()));

        TableColumn<Character, Number> computerSpeedColumn = new TableColumn<>("Speed");
        computerSpeedColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpeed()));

        TableColumn<Character, Number> computerSpecialAttackColumn = new TableColumn<>("Special Attack");
        computerSpecialAttackColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialAttack()));

        TableColumn<Character, Number> computerSpecialDefenseColumn = new TableColumn<>("Special Defense");
        computerSpecialDefenseColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getSpecialDefense()));

        TableColumn<Character, String> computerTypeColumn = new TableColumn<>("Type");
        computerTypeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.join(", ", cellData.getValue().getType())));

        TableColumn<Character, ImageView> computerImageColumn = new TableColumn<>("Image");
        computerImageColumn.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView(new Image(cellData.getValue().getCharImg()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            return new ReadOnlyObjectWrapper<>(imageView);
        });

        computerPokemonTableView.getColumns().addAll(computerImageColumn, computerNameColumn, computerHpColumn, computerAttackColumn, computerDefenseColumn, computerSpeedColumn, computerSpecialAttackColumn, computerSpecialDefenseColumn, computerTypeColumn);

        nextLayout.getChildren().add(new Label("Computer Team:"));
        nextLayout.getChildren().add(computerPokemonTableView);

        // Button to check if all Pokémon have four attacks and change to battle scene
        Button battleButton = new Button("Start Battle");
        battleButton.setOnAction(event -> {
            if (allPokemonHaveFourAttacks(team)) {
                BattleSceneBuilder battleSceneBuilder = new BattleSceneBuilder(primaryStage, initialScene, team, computerTeam);
                battleSceneBuilder.buildBattleScene();
            } else {
                showAlert("Incomplete Attacks", "All Pokémon must have 4 attacks to start the battle.");
            }
        });
        nextLayout.getChildren().add(battleButton);

        // Return button
        Button returnButton = new Button("Return");
        returnButton.setOnAction(event -> primaryStage.setScene(initialScene));
        nextLayout.getChildren().add(returnButton);

        Scene nextScene = new Scene(nextLayout, 800, 600);
        primaryStage.setScene(nextScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean allPokemonHaveFourAttacks(List<Character> team) {
        for (Character pokemon : team) {
            if (pokemon.getAttackList().size() != 4) {
                return false;
            }
        }
        return true;
    }
}*/


/*
package com.example.pokemon.Game;

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
import java.util.stream.Collectors;

public class CharacterModification {

    private final Stage primaryStage;
    private final Scene initialScene;
    private final ObservableList<Character> team;
    private final List<Attaque> allAttacks;
    private final List<Objet> allObjects;

    public CharacterModification(Stage primaryStage, Scene initialScene, ObservableList<Character> team) {
        this.primaryStage = primaryStage;
        this.initialScene = initialScene;
        this.team = team;
        this.allAttacks = AttaqueListLoader.loadAttacksFromCSV("src/main/resources/asset/AttaqueListe.csv");
        this.allObjects = ObjetListLoader.loadObjectsFromCSV("src/main/resources/asset/ObjetListe.csv");
    }

    public void buildNextScene() {
        VBox nextLayout = new VBox(10);
        nextLayout.setPadding(new Insets(10));

        HBox teamBox = createTeamBox(team);

        ListView<String> availableAttacksListView = new ListView<>();
        ObservableList<String> availableAttackNames = FXCollections.observableArrayList();
        availableAttacksListView.setItems(availableAttackNames);

        ListView<String> selectedAttacksListView = new ListView<>();
        ObservableList<String> selectedAttackNames = FXCollections.observableArrayList();
        selectedAttacksListView.setItems(selectedAttackNames);

        ListView<String> availableObjectsListView = new ListView<>();
        ObservableList<String> availableObjectNames = FXCollections.observableArrayList(allObjects.stream().map(Objet::getName).collect(Collectors.toList()));
        availableObjectsListView.setItems(availableObjectNames);

        ListView<String> selectedObjectsListView = new ListView<>();
        ObservableList<String> selectedObjectNames = FXCollections.observableArrayList();
        selectedObjectsListView.setItems(selectedObjectNames);

        Label availableAttacksLabel = new Label("Available Attacks:");
        Label selectedAttacksLabel = new Label("Selected Attacks:");
        Label selectedObjectsLabel = new Label("Selected Objects:");

        teamBox.getChildren().forEach(node -> {
            VBox pokemonBox = (VBox) node;
            pokemonBox.setOnMouseClicked(event -> {
                Character selectedPokemon = (Character) pokemonBox.getUserData();
                availableAttacksLabel.setText("Available Attacks for " + selectedPokemon.getName() + ":");
                selectedAttacksLabel.setText("Selected Attacks for " + selectedPokemon.getName() + ":");
                selectedObjectsLabel.setText("Selected Objects for " + selectedPokemon.getName() + ":");

                List<String> filteredAttacks = allAttacks.stream()
                        .filter(attack -> attack.getAvailableTo().contains(selectedPokemon.getName()))
                        .map(Attaque::getName)
                        .collect(Collectors.toList());
                availableAttackNames.setAll(filteredAttacks);

                selectedAttackNames.setAll(selectedPokemon.getAttackList().stream()
                        .map(Attaque::getName)
                        .collect(Collectors.toList()));
            });
        });

        Button addAttackButton = new Button("Add Attack");
        addAttackButton.setOnAction(event -> {
            String selectedAttack = availableAttacksListView.getSelectionModel().getSelectedItem();
            Character selectedPokemon = (Character) teamBox.getUserData();
            if (selectedAttack != null && selectedPokemon != null) {
                selectedPokemon.addAttackToAttackList(selectedPokemon, new Attaque(selectedAttack, 0, List.of(selectedPokemon.getName()))); // Adjust parameters as needed
                selectedAttackNames.add(selectedAttack);
            }
        });

        Button addObjectButton = new Button("Add Object");
        addObjectButton.setOnAction(event -> {
            String selectedObject = availableObjectsListView.getSelectionModel().getSelectedItem();
            if (selectedObject != null) {
                selectedObjectNames.add(selectedObject);
            }
        });

        nextLayout.getChildren().addAll(
                new Label("Selected Pokémon:"), teamBox,
                availableAttacksLabel, availableAttacksListView, addAttackButton,
                selectedAttacksLabel, selectedAttacksListView,
                selectedObjectsLabel, availableObjectsListView, addObjectButton,
                new Label("Selected Objects:"), selectedObjectsListView
        );

        Scene nextScene = new Scene(nextLayout, 800, 600);
        primaryStage.setScene(nextScene);
    }

    private HBox createTeamBox(ObservableList<Character> team) {
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));

        for (int i = 0; i < team.size(); i++) {
            Character pokemon = team.get(i);
            VBox infoBox = new VBox(5);
            infoBox.setPadding(new Insets(5));
            infoBox.getStyleClass().add("pokemon-box");
            infoBox.setUserData(pokemon);

            // Alternate background color between red and blue
            if (i % 2 == 0) {
                infoBox.setStyle("-fx-background-color: red;");
            } else {
                infoBox.setStyle("-fx-background-color: blue;");
            }

            Label nameLabel = new Label(pokemon.getName());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label typeLabel = new Label("Type: " + String.join(", ", pokemon.getType()));
            typeLabel.setStyle("-fx-font-size: 12px;");

            ImageView imageView = new ImageView(new Image(pokemon.getCharImg()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            infoBox.getChildren().addAll(imageView, nameLabel, typeLabel);
            hBox.getChildren().add(infoBox);
        }

        return hBox;

    }
}*/


package com.example.pokemon.Game;

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
import java.util.stream.Collectors;

public class CharacterModification {

    private final Stage primaryStage;
    private final Scene initialScene;
    private final ObservableList<Character> team;
    private final ObservableList<Character> computerTeam;
    private final List<Attaque> allAttacks;
    private final List<Objet> allObjects;
    private Character selectedPokemon;

    public CharacterModification(Stage primaryStage, Scene initialScene, ObservableList<Character> team) {
        this.primaryStage = primaryStage;
        this.initialScene = initialScene;
        this.team = team; this.computerTeam = computerPokemon.buildMachineTeam();

        this.allAttacks = AttaqueListLoader.loadAttacksFromCSV("src/main/resources/asset/AttaqueListe.csv");
        this.allObjects = ObjetListLoader.loadObjectsFromCSV("src/main/resources/asset/Objects.csv");
    }

    public void buildNextScene() {
        VBox nextLayout = new VBox(10);
        nextLayout.setPadding(new Insets(10));
        nextLayout.getStyleClass().add("root-background");

        HBox teamBox = createTeamBox(team);

        ListView<String> availableAttacksListView = new ListView<>();
        ObservableList<String> availableAttackNames = FXCollections.observableArrayList();
        availableAttacksListView.setItems(availableAttackNames);

        ListView<String> selectedAttacksListView = new ListView<>();
        ObservableList<String> selectedAttackNames = FXCollections.observableArrayList();
        selectedAttacksListView.setItems(selectedAttackNames);

        ListView<String> availableObjectsListView = new ListView<>();
        ObservableList<String> availableObjectNames = FXCollections.observableArrayList(allObjects.stream().map(Objet::getName).collect(Collectors.toList()));
        availableObjectsListView.setItems(availableObjectNames);

        ListView<String> selectedObjectsListView = new ListView<>();
        ObservableList<String> selectedObjectNames = FXCollections.observableArrayList();
        selectedObjectsListView.setItems(selectedObjectNames);

        Label availableAttacksLabel = new Label("Available Attacks:");
        Label selectedAttacksLabel = new Label("Selected Attacks:");
        Label availableObjectsLabel = new Label("Available Objects:");
        Label selectedObjectsLabel = new Label("Selected Objects:");
        availableObjectsLabel.getStyleClass().add("label-texte");
        selectedAttacksLabel.getStyleClass().add("label-texte");
        availableAttacksLabel.getStyleClass().add("label-texte");
        selectedObjectsLabel.getStyleClass().add("label-texte");

        teamBox.getChildren().forEach(node -> {
            VBox pokemonBox = (VBox) node;
            pokemonBox.setOnMouseClicked(event -> {
                selectedPokemon = (Character) pokemonBox.getUserData();
                availableAttacksLabel.setText("Available Attacks for " + selectedPokemon.getName() + ":");
                selectedAttacksLabel.setText("Selected Attacks for " + selectedPokemon.getName() + ":");
                selectedObjectsLabel.setText("Selected Object for " + selectedPokemon.getName() + ":");

                availableAttackNames.setAll(allAttacks.stream()
                        .filter(attack -> attack.getAvailableTo().contains(selectedPokemon.getName()))
                        .map(Attaque::getName)
                        .collect(Collectors.toList()));
                selectedAttackNames.setAll(selectedPokemon.getAttackList().stream()
                        .map(Attaque::getName)
                        .collect(Collectors.toList()));
                selectedObjectNames.setAll(selectedPokemon.getObjet() != null ? List.of(selectedPokemon.getObjet().getName()) : List.of());
            });
        });

        availableAttacksListView.setCellFactory(param -> new ListCell<>() {
            private final Button addButton = new Button("Add");
            private final HBox hBox = new HBox(10, new Label(), addButton);

            @Override
            protected void updateItem(String attack, boolean empty) {
                super.updateItem(attack, empty);
                if (empty || attack == null) {
                    setGraphic(null);
                } else {
                    Label label = (Label) hBox.getChildren().get(0);
                    label.setText(attack);
                    addButton.setOnAction(event -> {
                        if (selectedPokemon != null && selectedPokemon.getAttackList().size() < 4) {
                            Attaque attaque = allAttacks.stream()
                                    .filter(a -> a.getName().equals(attack))
                                    .findFirst()
                                    .orElse(null);
                            if (attaque != null) {
                                selectedPokemon.addAttackToAttackList(attaque);
                                selectedAttackNames.add(attaque.getName());
                            }
                        }
                    });
                    setGraphic(hBox);
                }
            }
        });

        selectedAttacksListView.setCellFactory(param -> new ListCell<>() {
            private final Button removeButton = new Button("Remove");
            private final HBox hBox = new HBox(10, new Label(), removeButton);

            @Override
            protected void updateItem(String attack, boolean empty) {
                super.updateItem(attack, empty);
                if (empty || attack == null) {
                    setGraphic(null);
                } else {
                    Label label = (Label) hBox.getChildren().get(0);
                    label.setText(attack);
                    removeButton.setOnAction(event -> {
                        if (selectedPokemon != null) {
                            Attaque attaque = selectedPokemon.getAttackList().stream()
                                    .filter(a -> a.getName().equals(attack))
                                    .findFirst()
                                    .orElse(null);
                            if (attaque != null) {
                                selectedPokemon.getAttackList().remove(attaque);
                                selectedAttackNames.remove(attaque.getName());
                            }
                        }
                    });
                    setGraphic(hBox);
                }
            }
        });

        availableObjectsListView.setCellFactory(param -> new ListCell<>() {
            private final Button addButton = new Button("Add");
            private final HBox hBox = new HBox(10, new Label(), addButton);

            @Override
            protected void updateItem(String object, boolean empty) {
                super.updateItem(object, empty);
                if (empty || object == null) {
                    setGraphic(null);
                } else {
                    Label label = (Label) hBox.getChildren().get(0);
                    label.setText(object);
                    addButton.setOnAction(event -> {
                        if (selectedPokemon != null) {
                            Objet objet = allObjects.stream().filter(o -> o.getName().equals(object)).findFirst().orElse(null);
                            if (objet != null) {
                                selectedPokemon.setObjet(objet);
                                selectedObjectNames.setAll(objet.getName());
                            }
                        }
                    });
                    setGraphic(hBox);
                }
            }
        });

        HBox box1 = new HBox(10, availableAttacksLabel, availableAttacksListView);
        VBox box2 = new VBox(10, selectedAttacksLabel, selectedAttacksListView);
        HBox attacksBox = new HBox(10, box1, box2);

        HBox box3 = new HBox(10, availableObjectsLabel, availableObjectsListView);
        VBox box4 = new VBox(10, selectedObjectsLabel, selectedObjectsListView);
        HBox objectsBox = new HBox(10, box3, box4);
        // Add the "Next" button
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            BattleSceneBuilder battleSceneBuilder = new BattleSceneBuilder(primaryStage,team, computerTeam);
            battleSceneBuilder.buildBattleScene();
        });

        nextLayout.getChildren().addAll(
                new Label("Selected Pokémon:"), teamBox,
                attacksBox,
                objectsBox,
                nextButton
        );

        Scene nextScene = new Scene(nextLayout, 800, 600);
        nextScene.getStylesheets().add(getClass().getResource("/modifcation.css").toExternalForm());
        primaryStage.setScene(nextScene);
    }

    private HBox createTeamBox(ObservableList<Character> team) {
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));

        for (int i = 0; i < team.size(); i++) {
            Character pokemon = team.get(i);
            VBox infoBox = new VBox(5);
            infoBox.setPadding(new Insets(5));
            infoBox.getStyleClass().add("pokemon-box");
            infoBox.setUserData(pokemon);


            Label nameLabel = new Label(pokemon.getName());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label typeLabel = new Label("Type: " + String.join(", ", pokemon.getType()));
            typeLabel.setStyle("-fx-font-size: 12px;");

            ImageView imageView = new ImageView(new Image(pokemon.getCharImg()));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            infoBox.getChildren().addAll(imageView, nameLabel, typeLabel);
            hBox.getChildren().add(infoBox);
        }

        return hBox;
    }
}