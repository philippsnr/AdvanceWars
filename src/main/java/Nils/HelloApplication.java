package com.example.startscreen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloApplication extends Application {

    private static final int NUM_MAPS = 6;
    private static final String[] MAP_NAMES = {
            "Little Island", "Eon Springs", "Piston Dam", "Zero Wood", "Cog Isle", "Sabre Range"
    };
    private static final String[] MAP_IMAGE_PATHS = {
            "/images/LittleIsland.png",
            "/images/EonSprings.png",
            "/images/PistonDam.png",
            "/images/ZeroWood.png",
            "/images/CogIsle.png",
            "/images/SabreRange.png"
    };

    private Scene startScreenScene;
    private Scene mapSelectionScene;
    private Scene instructionScene;
    private Scene creditsScene;
    private Scene selectedMapScene;

    @Override
    public void start(Stage primaryStage) {
        // Erstelle die Szenen für die verschiedenen Ansichten
        startScreenScene = createStartScreenScene(primaryStage);
        mapSelectionScene = createMapSelectionScene(primaryStage);
        instructionScene = createInstructionScene(primaryStage);
        creditsScene = createCreditsScene(primaryStage);
        selectedMapScene = createSelectedMapScene(primaryStage);

        // Setze die StartScreen-Szene
        primaryStage.setScene(startScreenScene);
        primaryStage.setTitle("Start Screen");

        // Stelle sicher, dass die Anwendung im Vollbildmodus startet
        primaryStage.setFullScreen(true);

        // Zeige das Hauptfenster an
        primaryStage.show();
    }

    private Scene createStartScreenScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        // Laden des Bildes für den Startbildschirm
        Image startImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/front-pic.jpg")));
        ImageView startImageView = new ImageView(startImage);

        // Begrenze die Höhe des Bildes, damit der Button sichtbar bleibt
        double maxHeight = primaryStage.getHeight() - 100; // Platz für den Button lassen
        startImageView.setFitWidth(maxHeight * startImage.getWidth() / startImage.getHeight());
        startImageView.setFitHeight(maxHeight);

        borderPane.setCenter(startImageView);

        // Button "Press to start"
        Button startButton = new Button("Press to start");
        startButton.setOnAction(event -> primaryStage.setScene(mapSelectionScene));

        VBox buttonBox = new VBox(startButton);
        buttonBox.setAlignment(Pos.CENTER); // Zentriere den Button
        buttonBox.setPadding(new Insets(20));

        borderPane.setBottom(buttonBox);

        // Erstelle und gebe die Szene zurück
        Scene scene = new Scene(borderPane, 800, 600);
        return scene;
    }


    private Scene createMapSelectionScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = createMapSelectionGrid(primaryStage);
        borderPane.setCenter(gridPane);

        // Bottom Buttons (Anleitung und Credits)
        HBox bottomButtons = new HBox(10);
        bottomButtons.setPadding(new Insets(10));
        bottomButtons.setAlignment(Pos.BOTTOM_RIGHT);

        Button instructionButton = new Button("Anleitung");
        Button creditsButton = new Button("Credits");
        bottomButtons.getChildren().addAll(instructionButton, creditsButton);

        instructionButton.setOnAction(event -> primaryStage.setScene(instructionScene));
        creditsButton.setOnAction(event -> primaryStage.setScene(creditsScene));

        borderPane.setBottom(bottomButtons);

        // Erstelle und gebe die Szene zurück
        Scene scene = new Scene(borderPane);
        scene.setFill(null); // Transparenten Hintergrund setzen für Vollbildmodus
        return scene;
    }

    private GridPane createMapSelectionGrid(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        gridPane.setAlignment(Pos.CENTER); // Zentriere den gesamten GridPane

        for (int i = 0; i < NUM_MAPS; i++) {
            Image mapImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(MAP_IMAGE_PATHS[i])));
            ImageView mapView = new ImageView(mapImage);
            mapView.setPreserveRatio(true);
            mapView.setFitWidth(300);
            mapView.setFitHeight(300);

            Button mapButton = new Button(MAP_NAMES[i]);
            int mapIndex = i;  // Notwendig für den Lambda-Ausdruck
            mapButton.setOnAction(event -> {
                primaryStage.setScene(selectedMapScene);
                // Hier könnte zusätzliche Logik für die ausgewählte Karte hinzugefügt werden
                System.out.println("Selected map: " + MAP_NAMES[mapIndex]);
            });

            VBox mapBox = new VBox(10);
            mapBox.getChildren().addAll(mapView, mapButton);
            mapBox.setAlignment(Pos.CENTER); // Zentriere den Inhalt der VBox

            gridPane.add(mapBox, i % 3, (i / 3) * 2 + 1); // Füge die VBox in das GridPane ein
        }
        return gridPane;
    }

    private Scene createInstructionScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        // Inhalt der Anleitungsszene
        VBox instructionsBox = new VBox(10);
        instructionsBox.setPadding(new Insets(10));
        instructionsBox.setAlignment(Pos.CENTER);
        Label instructionsLabel = new Label("Hier stehen die Anweisungen für das Spiel...");
        Button backButton = new Button("Zurück");
        backButton.setOnAction(event -> primaryStage.setScene(mapSelectionScene));

        instructionsBox.getChildren().addAll(instructionsLabel, backButton);
        borderPane.setBottom(instructionsBox);

        // Erstelle und gebe die Szene zurück
        Scene scene = new Scene(borderPane);
        scene.setFill(null); // Transparenten Hintergrund setzen für Vollbildmodus
        return scene;
    }

    private Scene createCreditsScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        VBox creditsBox = new VBox(10);
        creditsBox.setPadding(new Insets(10));
        creditsBox.setAlignment(Pos.CENTER);

        // Label für Credits mit angepassten Eigenschaften
        Label creditsLabel = new Label("JAVA Projekt: Advance Wars\n\nErstellt von:\n\n\tPhilipp Staudinger, TIK23\n\n\tLinus Gerlach,        TIT23\n\n\tJanne Nußbaum,     TIT23\n\n\tNils Fleschhut,        TIT23\n");
        creditsLabel.setStyle("-fx-font-size: 22px;"); // Schriftgröße anpassen
        creditsLabel.setAlignment(Pos.CENTER); // Text zentrieren

        Button backButton = new Button("Zurück");
        backButton.setOnAction(event -> primaryStage.setScene(mapSelectionScene));

        creditsBox.getChildren().addAll(creditsLabel, backButton);
        borderPane.setCenter(creditsBox);

        return new Scene(borderPane, 1200, 800);
    }


    private Scene createSelectedMapScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        // Hier könntest du die Szene für die ausgewählte Karte erstellen
        // Beispiel:
        // Label selectedMapLabel = new Label("Du hast die Karte ausgewählt: " + selectedMapName);
        // borderPane.setCenter(selectedMapLabel);

        // Erstelle und gebe die Szene zurück
        Scene scene = new Scene(borderPane);
        scene.setFill(null); // Transparenten Hintergrund setzen für Vollbildmodus
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
