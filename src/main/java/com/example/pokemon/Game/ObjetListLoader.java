package com.example.pokemon.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjetListLoader {

    public static List<Objet> loadObjectsFromCSV(String filePath) {
        List<Objet> objects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                String name = record[0];
                String description = record[1];
                String type = record[2];
                Objet objet = new Objet(name, description, type);
                objects.add(objet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }
}