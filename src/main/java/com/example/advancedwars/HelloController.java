package com.example.advancedwars;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private final GameModel model;
    @FXML
    private GridPane mapGridPane;

    public HelloController() {
        this.model = new GameModel("Eon Springs");
        System.out.println("Controller created");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMap();
    }

    private void loadMap() {
        // Iteriere über das 2D-Array und erstelle Bildansichten basierend auf den Werten
        for (int y = 0; y < this.model.map.length; y++) {
            for (int x = 0; x < this.model.map[y].length; x++) {
                ImageView imageView = new ImageView();
                switch (this.model.map[y][x]) {
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
                    troopImageView.setFitWidth(35);
                    troopImageView.setFitHeight(35);
                    mapGridPane.add(troopImageView, x, y);
                }
            }
        }
    }
}
