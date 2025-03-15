/*
package com.example.pokemon.Game;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleBox extends StackPane {

private ImageView backgroundImageView;
private Pane pokemonContainer;
private Character leftPokemon;
private Character rightPokemon;
private ImageView leftImageView;
private ImageView rightImageView;
private Label battleLabel;
private List<BattleHistory> battleHistoryList = new ArrayList<>();

private BattleBox battleBox;

public BattleBox() {
    this.setAlignment(Pos.CENTER);
    this.setStyle("-fx-padding: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px;");

    battleLabel = new Label("Battle Box");
    battleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    this.getChildren().add(battleLabel);

    try {
        backgroundImageView = new ImageView(new Image("https://i.pinimg.com/736x/0a/d7/40/0ad740bdde2d5ed0f5a641ebefaff38b.jpg"));
    } catch (Exception e) {
        System.err.println("Error loading background image: " + e.getMessage());
        backgroundImageView = new ImageView();
    }
    backgroundImageView.setFitWidth(1000);
    backgroundImageView.setFitHeight(400);

    pokemonContainer = new Pane();
    pokemonContainer.setPrefSize(800, 400);

    leftImageView = new ImageView();
    rightImageView = new ImageView();

    getChildren().addAll(backgroundImageView, pokemonContainer);
}

public BattleBox getBattleBox() {
    return battleBox;
}

public boolean isLeftSideOccupied() {
    return leftPokemon != null;
}

public Character getLeftPokemon() {
    return leftPokemon;
}

public void addPokemon(Character pokemon, boolean isLeftSide, ObservableList<Character> computerTeam) {
    addRandomComputerPokemon(computerTeam);
    VBox pokemonBox = new VBox(10);
    pokemonBox.setAlignment(Pos.CENTER);

    if (pokemon == null) {
        // Declare the opposing team as the winner
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Winner");

        if (isLeftSide) {
            alert.setContentText("Computer team wins!");
            leftPokemon = pokemon;
            leftImageView.setImage(null);
        } else {
            alert.setContentText("Player team wins!");
            rightPokemon = pokemon;
            rightImageView.setImage(null);
        }

        alert.showAndWait();
        return;
    }

    ImageView pokemonImageView = new ImageView(new Image(pokemon.getCharImg()));
    pokemonImageView.setFitHeight(200);
    pokemonImageView.setFitWidth(200);

    ProgressBar hpProgressBar = new ProgressBar();
    hpProgressBar.getStyleClass().add("hp-progress-bar");
    hpProgressBar.setProgress(pokemon.getHP() / 1000.0);

    Label hpLabel = new Label("HP: " + pokemon.getHP());
    hpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    hpLabel.setTextFill(Color.RED);

    pokemonBox.getChildren().addAll(hpLabel, pokemonImageView, hpProgressBar);

    if (isLeftSide) {
        leftPokemon = pokemon;
        leftImageView.setImage(new Image(pokemon.getCharImg()));

        if (!pokemonContainer.getChildren().contains(leftImageView)) {
            pokemonContainer.getChildren().add(0, leftImageView);
            positionXY();
        }
    }
}

public void addRandomComputerPokemon(ObservableList<Character> computerTeam) {
    if (computerTeam.isEmpty()) {
        return;
    }
    Random random = new Random();
    int randomIndex = random.nextInt(computerTeam.size());
    Character randomPokemon = computerTeam.get(randomIndex);

    VBox pokemonBox = new VBox(10);
    pokemonBox.setAlignment(Pos.CENTER);

    ImageView pokemonImageView = new ImageView(new Image(randomPokemon.getCharImg()));
    pokemonImageView.setFitHeight(200);
    pokemonImageView.setFitWidth(200);

    ProgressBar hpProgressBar = new ProgressBar();
    hpProgressBar.getStyleClass().add("hp-progress-bar");
    hpProgressBar.setProgress(randomPokemon.getHP() / 1000.0);

    pokemonBox.getChildren().addAll(pokemonImageView, hpProgressBar);

    rightPokemon = randomPokemon; // Update the rightPokemon reference
    rightImageView.setImage(new Image(randomPokemon.getCharImg()));
    if (!pokemonContainer.getChildren().contains(rightImageView)) {
        pokemonContainer.getChildren().add(rightImageView);
        positionXY();
    }
}

public void positionXY() {
    leftImageView.setLayoutX(350);
    leftImageView.setLayoutY(200);
    rightImageView.setLayoutX(650);
    rightImageView.setLayoutY(200);
}

public void setAttackButtons(Button[] attackButtons, Button specialAttackButton, Button applieObjectButton) {
    Character character = leftPokemon;
    if (character != null) {
        List<Attaque> attacks = character.getAttackList();

        for (int i = 0; i < 5; i++) {
            if (i < attacks.size()) {
                Attaque attack = attacks.get(i);
                attackButtons[i].setText(attack.getName());
                attackButtons[i].setOnAction(event -> {
                    System.out.println("Damage: " + attack.getDamage());
                    leftPokemon.PhysicalHit(rightPokemon, attack.getDamage());
                    System.out.println("Right Pokémon HP: " + rightPokemon.getHP());
                    ((BattleSceneBuilder) getScene().getUserData()).updateHPDisplay();
                    BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) getScene().getUserData();
                    battleSceneBuilder.logAndDisplayAttack(" \n------------------------- \n YOU \n----------------------- \n  Your team", rightPokemon.getName(), attack.getName(), rightPokemon.getName(), attack.getDamage());
                    TreeItem<String> attackItem = new TreeItem<>("You used " + attack.getName() + " on " + leftPokemon.getName() + "! \n ------------------------- \n");
                    battleSceneBuilder.appendToLog(battleSceneBuilder.getRootItem(), attackItem.getValue());

                    ((BattleSceneBuilder) getScene().getUserData()).checkAndRemoveFaintedPokemon(); // Check and remove fainted Pokémon
                    computerAttack();
            });

                specialAttackButton.setText("Special Attack");
                specialAttackButton.setOnAction(event -> {
                    leftPokemon.specialHit(rightPokemon);
                    System.out.println("Right Pokémon HP: " + rightPokemon.getHP());
                    ((BattleSceneBuilder) getScene().getUserData()).updateHPDisplay();
                    BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) getScene().getUserData();
                    battleSceneBuilder.logAndDisplayAttack("\n----------------------- \n YOU \n----------------------- \n  Your team", rightPokemon.getName(), attack.getName(), rightPokemon.getName(), attack.getDamage());
                    TreeItem<String> attackItem = new TreeItem<>("You used " + attack.getName() + " on " + leftPokemon.getName() + "! \n------------------------- \n");
                    ((BattleSceneBuilder) getScene().getUserData()).checkAndRemoveFaintedPokemon(); // Check and remove fainted Pokémon
                    computerAttack();
                });

                applieObjectButton.setOnAction(event -> {
                    Activate(leftPokemon.getObjet(), leftPokemon);
                    ((BattleSceneBuilder) getScene().getUserData()).updateHPDisplay();
                    ((BattleSceneBuilder) getScene().getUserData()).checkAndRemoveFaintedPokemon(); // Check and remove fainted Pokémon
                });
            }
        }
    }
}

public void computerAttack() {
    if (rightPokemon == null || leftPokemon == null) {
        System.out.println("No Pokémon available for the computer to attack!");
        return;
    }

    // Choose a random attack from the computer's Pokémon attack list
    List<Attaque> attacks = rightPokemon.getAttackList();
    if (attacks.isEmpty()) {
        System.out.printf("No attacks available for %s!%n", rightPokemon.getName());
        return;
    }
    Random random = new Random();
    Attaque chosenAttack = attacks.get(random.nextInt(attacks.size()));

    // Inflict the attack on the user's Pokémon
    rightPokemon.PhysicalHit(leftPokemon, chosenAttack.getDamage());

    // Display the attack details
    System.out.printf("%s used %s on %s!%n", rightPokemon.getName(), chosenAttack.getName(), leftPokemon.getName());
    System.out.printf("%s HP: %d%n", leftPokemon.getName(), leftPokemon.getHP());

    // Update the HP display and check for fainted Pokémon

    // Example values for effectiveness and status effects
    String effectiveness = "Effective";
    String statusEffects = "None";
    boolean isKO = leftPokemon.getHP() <= 0;

    BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) getScene().getUserData();
    battleSceneBuilder.logAndDisplayAttack("Computer", rightPokemon.getName(), chosenAttack.getName(), leftPokemon.getName(), chosenAttack.getDamage(), effectiveness, statusEffects, isKO, null);
    battleSceneBuilder.updateHPDisplay();
    battleSceneBuilder.checkAndRemoveFaintedPokemon();


BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) getScene().getUserData();
    battleSceneBuilder.logAndDisplayAttack("\n ------------------------- \n COMPUTER \n----------------------- \n Computer", rightPokemon.getName(), chosenAttack.getName(), leftPokemon.getName(), chosenAttack.getDamage());
    TreeItem<String> attackItem = new TreeItem<>("Computer used " + chosenAttack.getName() + " on " + leftPokemon.getName() + " ! \n------------------------- \n");
    battleSceneBuilder.appendToLog(battleSceneBuilder.getRootItem(), attackItem.getValue());
    battleSceneBuilder.updateHPDisplay();
    battleSceneBuilder.checkAndRemoveFaintedPokemon();

}



public void Activate(Objet objet, Character pokemon) {
    if (objet == null) {
        System.out.println("No object provided.");
        return;
    }

    switch (objet.getType()) {
        case "RESTES":
            Restes(pokemon);
            break;
        case "AguavBerry":
           AguavBerry(pokemon);
            break;
        case "LUNETTE_DE_SOLEIL":
            System.out.println("Object: " + objet.getName() + " - Effect: Increases the power of Dark-type moves by 1.2x.");
            break;
        case "POTION":
            Potion(pokemon);
            System.out.println("Object: " + objet.getName() + " - Effect: Heals 20 HP when HP drops below 50%.");
            break;
        case "BERRY":
           Berry(pokemon);
            break;
        default:
            System.out.println("Object: " + objet.getName() + " - Effect: Unknown.");
            break;
    }
}

public void Potion(Character pokemon) {
    int currentHP = pokemon.getHP();
    int restoredHP = currentHP + 50;
    pokemon.setHP(restoredHP);
}

public void Restes(Character pokemon) {
    int currentHP = pokemon.getHP();
    int restoredHP = currentHP + 8;
    pokemon.setHP(restoredHP);
}

private void AguavBerry(Character pokemon) {
    System.out.println("Object: Aguav Berry - Effect: Restores 1/3 max HP at 1/4 max HP or less; confuses if -SpD Nature. Single use.");
    int maxHP = 1000;
    int currentHP = pokemon.getHP();
    if (currentHP <= maxHP / 4) {
        pokemon.setHP(currentHP + maxHP / 3);
    }
}

public void logAttack(String attackingTeam, String attackingPokemon, String attackUsed, String targetPokemon, int damageCaused) {
    BattleHistory history = new BattleHistory(attackingTeam, attackingPokemon, attackUsed, targetPokemon, damageCaused);
    battleHistoryList.add(history);
    System.out.println(history); // For debugging purposes
}

private void Berry(Character pokemon) {
    System.out.println("Object: Berry - Effect: Heals 10 HP when HP drops below 25%.");
    int maxHP = 1000;
    int currentHP = pokemon.getHP();
    if (currentHP <= maxHP / 4) {
        pokemon.setHP(currentHP + 10);
    }
}
public List<BattleHistory> getBattleHistoryList() {
    return battleHistoryList;
}



}
*/

