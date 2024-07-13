package com.example.advancedwars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdvanceWarsApplication extends Application {

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
    private Scene gameScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        startScreenScene = createStartScreenScene(primaryStage);
        mapSelectionScene = createMapSelectionScene(primaryStage);
        instructionScene = createInstructionScene(primaryStage);
        creditsScene = createCreditsScene(primaryStage);

        primaryStage.setScene(startScreenScene);
        primaryStage.setTitle("Advance Wars");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private Scene createStartScreenScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        Image startImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/front-pic.jpg")));
        ImageView startImageView = new ImageView(startImage);
        double maxHeight = primaryStage.getHeight() - 100;
        startImageView.setFitWidth(maxHeight * startImage.getWidth() / startImage.getHeight());
        startImageView.setFitHeight(maxHeight);
        borderPane.setCenter(startImageView);

        Button startButton = new Button("Press to start");
        startButton.setOnAction(event -> {primaryStage.setScene(mapSelectionScene); primaryStage.setFullScreen(true);});

        VBox buttonBox = new VBox(startButton);
        buttonBox.setAlignment(Pos.CENTER); // Zentriere den Button
        buttonBox.setPadding(new Insets(20));

        borderPane.setBottom(buttonBox);

        Scene scene = new Scene(borderPane, 800, 600);
        return scene;
    }

    private Scene createMapSelectionScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = createMapSelectionGrid(primaryStage);
        borderPane.setCenter(gridPane);

        HBox bottomButtons = new HBox(10);
        bottomButtons.setPadding(new Insets(10));
        bottomButtons.setAlignment(Pos.BOTTOM_RIGHT);

        Button instructionButton = new Button("Anleitung");
        Button creditsButton = new Button("Credits");
        bottomButtons.getChildren().addAll(instructionButton, creditsButton);

        instructionButton.setOnAction(event -> {primaryStage.setScene(instructionScene); primaryStage.setFullScreen(true);});
        creditsButton.setOnAction(event -> {primaryStage.setScene(creditsScene); primaryStage.setFullScreen(true);});

        borderPane.setBottom(bottomButtons);

        Scene scene = new Scene(borderPane);
        scene.setFill(null);
        return scene;
    }

    private GridPane createMapSelectionGrid(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < NUM_MAPS; i++) {
            Image mapImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(MAP_IMAGE_PATHS[i])));
            ImageView mapView = new ImageView(mapImage);
            mapView.setPreserveRatio(true);
            mapView.setFitWidth(300);
            mapView.setFitHeight(300);

            Button mapButton = new Button(MAP_NAMES[i]);
            int mapIndex = i;
            mapButton.setOnAction(event -> {
                try {
                    gameScene = createGameScene(primaryStage, MAP_NAMES[mapIndex]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                primaryStage.setScene(gameScene);
                primaryStage.setFullScreen(true);
            });
            VBox mapBox = new VBox(10);
            mapBox.getChildren().addAll(mapView, mapButton);
            mapBox.setAlignment(Pos.CENTER);
            gridPane.add(mapBox, i % 3, (i / 3) * 2 + 1);
        }
        return gridPane;
    }

    private Scene createInstructionScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        VBox instructionsBox = new VBox(10);
        instructionsBox.setPadding(new Insets(10));
        instructionsBox.setAlignment(Pos.CENTER);
        Label instructionsLabel = new Label("Hier stehen die Anweisungen für das Spiel...");
        Button backButton = new Button("Zurück");
        backButton.setOnAction(event -> {primaryStage.setScene(mapSelectionScene); primaryStage.setFullScreen(true);});

        instructionsBox.getChildren().addAll(instructionsLabel, backButton);
        borderPane.setBottom(instructionsBox);

        Scene scene = new Scene(borderPane);
        scene.setFill(null);
        return scene;
    }

    private Scene createCreditsScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        VBox creditsBox = new VBox(10);
        creditsBox.setPadding(new Insets(10));
        creditsBox.setAlignment(Pos.CENTER);

        Label creditsLabel = new Label("JAVA Projekt: Advance Wars\n\nErstellt von:\n\n\tPhilipp Staudinger, TIK23\n\n\tLinus Gerlach,        TIT23\n\n\tJanne Nußbaum,     TIT23\n\n\tNils Fleschhut,        TIT23\n");
        creditsLabel.setStyle("-fx-font-size: 22px;");
        creditsLabel.setAlignment(Pos.CENTER);

        Button backButton = new Button("Zurück");
        backButton.setOnAction(event -> {primaryStage.setScene(mapSelectionScene); primaryStage.setFullScreen(true);});

        creditsBox.getChildren().addAll(creditsLabel, backButton);
        borderPane.setCenter(creditsBox);

        return new Scene(borderPane, 1200, 800);
    }

    public Scene createGameScene(Stage primaryStage, String mapName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdvanceWarsApplication.class.getResource("hello-view.fxml"));
        fxmlLoader.setController(new HelloController(mapName));
        Parent root = fxmlLoader.load();

        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}