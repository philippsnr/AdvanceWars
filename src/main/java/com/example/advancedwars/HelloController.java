package com.example.advancedwars;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private final GameModel model;
    @FXML
    private GridPane mapGridPane;

    public HelloController() {
        this.model = new GameModel("Piston Dam");
        System.out.println("Controller created");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMap();
    }

    private void loadMap() {
        // Iteriere über das 2D-Array und erstelle Bildansichten basierend auf den Werten
        for (int y = 0; y < this.model.map.mapArray.length; y++) {
            for (int x = 0; x < this.model.map.mapArray[y].length; x++) {
                ImageView imageView = new ImageView();
                switch (this.model.map.mapArray[y][x]) {
                    case 0:
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/gras.png")));
                        break;
                    case 1:
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/wood.png")));
                        break;
                    case 2:
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/mountain.png")));
                        break;
                    case 3:
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/sea.png")));
                        break;
                    // Füge weitere Fälle hinzu, wenn du mehr Bilder für andere Werte benötigst
                }

                imageView.setFitWidth(50);
                imageView.setFitHeight(50);


                mapGridPane.add(imageView, x, y);

                // Überprüfe, ob eine Truppe auf diesem Feld steht
                if (this.model.troops[y][x] != null) {
                    String troopImgPath = this.model.troops[y][x].getTroopImg();
                    Image troopImg = new Image(getClass().getResourceAsStream(troopImgPath));
                    ImageView troopImageView = new ImageView(troopImg);
                    troopImageView.getStyleClass().add("troopImageView");
                    troopImageView.setScaleX(-1);
                    troopImageView.setFitWidth(35);
                    troopImageView.setFitHeight(35);
                    int finalY = y;
                    int finalX = x;
                    troopImageView.setOnMouseClicked(event -> selectTroop(this.model.troops[finalY][finalX])); // EventHandler für Truppenbild hinzufügen
                    mapGridPane.add(troopImageView, x, y);
                }
            }
        }
    }

    // Methode, die aufgerufen wird, wenn eine Truppe ausgewählt wird
    private void selectTroop(Troop troop) {
        System.out.println("Truppe ausgewählt: bei Koordinaten (" + troop.xpos + ", " + troop.ypos + ")");

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                int x = troop.xpos + i;
                int y = troop.ypos + j;
                // Überprüfe, ob das Feld innerhalb der Spielfeldgrenzen liegt
                if (isValidField(x, y)) {
                    Image blue = new Image(getClass().getResourceAsStream("/images/possible.png"));
                    ImageView blueImageView = new ImageView(blue);
                    blueImageView.getStyleClass().add("blueImageView");
                    blueImageView.setFitWidth(50);
                    blueImageView.setFitHeight(50);
                    // Füge einen EventHandler zum Bewegen der Truppe hinzu, wenn das Feld geklickt wird
                    blueImageView.setOnMouseClicked(event -> selectTargetField(troop, x, y));
                    mapGridPane.add(blueImageView, x, y);
                }
            }
        }
    }

    // Methode, um zu überprüfen, ob ein Feld innerhalb der Spielfeldgrenzen liegt
    private boolean isValidField(int x, int y) {
        return x >= 0 && x < this.model.map.mapArray[0].length && y >= 0 && y < this.model.map.mapArray.length&&this.model.troops[y][x]==null;
    }

    private void selectTargetField(Troop troop, int x, int y) {

        for (Node node : mapGridPane.getChildren()) {
            // Überprüfe, ob das aktuelle Kind ein ImageView ist und sich an der gewünschten Position befindet
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == troop.xpos && GridPane.getRowIndex(node) == troop.ypos && node.getStyleClass().contains("troopImageView")) {
                // Entferne das ImageView der Truppe aus dem GridPane
                mapGridPane.getChildren().remove(node);
                break;
            }
        }
        model.moveTroop(troop, x, y);
        String troopImgPath = troop.getTroopImg();
        Image troopImg = new Image(getClass().getResourceAsStream(troopImgPath));
        ImageView troopImageView = new ImageView(troopImg);
        troopImageView.getStyleClass().add("troopImageView");
        troopImageView.setFitWidth(35);
        troopImageView.setFitHeight(35);
        troopImageView.setOnMouseClicked(event -> selectTroop(troop)); // EventHandler für Truppenbild hinzufügen
        mapGridPane.add(troopImageView, x, y);

        clearBlueHighlights();
    }

    private void clearBlueHighlights() {
        ObservableList<Node> children = mapGridPane.getChildren();
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : children) {
            if (node instanceof ImageView && node.getStyleClass().contains("blueImageView")) {
                nodesToRemove.add(node);
            }
        }

        children.removeAll(nodesToRemove);
    }

}