package com.example.pokemon.Game;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleBox extends StackPane {

    private ImageView backgroundImageView;
    private Pane pokemonContainer;
    private Character leftPokemon;
    private Character rightPokemon;
    private ImageView leftImageView;
    private ImageView rightImageView;
    private Label battleLabel;
    private List<BattleHistory> battleHistoryList = new ArrayList<>();
    private int turnNumber = 1;

    private BattleBox battleBox;

    public BattleBox() {
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-padding: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px;");

        battleLabel = new Label("Battle Box");
        battleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.getChildren().add(battleLabel);

        try {
            backgroundImageView = new ImageView(new Image("https://i.pinimg.com/736x/0a/d7/40/0ad740bdde2d5ed0f5a641ebefaff38b.jpg"));
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            backgroundImageView = new ImageView();
        }
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(400);

        pokemonContainer = new Pane();
        pokemonContainer.setPrefSize(800, 400);

        leftImageView = new ImageView();
        rightImageView = new ImageView();

        getChildren().addAll(backgroundImageView, pokemonContainer);
    }

    public BattleBox getBattleBox() {
        return battleBox;
    }

    public boolean isLeftSideOccupied() {
        return leftPokemon != null;
    }

    public Character getLeftPokemon() {
        return leftPokemon;
    }

    public void addPokemon(Character pokemon, boolean isLeftSide, ObservableList<Character> computerTeam) {
        addRandomComputerPokemon(computerTeam);
        VBox pokemonBox = new VBox(10);
        pokemonBox.setAlignment(Pos.CENTER);

        if (pokemon == null) {
            // Declare the opposing team as the winner
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Winner");

            if (isLeftSide) {
                alert.setContentText("Computer team wins!");
                leftPokemon = pokemon;
                leftImageView.setImage(null);
            } else {
                alert.setContentText("Player team wins!");
                rightPokemon = pokemon;
                rightImageView.setImage(null);
            }

            alert.showAndWait();
            return;
        }

        ImageView pokemonImageView = new ImageView(new Image(pokemon.getCharImg()));
        pokemonImageView.setFitHeight(200);
        pokemonImageView.setFitWidth(200);

        ProgressBar hpProgressBar = new ProgressBar();
        hpProgressBar.getStyleClass().add("hp-progress-bar");
        hpProgressBar.setProgress(pokemon.getHP() / 1000.0);

        Label hpLabel = new Label("HP: " + pokemon.getHP());
        hpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hpLabel.setTextFill(Color.RED);

        pokemonBox.getChildren().addAll(hpLabel, pokemonImageView, hpProgressBar);

        if (isLeftSide) {
            leftPokemon = pokemon;
            leftImageView.setImage(new Image(pokemon.getCharImg()));

            if (!pokemonContainer.getChildren().contains(leftImageView)) {
                pokemonContainer.getChildren().add(0, leftImageView);
                positionXY();
            }
        }
    }

    public void addRandomComputerPokemon(ObservableList<Character> computerTeam) {
        if (computerTeam.isEmpty()) {
            return;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(computerTeam.size());
        Character randomPokemon = computerTeam.get(randomIndex);

        VBox pokemonBox = new VBox(10);
        pokemonBox.setAlignment(Pos.CENTER);

        ImageView pokemonImageView = new ImageView(new Image(randomPokemon.getCharImg()));
        pokemonImageView.setFitHeight(200);
        pokemonImageView.setFitWidth(200);

        ProgressBar hpProgressBar = new ProgressBar();
        hpProgressBar.getStyleClass().add("hp-progress-bar");
        hpProgressBar.setProgress(randomPokemon.getHP() / 1000.0);

        pokemonBox.getChildren().addAll(pokemonImageView, hpProgressBar);

        rightPokemon = randomPokemon; // Update the rightPokemon reference
        rightImageView.setImage(new Image(randomPokemon.getCharImg()));
        if (!pokemonContainer.getChildren().contains(rightImageView)) {
            pokemonContainer.getChildren().add(rightImageView);
            positionXY();
        }
    }

    public void positionXY() {
        leftImageView.setLayoutX(350);
        leftImageView.setLayoutY(200);
        rightImageView.setLayoutX(650);
        rightImageView.setLayoutY(200);
    }

    public void setAttackButtons(Button[] attackButtons, Button specialAttackButton, Button applieObjectButton) {
        Character character = leftPokemon;
        if (character != null) {
            List<Attaque> attacks = character.getAttackList();

            for (int i = 0; i < 5; i++) {
                if (i < attacks.size()) {
                    Attaque attack = attacks.get(i);
                    attackButtons[i].setText(attack.getName());
                    attackButtons[i].setOnAction(event -> {
                        System.out.println("Damage: " + attack.getDamage());
                        leftPokemon.PhysicalHit(rightPokemon, attack.getDamage());
                        System.out.println("Right Pokémon HP: " + rightPokemon.getHP());
                        ((BattleSceneBuilder) getScene().getUserData()).updateHPDisplay();
                        BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) getScene().getUserData();
                        battleSceneBuilder.logAndDisplayAttack(" \n------------------------- \n YOU \n----------------------- \n  Your team", rightPokemon.getName(), attack.getName(), rightPokemon.getName(), attack.getDamage(), "Effective", "None", rightPokemon.getHP() <= 0, null);
                        TreeItem<String> attackItem = new TreeItem<>("You used " + attack.getName() + " on " + leftPokemon.getName() + "! \n ------------------------- \n");
                        battleSceneBuilder.appendToLog(battleSceneBuilder.getRootItem(), attackItem.getValue());

                        ((BattleSceneBuilder) getScene().getUserData()).checkAndRemoveFaintedPokemon(); // Check and remove fainted Pokémon
                        computerAttack();
                    });

                    specialAttackButton.setText("Special Attack");
                    specialAttackButton.setOnAction(event -> {
                        leftPokemon.specialHit(rightPokemon);
                        System.out.println("Right Pokémon HP: " + rightPokemon.getHP());
                        ((BattleSceneBuilder) getScene().getUserData()).updateHPDisplay();
                        BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) getScene().getUserData();
                        battleSceneBuilder.logAndDisplayAttack("\n----------------------- \n YOU \n----------------------- \n  Your team", rightPokemon.getName(), attack.getName(), rightPokemon.getName(), attack.getDamage(), "Effective", "None", rightPokemon.getHP() <= 0, null);
                        TreeItem<String> attackItem = new TreeItem<>("You used " + attack.getName() + " on " + leftPokemon.getName() + "! \n------------------------- \n");
                        ((BattleSceneBuilder) getScene().getUserData()).checkAndRemoveFaintedPokemon(); // Check and remove fainted Pokémon
                        computerAttack();
                    });

                    applieObjectButton.setOnAction(event -> {
                        Activate(leftPokemon.getObjet(), leftPokemon);
                        ((BattleSceneBuilder) getScene().getUserData()).updateHPDisplay();
                        ((BattleSceneBuilder) getScene().getUserData()).checkAndRemoveFaintedPokemon(); // Check and remove fainted Pokémon
                    });
                }
            }
        }
    }

    public void computerAttack() {
        if (rightPokemon == null || leftPokemon == null) {
            System.out.println("No Pokémon available for the computer to attack!");
            return;
        }

        // Choose a random attack from the computer's Pokémon attack list
        List<Attaque> attacks = rightPokemon.getAttackList();
        if (attacks.isEmpty()) {
            System.out.printf("No attacks available for %s!%n", rightPokemon.getName());
            return;
        }
        Random random = new Random();
        Attaque chosenAttack = attacks.get(random.nextInt(attacks.size()));

        // Inflict the attack on the user's Pokémon
        rightPokemon.PhysicalHit(leftPokemon, chosenAttack.getDamage());

        // Display the attack details
        System.out.printf("%s used %s on %s!%n", rightPokemon.getName(), chosenAttack.getName(), leftPokemon.getName());
        System.out.printf("%s HP: %d%n", leftPokemon.getName(), leftPokemon.getHP());

        // Update the HP display and check for fainted Pokémon

        // Example values for effectiveness and status effects
        String effectiveness = "Effective";
        String statusEffects = "None";
        boolean isKO = leftPokemon.getHP() <= 0;

        BattleSceneBuilder battleSceneBuilder = (BattleSceneBuilder) getScene().getUserData();
        battleSceneBuilder.logAndDisplayAttack("Computer", rightPokemon.getName(), chosenAttack.getName(), leftPokemon.getName(), chosenAttack.getDamage(), effectiveness, statusEffects, isKO, null);
        battleSceneBuilder.updateHPDisplay();
        battleSceneBuilder.checkAndRemoveFaintedPokemon();
    }

    public void Activate(Objet objet, Character pokemon) {
        if (objet == null) {
            System.out.println("No object provided.");
            return;
        }

        switch (objet.getType()) {
            case "RESTES":
                Restes(pokemon);
                break;
            case "AguavBerry":
                AguavBerry(pokemon);
                break;
            case "LUNETTE_DE_SOLEIL":
                System.out.println("Object: " + objet.getName() + " - Effect: Increases the power of Dark-type moves by 1.2x.");
                break;
            case "POTION":
                Potion(pokemon);
                System.out.println("Object: " + objet.getName() + " - Effect: Heals 20 HP when HP drops below 50%.");
                break;
            case "BERRY":
                Berry(pokemon);
                break;
            default:
                System.out.println("Object: " + objet.getName() + " - Effect: Unknown.");
                break;
        }
    }

    public void Potion(Character pokemon) {
        int currentHP = pokemon.getHP();
        int restoredHP = currentHP + 50;
        pokemon.setHP(restoredHP);
    }

    public void Restes(Character pokemon) {
        int currentHP = pokemon.getHP();
        int restoredHP = currentHP + 8;
        pokemon.setHP(restoredHP);
    }

    private void AguavBerry(Character pokemon) {
        System.out.println("Object: Aguav Berry - Effect: Restores 1/3 max HP at 1/4 max HP or less; confuses if -SpD Nature. Single use.");
        int maxHP = 1000;
        int currentHP = pokemon.getHP();
        if (currentHP <= maxHP / 4) {
            pokemon.setHP(currentHP + maxHP / 3);
        }
    }

    public void logAttack(String attackingTeam, String attackingPokemon, String attackUsed, String targetPokemon, int damageCaused, String effectiveness, String statusEffects, boolean isKO, String winner) {
        BattleHistory history = new BattleHistory(turnNumber, attackingTeam, attackingPokemon, attackUsed, targetPokemon, damageCaused, effectiveness, statusEffects, isKO, winner);
        battleHistoryList.add(history);
        System.out.println(history); // For debugging purposes
        turnNumber++;
    }

    private void Berry(Character pokemon) {
        System.out.println("Object: Berry - Effect: Heals 10 HP when HP drops below 25%.");
        int maxHP = 1000;
        int currentHP = pokemon.getHP();
        if (currentHP <= maxHP / 4) {
            pokemon.setHP(currentHP + 10);
        }
    }

    public List<BattleHistory> getBattleHistoryList() {
        return battleHistoryList;
    }
}