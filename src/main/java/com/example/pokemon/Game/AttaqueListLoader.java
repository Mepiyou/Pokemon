/*
package com.example.pokemon.Game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AttaqueListLoader {

    public static List<Attaque> loadAttacksFromCSV(String filePath) {
        ObservableList<Attaque> attacks = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0];
                int damage = Integer.parseInt(values[1]);
                List<String> availableTo = Arrays.asList(values[2].split(";"));
                attacks.add(new Attaque(name, damage, availableTo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attacks;
    }
}*/

package com.example.pokemon.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttaqueListLoader {

    public static List<Attaque> loadAttacksFromCSV(String filePath) {
        List<Attaque> attacks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] values = line.split(",");
                try {
                    String name = values[0];
                    int damage = Integer.parseInt(values[1].trim());
                    List<String> availableTo = List.of(values[2].trim().split(";"));
                    attacks.add(new Attaque(name, damage, availableTo));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in line: " + line);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attacks;
    }
}