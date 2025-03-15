package com.example.pokemon.Game;

public class BattleHistory {
    private int turnNumber;
    private String attackingTeam;
    private String attackingPokemon;
    private String attackUsed;
    private String targetPokemon;
    private int damageCaused;
    private String effectiveness;
    private String statusEffects;
    private boolean isKO;
    private String winner;

    public BattleHistory(int turnNumber, String attackingTeam, String attackingPokemon, String attackUsed, String targetPokemon, int damageCaused, String effectiveness, String statusEffects, boolean isKO, String winner) {
        this.turnNumber = turnNumber;
        this.attackingTeam = attackingTeam;
        this.attackingPokemon = attackingPokemon;
        this.attackUsed = attackUsed;
        this.targetPokemon = targetPokemon;
        this.damageCaused = damageCaused;
        this.effectiveness = effectiveness;
        this.statusEffects = statusEffects;
        this.isKO = isKO;
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Turn " + turnNumber + ": " + attackingPokemon + " from the " + attackingTeam + " team used " + attackUsed +
                " on " + targetPokemon + " causing " + damageCaused + " damage. Effectiveness: " + effectiveness +
                ". Status Effects: " + statusEffects + (isKO ? ". " + targetPokemon + " is KO!" : "") +
                (winner != null ? ". Winner: " + winner : "");
    }
}