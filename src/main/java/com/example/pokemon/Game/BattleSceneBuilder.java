/*
package com.example.pokemon.Game;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleSceneBuilder {

    private final Stage primaryStage;
    private final ObservableList<Character> playerTeam;
    private final ObservableList<Character> computerTeam;
    private HBox playerPokemonBoxes;
    private HBox computerPokemonBoxes;
    private BattleBox battleBox;
    private TreeView<String> combatLog;
    private TreeItem<String> rootItem;

    public BattleSceneBuilder(Stage primaryStage, ObservableList<Character> playerTeam, ObservableList<Character> computerTeam) {
        this.primaryStage = primaryStage;
        this.playerTeam = playerTeam;
        this.computerTeam = computerTeam;
    }

    public void buildBattleScene() {
        HBox mainLayout = new HBox(5);
        mainLayout.setPadding(new Insets(15));
        mainLayout.getStyleClass().add("battle-layout");

        VBox battleLayout = new VBox(20);
        battleLayout.setAlignment(Pos.CENTER);

        Label playerTurnLabel = new Label("Click on a Pokémon of your team to start the battle");
        playerTurnLabel.getStyleClass().add("player-turn-label");

        VBox battleProgressBox = new VBox(10);
        battleProgressBox.getStyleClass().add("battle-progress-box");
        battleProgressBox.setPrefWidth(800);
        battleProgressBox.setPrefHeight(150);
        battleProgressBox.setAlignment(Pos.TOP_LEFT);
        Label battleProgressLabel = new Label("Battle Progress:\n\n...");
        battleProgressLabel.getStyleClass().add("battle-progress-label");
        battleProgressBox.getChildren().add(battleProgressLabel);

        VBox computerTeamBox = new VBox(10);
        computerTeamBox.getStyleClass().add("team-section");
        Label computerTeamLabel = new Label("Computer's Pokémon");
        computerTeamLabel.getStyleClass().add("team-label");
        computerTeamBox.getChildren().add(computerTeamLabel);

        computerPokemonBoxes = new HBox(20);
        computerPokemonBoxes.setAlignment(Pos.CENTER);
        for (Character pokemon : computerTeam) {
            VBox pokemonBox = createPokemonBox(pokemon);
            computerPokemonBoxes.getChildren().add(pokemonBox);
        }
        computerTeamBox.getChildren().add(computerPokemonBoxes);

        battleBox = new BattleBox();
        battleBox.setPrefHeight(300);

        VBox playerTeamBox = new VBox(10);
        playerTeamBox.getStyleClass().add("team-section");
        Label playerTeamLabel = new Label("Your Team");
        playerTeamLabel.getStyleClass().add("team-label");
        playerTeamBox.getChildren().add(playerTeamLabel);

        HBox attackButtonsView = new HBox(20);
        attackButtonsView.setAlignment(Pos.CENTER);
        attackButtonsView.setPrefHeight(30);

        Button[] attackButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            attackButtons[i] = new Button("Attack " + (i + 1));
            attackButtons[i].getStyleClass().add("attack-button");
            attackButtonsView.getChildren().add(attackButtons[i]);
        }

        Button specialAttackButton = new Button("Special Attack");
        specialAttackButton.getStyleClass().add("special-attack-button");
        attackButtonsView.getChildren().add(specialAttackButton);

        Button applyObjectButton = new Button("Use Object");
        applyObjectButton.getStyleClass().add("object-button");
        attackButtonsView.getChildren().add(applyObjectButton);

        Button computerAttackButton = new Button("Computer Attack");
        attackButtonsView.getChildren().add(computerAttackButton);

        computerAttackButton.setOnAction(e -> {
            battleBox.computerAttack();
            checkWinner(playerTeam, computerTeam);
        });

        playerPokemonBoxes = new HBox(20);
        playerPokemonBoxes.setAlignment(Pos.CENTER);
        for (Character pokemon : playerTeam) {
            VBox pokemonBox = createPokemonBox(pokemon);
            pokemonBox.setOnMouseClicked(event -> {
                battleBox.addPokemon(pokemon, true, computerTeam);
                battleBox.setAttackButtons(attackButtons, specialAttackButton, applyObjectButton);
                updateHPDisplay();
                checkAndRemoveFaintedPokemon();
                checkWinner(playerTeam, computerTeam);
            });
            playerPokemonBoxes.getChildren().add(pokemonBox);
        }

        playerTeamBox.getChildren().addAll(playerPokemonBoxes, attackButtonsView);

        battleLayout.getChildren().addAll(playerTurnLabel, battleProgressBox, computerTeamBox, battleBox, playerTeamBox);

        rootItem = new TreeItem<>("Combat Log");
        combatLog = new TreeView<>(rootItem);
        combatLog.setShowRoot(false);
        combatLog.setPrefWidth(350);
       combatLog.setPrefHeight(900);
        combatLog.getStyleClass().add("combat-log");

        VBox sideBox = new VBox(10);
        sideBox.setPadding(new Insets(10));
        //sideBox.getStyleClass().add("sidebox");
        sideBox.setPrefWidth(350);

        Label sideBoxLabel = new Label("Battle History");
        sideBoxLabel.getStyleClass().add("sidebox-label");
        sideBox.getChildren().addAll(sideBoxLabel, combatLog);

        mainLayout.getChildren().add(sideBox);
        mainLayout.getChildren().addAll(battleLayout);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene battleScene = new Scene(scrollPane, 700, 700);
        battleScene.getStylesheets().add(getClass().getResource("/combat.css").toExternalForm());
        battleScene.setUserData(this);
        primaryStage.setScene(battleScene);
    }

    private VBox createPokemonBox(Character pokemon) {
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

        Label hpLabel = new Label("HP: " + pokemon.getHP());
        hpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hpLabel.setTextFill(Color.RED);

        pokemonBox.getChildren().addAll(hpProgressBar, hpLabel, pokemonImageView, nameLabel);
        return pokemonBox;
    }

    public void updateHPDisplay() {
        updateTeamHPDisplay(playerTeam, playerPokemonBoxes);
        updateTeamHPDisplay(computerTeam, computerPokemonBoxes);
    }

    private void updateTeamHPDisplay(ObservableList<Character> team, HBox pokemonBoxes) {
        for (int i = 0; i < team.size(); i++) {
            Character pokemon = team.get(i);
            VBox pokemonBox = (VBox) pokemonBoxes.getChildren().get(i);

            ProgressBar hpProgressBar = (ProgressBar) pokemonBox.getChildren().get(0);
            Label hpLabel = (Label) pokemonBox.getChildren().get(1);

            hpProgressBar.setProgress(pokemon.getHP() / 1000.0);
            hpLabel.setText("HP: " + pokemon.getHP());
        }
    }

    public void checkAndRemoveFaintedPokemon() {
        checkTeamForFaintedPokemon(playerTeam, playerPokemonBoxes);
        checkTeamForFaintedPokemon(computerTeam, computerPokemonBoxes);
    }

    private void checkTeamForFaintedPokemon(ObservableList<Character> team, HBox pokemonBoxes) {
        List<Integer> faintedIndices = new ArrayList<>();

        for (int i = 0; i < team.size(); i++) {
            Character pokemon = team.get(i);
            if (pokemon.getHP() <= 0) {
                VBox pokemonBox = (VBox) pokemonBoxes.getChildren().get(i);
                pokemonBox.setStyle("-fx-background-color: red;");
                pokemonBox.setDisable(true);
                faintedIndices.add(i);
            }
        }

        for (int i = faintedIndices.size() - 1; i >= 0; i--) {
            int index = faintedIndices.get(i);
            team.remove(index);
            pokemonBoxes.getChildren().remove(index);

            BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) primaryStage.getScene().getUserData();
            if (team == playerTeam) {
                battleSceneBuilder.getBattleBox().addPokemon(battleSceneBuilder.getRandomPokemonFromTeam(playerTeam), true, computerTeam);
            } else if (team == computerTeam) {
                battleSceneBuilder.getBattleBox().addPokemon(battleSceneBuilder.getRandomPokemonFromTeam(computerTeam), false, computerTeam);
            }
        }

        updateHPDisplay();
    }

    public BattleBox getBattleBox() {
        return battleBox;
    }

    private Character getRandomPokemonFromTeam(ObservableList<Character> team) {
        if (team.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return team.get(random.nextInt(team.size()));
    }

    public void checkWinner(ObservableList<Character> playerTeam, ObservableList<Character> computerTeam) {
        if (playerTeam.isEmpty()) {
            appendToLog(rootItem, "Player has no more Pokémon. Computer wins!");
        } else if (computerTeam.isEmpty()) {
            appendToLog(rootItem, "Computer has no more Pokémon. Player wins!");
        }
    }

    public void logAndDisplayAttack(String attackingTeam, String attackingPokemon, String attackUsed, String targetPokemon, int damageCaused) {
        battleBox.logAttack(attackingTeam, attackingPokemon, attackUsed, targetPokemon, damageCaused);
        updateBattleHistory(attackingTeam, attackingPokemon, attackUsed, targetPokemon, damageCaused);
    }

    private void updateBattleHistory(String attackingTeam, String attackingPokemon, String attackUsed, String targetPokemon, int damageCaused) {
        String logMessage = attackingTeam + " team: " + attackingPokemon + " used " + attackUsed + " on " + targetPokemon + " causing " + damageCaused + " damage.";
        TreeItem<String> attackItem = new TreeItem<>(logMessage);
        rootItem.getChildren().add(attackItem);
        combatLog.refresh(); // Ensure the TreeView updates
    }

    public void appendToLog(TreeItem<String> parent, String message) {
        TreeItem<String> item = new TreeItem<>(message);
        parent.getChildren().add(item);
    }

    public TreeItem<String> getRootItem() {
        return rootItem;
    }
}*/


