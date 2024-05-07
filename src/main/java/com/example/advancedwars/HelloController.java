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
        // Iteriere über das 2D-Array und erstelle Rechtecke mit entsprechender Farbe basierend auf den Werten
        for (int y = 0; y < this.model.map.length; y++) {
            for (int x = 0; x < this.model.map[y].length; x++) {
                Rectangle rectangle = new Rectangle(50, 50);
                switch (this.model.map[y][x]) {
                    case 0:
                        rectangle.setFill(Color.GREEN);
                        break;
                    case 1:
                        rectangle.setFill(Color.SADDLEBROWN);
                        break;
                    case 2:
                        rectangle.setFill(Color.GREY);
                        break;
                    case 3:
                        rectangle.setFill(Color.BLUE);
                }
                mapGridPane.add(rectangle, x, y);
                if (this.model.troops[y][x] != null) {
                    String troopImgPath = this.model.troops[y][x].getTroopImg();
                    Image troopImg = new Image(getClass().getResourceAsStream(troopImgPath));
                    ImageView imageView = new ImageView(troopImg);
                    imageView.setFitWidth(35);
                    imageView.setFitHeight(35);
                    mapGridPane.add(imageView, x, y);
                }
            }
        }
    }
}
