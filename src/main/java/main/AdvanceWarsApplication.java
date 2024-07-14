package main;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;
import java.io.IOException;
import java.util.Objects;

public class AdvanceWarsApplication extends Application {

    private static final int NUM_MAPS = 6;
    private static final String[] MAP_NAMES = {
            "Little Island", "Eon Springs", "Piston Dam", "Zero Wood", "Cog Isle", "Sabre Range"
    };
    private static final String[] MAP_IMAGE_PATHS = {
            "/images/map-preview/LittleIsland.png",
            "/images/map-preview/EonSprings.png",
            "/images/map-preview/PistonDam.png",
            "/images/map-preview/ZeroWood.png",
            "/images/map-preview/CogIsle.png",
            "/images/map-preview/SabreRange.png"
    };

    private Scene startScreenScene;
    private Scene mapSelectionScene;
    private Scene instructionScene;
    private Scene creditsScene;
    private Scene gameScene;
    private Scene endScreenScene;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.startScreenScene = createStartScreenScene(primaryStage);
        this.mapSelectionScene = createMapSelectionScene(primaryStage);
        this.instructionScene = createInstructionScene(primaryStage);
        this.creditsScene = createCreditsScene(primaryStage);

        primaryStage.setScene(startScreenScene);
        primaryStage.setTitle("Advance Wars");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private Scene createStartScreenScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #648571;");
        Image startImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/front-pic.jpg")));
        ImageView startImageView = new ImageView(startImage);
        double maxHeight = primaryStage.getHeight() - 100;
        startImageView.setFitWidth(maxHeight * startImage.getWidth() / startImage.getHeight());
        startImageView.setFitHeight(maxHeight);
        borderPane.setCenter(startImageView);

        Button startButton = new Button("Press to start");
        startButton.getStyleClass().add("start-button");
        startButton.setOnAction(event -> {primaryStage.setScene(mapSelectionScene); primaryStage.setFullScreen(true);});

        VBox buttonBox = new VBox(startButton);
        buttonBox.setAlignment(Pos.CENTER); // Zentriere den Button
        buttonBox.setPadding(new Insets(20));

        borderPane.setBottom(buttonBox);

        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/menu.css").toExternalForm());
        return scene;
    }

    private Scene createMapSelectionScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #648571;");
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
        scene.getStylesheets().add(getClass().getResource("/menu.css").toExternalForm());
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
            mapButton.getStyleClass().add("map-selection-button");
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
        borderPane.setStyle("-fx-background-color: #648571;");
        VBox instructionsBox = new VBox(10);
        instructionsBox.setPadding(new Insets(10));
        instructionsBox.setAlignment(Pos.TOP_LEFT);
        Text instructionsText = new Text("Anweisungen für das Spiel:\n\n1. Die Steuerung erfolgt ausschließlich mit der Maus\n\n2. Jeder Spieler startet mit den gleichen Truppen, einer Fabrik und 3000 'Geld'\n\n3. Man gewinnt, wenn man alle Truppen des Gegners auslöscht und auch mit einer eigenen Truppe die Gegnerische Fabrik besetzt\n\n4. Bewegen: Drückt man auf eine Truppe so werden diejenigen Felder markiert, auf die man sich auch bewegen kann. Drückt man dann auf eines dieser Felder bewegt sich die Truppe auf das Feld. Ist die Truppe auf dem Feld angekommen, so kann man auf den Knopf 'Warten' drücken. Damit ist der Zug für diese Truppe beendet und die anderen Truppen können bewegt werden. Sind alle Truppen bewegt worden bzw. man will keine Truppen mehr bewegen, dann kann man mit dem Knopf 'Zug beenden' seinen Zug beenden und damit beginnt der Zug des anderen Spielers.\n\n5. Kämpfen: Ist nach dem Bewegen eine gegnerische Truppe in Angriffsreichweite, so kann man auf den Knopf 'Angriff' drücken. Dann werden alle möglichen Ziele mit einem roten Fadenkreuz markiert. Drückt man nun auf eines dieser markierten Truppen so wird diese angegriffen und die Lebensanzeigen werden entsprechend angepasst.\n\n6. Kaufen: Nach jedem Zug erhält man eine gewisse Menge an Geld. Dies wird mit der Zeit immer mehr und ist daher nur rundenabhängig. Drückt man auf seine eigene Fabrik, so erscheinen die Knöpfe, um die Truppen zu kaufen. Drückt man auf einen dieser Knöpfe wird die Truppe gekauft und diese erscheint auf der Fabrik. Diese Truppe kann sich erst im nächsten Zug wieder bewegen.");
        instructionsText.setStyle("-fx-font-size: 18px;");
        instructionsText.setWrappingWidth(1500);

        ScrollPane scrollPane = new ScrollPane(instructionsText);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Button backButton = new Button("Zurück");
        backButton.setOnAction(event -> {
            primaryStage.setScene(mapSelectionScene);
            primaryStage.setFullScreen(true);
        });

        instructionsBox.getChildren().addAll(scrollPane, backButton);
        borderPane.setCenter(instructionsBox);

        Scene scene = new Scene(borderPane, 800, 600);
        return scene;
    }


    private Scene createCreditsScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #648571;");
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
        FXMLLoader fxmlLoader = new FXMLLoader(AdvanceWarsApplication.class.getResource("game scene.fxml"));
        fxmlLoader.setController(new AdvanceWarsController(mapName, this));
        Parent root = fxmlLoader.load();

        root.setStyle("-fx-background-color: #648571;");

        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add(getClass().getResource("/game.css").toExternalForm());
        return scene;
    }

    private Scene createEndScreenScene(Stage primaryStage, int winner) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #648571;");
        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(10));
        contentBox.setAlignment(Pos.CENTER);

        String winnerText = "";
        String color = "";

        if (winner == 1) {
            winnerText = "Spieler Rot hat gewonnen!";
            color = "red";
        }
        else if (winner == 2) {
            winnerText = "Spieler Blau hat gewonnen!";
            color = "blue";
        }

        Image winnerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/end-pic.png")));
        ImageView imageView = new ImageView(winnerImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(900);
        imageView.setFitHeight(900);

        Label winnerLabel = new Label(winnerText);
        winnerLabel.setStyle(String.format("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: %s;", color));

        winnerLabel.setAlignment(Pos.CENTER);

        contentBox.getChildren().addAll(imageView, winnerLabel);
        borderPane.setCenter(contentBox);

        Button backButton = new Button("Zurück zur Map-Auswahl");
        backButton.setOnAction(event -> primaryStage.setScene(mapSelectionScene));
        BorderPane.setMargin(backButton, new Insets(10));
        borderPane.setBottom(backButton);

        return new Scene(borderPane, 1200, 800);
    }

    public void switchToEndScreen(int winner) {
        endScreenScene = createEndScreenScene(primaryStage, winner);
        primaryStage.setScene(endScreenScene);
    }

    public static void main(String[] args) {
        launch();
    }
}