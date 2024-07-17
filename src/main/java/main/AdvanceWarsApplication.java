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
    public void start(Stage primaryStage) {
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
        startButton.setOnAction(_ -> {primaryStage.setScene(mapSelectionScene); primaryStage.setFullScreen(true);});

        VBox buttonBox = new VBox(startButton);
        buttonBox.setAlignment(Pos.CENTER); // Zentriere den Button
        buttonBox.setPadding(new Insets(20));

        borderPane.setBottom(buttonBox);

        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/menu.css")).toExternalForm());
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
        instructionButton.getStyleClass().add("menu-button");
        Button creditsButton = new Button("Credits");
        creditsButton.getStyleClass().add("menu-button");
        bottomButtons.getChildren().addAll(instructionButton, creditsButton);

        instructionButton.setOnAction(_ -> {primaryStage.setScene(instructionScene); primaryStage.setFullScreen(true);});
        creditsButton.setOnAction(_ -> {primaryStage.setScene(creditsScene); primaryStage.setFullScreen(true);});

        borderPane.setBottom(bottomButtons);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/menu.css")).toExternalForm());
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
            mapButton.getStyleClass().add("menu-button");
            int mapIndex = i;
            mapButton.setOnAction(_ -> {
                try {
                    gameScene = createGameScene(MAP_NAMES[mapIndex]);
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
        borderPane.setStyle("-fx-background-color: #648571;"); // Fallback background color
        borderPane.getStyleClass().add("instructions-background");

        VBox instructionsBox = new VBox(20);
        instructionsBox.setPadding(new Insets(20));
        instructionsBox.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Anweisungen für das Spiel");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white; -fx-font-weight: bold; -fx-effect: dropshadow(one-pass-box, black, 8, 0, 0, 0);");
        titleLabel.setAlignment(Pos.CENTER);

        Text instruction1 = new Text("1. Die Steuerung erfolgt ausschließlich mit der Maus\n\n");
        Text instruction2 = new Text("2. Jeder Spieler startet mit den gleichen Truppen, einer Fabrik und 3000 'Geld'\n\n");
        Text instruction3 = new Text("3. Man gewinnt, wenn man alle Truppen des Gegners auslöscht und auch mit einer eigenen Truppe die gegnerische Fabrik besetzt\n\n");
        Text instruction4 = new Text("4. Bewegen:\n");
        Text instruction4Detail = new Text("Drückt man auf eine Truppe, so werden diejenigen Felder markiert, auf die man sich auch bewegen kann. Drückt man dann auf eines dieser Felder, bewegt sich die Truppe auf das Feld. Ist die Truppe auf dem Feld angekommen, so kann man auf den Knopf 'Warten' drücken. Damit ist der Zug für diese Truppe beendet und die anderen Truppen können bewegt werden. Sind alle Truppen bewegt worden bzw. man will keine Truppen mehr bewegen, dann kann man mit dem Knopf 'Zug beenden' seinen Zug beenden und damit beginnt der Zug des anderen Spielers.\n\n");
        Text instruction5 = new Text("5. Kämpfen:\n");
        Text instruction5Detail = new Text("Ist nach dem Bewegen eine gegnerische Truppe in Angriffsreichweite, so kann man auf den Knopf 'Angriff' drücken. Dann werden alle möglichen Ziele mit einem roten Fadenkreuz markiert. Drückt man nun auf eine dieser markierten Truppen, so wird diese angegriffen und die Lebensanzeigen werden entsprechend angepasst.\n(Wichtig bei Artillerie: Darf nur in 2-3 Felder Entfernung angreifen, wenn man sie vorher nicht bewegt hat)\n\n");
        Text instruction6 = new Text("6. Kaufen:\n");
        Text instruction6Detail = new Text("Nach jedem Zug erhält man eine gewisse Menge an Geld. Dies wird mit der Zeit immer mehr und ist daher nur rundenabhängig. Drückt man auf seine eigene Fabrik, so erscheinen die Knöpfe, um die Truppen zu kaufen. Drückt man auf einen dieser Knöpfe, wird die Truppe gekauft und diese erscheint auf der Fabrik. Diese Truppe kann sich erst im nächsten Zug wieder bewegen.\n");

        instruction1.setFill(Color.WHITE);
        instruction2.setFill(Color.WHITE);
        instruction3.setFill(Color.WHITE);
        instruction4.setFill(Color.WHITE);
        instruction4Detail.setFill(Color.WHITE);
        instruction5.setFill(Color.WHITE);
        instruction5Detail.setFill(Color.WHITE);
        instruction6.setFill(Color.WHITE);
        instruction6Detail.setFill(Color.WHITE);

        instruction1.setStyle("-fx-font-size: 18px;");
        instruction2.setStyle("-fx-font-size: 18px;");
        instruction3.setStyle("-fx-font-size: 18px;");
        instruction4.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        instruction4Detail.setStyle("-fx-font-size: 18px;");
        instruction5.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        instruction5Detail.setStyle("-fx-font-size: 18px;");
        instruction6.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        instruction6Detail.setStyle("-fx-font-size: 18px;");

        VBox textBox = new VBox(10, instruction1, instruction2, instruction3, instruction4, instruction4Detail, instruction5, instruction5Detail, instruction6, instruction6Detail);
        textBox.setAlignment(Pos.TOP_LEFT);

        Button backButton = new Button("Zurück");
        backButton.getStyleClass().add("menu-button");
        backButton.setOnAction(_ -> {
            primaryStage.setScene(mapSelectionScene);
            primaryStage.setFullScreen(true);
        });

        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        instructionsBox.getChildren().addAll(titleLabel, textBox, buttonBox);
        borderPane.setCenter(instructionsBox);

        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/menu.css")).toExternalForm());
        return scene;
    }

    private Scene createCreditsScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #648571;");

        VBox creditsBox = new VBox(20);
        creditsBox.setPadding(new Insets(20));
        creditsBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("JAVA Projekt: Advance Wars");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white; -fx-font-weight: bold; -fx-effect: dropshadow(one-pass-box, black, 8, 0, 0, 0);");
        titleLabel.setAlignment(Pos.CENTER);

        GridPane creditsGrid = new GridPane();
        creditsGrid.setAlignment(Pos.CENTER);
        creditsGrid.setHgap(20);
        creditsGrid.setVgap(10);

        addCredit(creditsGrid, "Philipp Staudinger", "TIK23", 1);
        addCredit(creditsGrid, "Linus Gerlach", "TIT23", 2);
        addCredit(creditsGrid, "Janne Nußbaum", "TIT23", 3);
        addCredit(creditsGrid, "Nils Fleschhut", "TIT23", 4);

        Button backButton = new Button("Zurück");
        backButton.getStyleClass().add("menu-button");
        backButton.setOnAction(_ -> {primaryStage.setScene(mapSelectionScene); primaryStage.setFullScreen(true);});

        creditsBox.getChildren().addAll(titleLabel, creditsGrid, backButton);
        borderPane.setCenter(creditsBox);

        Scene scene = new Scene(borderPane, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/menu.css")).toExternalForm());
        return scene;
    }

    private void addCredit(GridPane grid, String name, String title, int row) {
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
        grid.add(nameLabel, 0, row);
        grid.add(titleLabel, 1, row);
    }


    public Scene createGameScene(String mapName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdvanceWarsApplication.class.getResource("game scene.fxml"));
        fxmlLoader.setController(new AdvanceWarsController(mapName, this));
        Parent root = fxmlLoader.load();

        root.setStyle("-fx-background-color: #648571;");

        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/game.css")).toExternalForm());
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
        backButton.getStyleClass().add("menu-button");
        backButton.setOnAction(_ -> { primaryStage.setScene(mapSelectionScene);  primaryStage.setFullScreen(true); });
        BorderPane.setMargin(backButton, new Insets(10));
        borderPane.setBottom(backButton);

        Scene scene = new Scene(borderPane, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/menu.css")).toExternalForm());
        return scene;
    }

    public void switchToEndScreen(int winner) {
        endScreenScene = createEndScreenScene(primaryStage, winner);
        primaryStage.setScene(endScreenScene);
        primaryStage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch();
    }
}