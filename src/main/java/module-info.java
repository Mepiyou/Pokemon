module com.example.pokemon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.pokemon to javafx.fxml;

    exports com.example.pokemon;
    exports com.example.pokemon.Game;


}