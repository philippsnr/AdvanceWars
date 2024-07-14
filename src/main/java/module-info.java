module com.example.advancedwars {
    requires javafx.controls;
    requires javafx.fxml;


    opens main to javafx.fxml;
    exports main;
    exports troops;
    opens troops to javafx.fxml;
    exports maps;
    opens maps to javafx.fxml;
}