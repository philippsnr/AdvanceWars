module com.example.advancedwars {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.advancedwars to javafx.fxml;
    exports com.example.advancedwars;
}