package com.example.pokemon.Game;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleSceneBuilder {

    private final Stage primaryStage;
    private final ObservableList<Character> playerTeam;
    private final ObservableList<Character> computerTeam;
    private HBox playerPokemonBoxes;
    private HBox computerPokemonBoxes;
    private BattleBox battleBox;
    private TreeView<String> combatLog;
    private TreeItem<String> rootItem;
    private int turnNumber = 1;


    public BattleSceneBuilder(Stage primaryStage, ObservableList<Character> playerTeam, ObservableList<Character> computerTeam) {
        this.primaryStage = primaryStage;
        this.playerTeam = playerTeam;
        this.computerTeam = computerTeam;
    }

    public void buildBattleScene() {
        HBox mainLayout = new HBox(5);
        mainLayout.setPadding(new Insets(15));
        mainLayout.getStyleClass().add("battle-layout");


        // Load the background image
        Image backgroundImage = new Image(getClass().getResource("/asset/gif/modif.gif").toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
               BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
               BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        mainLayout.setBackground(new Background(bgImage));

        VBox battleLayout = new VBox(20);
        battleLayout.setAlignment(Pos.CENTER);

        Label playerTurnLabel = new Label("Click on a Pokémon of your team to start the battle");
        playerTurnLabel.getStyleClass().add("player-turn-label");

        VBox battleProgressBox = new VBox(10);
        battleProgressBox.getStyleClass().add("battle-progress-box");
        battleProgressBox.setPrefWidth(800);
        battleProgressBox.setPrefHeight(150);
        battleProgressBox.setAlignment(Pos.TOP_LEFT);
        Label battleProgressLabel = new Label("Battle Progress:\n\n...");
        battleProgressLabel.getStyleClass().add("battle-progress-label");
        battleProgressBox.getChildren().add(battleProgressLabel);

        VBox computerTeamBox = new VBox(10);
        computerTeamBox.getStyleClass().add("team-section");
        Label computerTeamLabel = new Label("Computer's Pokémon");
        computerTeamLabel.getStyleClass().add("team-label");
        computerTeamBox.getChildren().add(computerTeamLabel);

        computerPokemonBoxes = new HBox(20);
        computerPokemonBoxes.setAlignment(Pos.CENTER);
        for (Character pokemon : computerTeam) {
            VBox pokemonBox = createPokemonBox(pokemon);
            computerPokemonBoxes.getChildren().add(pokemonBox);
        }
        computerTeamBox.getChildren().add(computerPokemonBoxes);

        battleBox = new BattleBox();
        battleBox.setPrefHeight(300);

        VBox playerTeamBox = new VBox(10);
        playerTeamBox.getStyleClass().add("team-section");
        Label playerTeamLabel = new Label("Your Team");
        playerTeamLabel.getStyleClass().add("team-label");
        playerTeamBox.getChildren().add(playerTeamLabel);

        HBox attackButtonsView = new HBox(20);
        attackButtonsView.setAlignment(Pos.CENTER);
        attackButtonsView.setPrefHeight(30);

        Button[] attackButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            attackButtons[i] = new Button("Attack " + (i + 1));
            attackButtons[i].getStyleClass().add("attack-button");
            attackButtonsView.getChildren().add(attackButtons[i]);
        }

        Button specialAttackButton = new Button("Special Attack");
        specialAttackButton.getStyleClass().add("special-attack-button");
        attackButtonsView.getChildren().add(specialAttackButton);

        Button applyObjectButton = new Button("Use Object");
        applyObjectButton.getStyleClass().add("object-button");
        attackButtonsView.getChildren().add(applyObjectButton);

        Button computerAttackButton = new Button("Computer Attack");
        attackButtonsView.getChildren().add(computerAttackButton);

        computerAttackButton.setOnAction(e -> {
            battleBox.computerAttack();
            checkWinner(playerTeam, computerTeam);
        });

        playerPokemonBoxes = new HBox(20);
        playerPokemonBoxes.setAlignment(Pos.CENTER);
        for (Character pokemon : playerTeam) {
            VBox pokemonBox = createPokemonBox(pokemon);
            pokemonBox.setOnMouseClicked(event -> {
                battleBox.addPokemon(pokemon, true, computerTeam);
                battleBox.setAttackButtons(attackButtons, specialAttackButton, applyObjectButton);
                updateHPDisplay();
                checkAndRemoveFaintedPokemon();
                checkWinner(playerTeam, computerTeam);
            });
            playerPokemonBoxes.getChildren().add(pokemonBox);
        }

        playerTeamBox.getChildren().addAll( attackButtonsView ,playerPokemonBoxes);

        battleLayout.getChildren().addAll(playerTurnLabel, battleProgressBox, computerTeamBox, battleBox, playerTeamBox);

        rootItem = new TreeItem<>("Combat Log");
        combatLog = new TreeView<>(rootItem);
        combatLog.setShowRoot(false);
        combatLog.setPrefWidth(350);
        combatLog.setPrefHeight(900);
        combatLog.getStyleClass().add("combat-log");

        VBox sideBox = new VBox(10);
        sideBox.setPadding(new Insets(10));
        sideBox.setPrefWidth(400);
        sideBox.setPrefHeight(800);


        Label sideBoxLabel = new Label("Battle History");
        sideBoxLabel.getStyleClass().add("sidebox-label");
        sideBox.getChildren().addAll(sideBoxLabel, combatLog);

        mainLayout.getChildren().add(sideBox);
        mainLayout.getChildren().addAll(battleLayout);

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene battleScene = new Scene(scrollPane, 700, 700);
        battleScene.getStylesheets().add(getClass().getResource("/combat.css").toExternalForm());
        battleScene.setUserData(this);
        primaryStage.setScene(battleScene);
    }

    private VBox createPokemonBox(Character pokemon) {
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

        Label hpLabel = new Label("HP: " + pokemon.getHP());
        hpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hpLabel.setTextFill(Color.RED);

        pokemonBox.getChildren().addAll(hpProgressBar, hpLabel, pokemonImageView, nameLabel);
        return pokemonBox;
    }

    public void updateHPDisplay() {
        updateTeamHPDisplay(playerTeam, playerPokemonBoxes);
        updateTeamHPDisplay(computerTeam, computerPokemonBoxes);
    }

    private void updateTeamHPDisplay(ObservableList<Character> team, HBox pokemonBoxes) {
        for (int i = 0; i < team.size(); i++) {
            Character pokemon = team.get(i);
            VBox pokemonBox = (VBox) pokemonBoxes.getChildren().get(i);

            ProgressBar hpProgressBar = (ProgressBar) pokemonBox.getChildren().get(0);
            Label hpLabel = (Label) pokemonBox.getChildren().get(1);

            hpProgressBar.setProgress(pokemon.getHP() / 1000.0);
            hpLabel.setText("HP: " + pokemon.getHP());
        }
    }

    public void checkAndRemoveFaintedPokemon() {
        checkTeamForFaintedPokemon(playerTeam, playerPokemonBoxes);
        checkTeamForFaintedPokemon(computerTeam, computerPokemonBoxes);
    }

    private void checkTeamForFaintedPokemon(ObservableList<Character> team, HBox pokemonBoxes) {
        List<Integer> faintedIndices = new ArrayList<>();

        for (int i = 0; i < team.size(); i++) {
            Character pokemon = team.get(i);
            if (pokemon.getHP() <= 0) {
                VBox pokemonBox = (VBox) pokemonBoxes.getChildren().get(i);
                pokemonBox.setStyle("-fx-background-color: red;");
                pokemonBox.setDisable(true);
                faintedIndices.add(i);
            }
        }

        for (int i = faintedIndices.size() - 1; i >= 0; i--) {
            int index = faintedIndices.get(i);
            team.remove(index);
            pokemonBoxes.getChildren().remove(index);

            BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) primaryStage.getScene().getUserData();
            if (team == playerTeam) {
                battleSceneBuilder.getBattleBox().addPokemon(battleSceneBuilder.getRandomPokemonFromTeam(playerTeam), true, computerTeam);
            } else if (team == computerTeam) {
                // Handle computer team logic
            }
        }

        updateHPDisplay();
    }

    public BattleBox getBattleBox() {
        return battleBox;
    }

    private Character getRandomPokemonFromTeam(ObservableList<Character> team) {
        if (team.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return team.get(random.nextInt(team.size()));
    }

    public void checkWinner(ObservableList<Character> playerTeam, ObservableList<Character> computerTeam) {
        String winner = null;
        if (playerTeam.isEmpty()) {
            winner = "Computer";
            appendToLog(rootItem, "Player has no more Pokémon. Computer wins!");
        } else if (computerTeam.isEmpty()) {
            winner = "Player";
            appendToLog(rootItem, "Computer has no more Pokémon. Player wins!");
        }
        if (winner != null) {
            logAndDisplayAttack("", "", "", "", 0, "", "", false, winner);
        }
    }

    public void logAndDisplayAttack(String attackingTeam, String attackingPokemon, String attackUsed, String targetPokemon, int damageCaused, String effectiveness, String statusEffects, boolean isKO, String winner) {
        BattleHistory battleHistory = new BattleHistory(turnNumber, attackingTeam, attackingPokemon, attackUsed, targetPokemon, damageCaused, effectiveness, statusEffects, isKO, winner);
        rootItem.getChildren().add(new TreeItem<>(battleHistory.toString()));
        combatLog.refresh();
        turnNumber++;
    }

    private void updateBattleHistory(String attackingTeam, String attackingPokemon, String attackUsed, String targetPokemon, int damageCaused) {
        String logMessage = attackingTeam + " team: " + attackingPokemon + " used " + attackUsed + " on " + targetPokemon + " causing " + damageCaused + " damage.";
        TreeItem<String> attackItem = new TreeItem<>(logMessage);
        rootItem.getChildren().add(attackItem);
        combatLog.refresh(); // Ensure the TreeView updates
    }

    public void appendToLog(TreeItem<String> parent, String message) {
        TreeItem<String> item = new TreeItem<>(message);
        parent.getChildren().add(item);
    }

    public TreeItem<String> getRootItem() {
        return rootItem;
    }
}