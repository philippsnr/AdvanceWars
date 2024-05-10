package com.example.advancedwars;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private final GameModel model;
    private boolean mooving = false;
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
                }

                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                mapGridPane.add(imageView, x, y);

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
                    troopImageView.setOnMouseClicked(event -> selectTroop(this.model.troops[finalY][finalX]));
                    mapGridPane.add(troopImageView, x, y);
                }
            }
        }
    }

    private void selectTroop(Troop troop) {
        if (this.mooving == true) {
            return;
        }
        this.mooving = true;
        if (troop.moved == true) {
            return;
        }
        System.out.println("Truppe ausgewählt: bei Koordinaten (" + troop.xpos + ", " + troop.ypos + ")");

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                int x = troop.xpos + i;
                int y = troop.ypos + j;

                if (isValidField(x, y, troop)) {
                    if (this.model.troops[y][x] == null) {
                        Image blue = new Image(getClass().getResourceAsStream("/images/possible.png"));
                        ImageView blueImageView = new ImageView(blue);
                        blueImageView.getStyleClass().add("blueImageView");
                        blueImageView.setFitWidth(50);
                        blueImageView.setFitHeight(50);
                        blueImageView.setOnMouseClicked(event -> selectTargetField(troop, x, y));
                        mapGridPane.add(blueImageView, x, y);
                    } else if (this.model.troops[troop.ypos][troop.xpos].team != this.model.troops[y][x].team) {
                        Image red = new Image(getClass().getResourceAsStream("/images/rot.png"));
                        ImageView redImageView = new ImageView(red);
                        redImageView.getStyleClass().add("redImageView");
                        redImageView.setFitWidth(50);
                        redImageView.setFitHeight(50);

                        mapGridPane.add(redImageView, x, y);
                    }

                }
            }
        }
    }

    private boolean isValidField(int x, int y, Troop selectetTroop) {
        return x >= 0 && x < this.model.map.mapArray[0].length && y >= 0 && y < this.model.map.mapArray.length && (this.model.troops[y][x] == null || this.model.troops[y][x] == selectetTroop || this.model.troops[selectetTroop.ypos][selectetTroop.xpos].team != this.model.troops[y][x].team);
    }

    private void selectTargetField(Troop troop, int x, int y) {

        for (Node node : mapGridPane.getChildren()) {
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == troop.xpos && GridPane.getRowIndex(node) == troop.ypos && node.getStyleClass().contains("troopImageView")) {
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
        troopImageView.setOnMouseClicked(event -> selectTroop(troop));
        mapGridPane.add(troopImageView, x, y);
        ListActions(troop);
        clearHighlights();
    }

    private void clearHighlights() {
        ObservableList<Node> children = mapGridPane.getChildren();
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : children) {
            if (node instanceof ImageView && node.getStyleClass().contains("blueImageView") || node instanceof ImageView && node.getStyleClass().contains("redImageView")) {
                nodesToRemove.add(node);
            }
        }

        children.removeAll(nodesToRemove);
    }

    private void ListActions(Troop troop) {

        ArrayList<Button> allButtons = new ArrayList<Button>();

        Button waitButton = new Button("Warten");
        waitButton.setPrefHeight(50);
        waitButton.setPrefWidth(100);
        allButtons.add(waitButton);

        boolean attackPossible = false;
        ArrayList<int[]> attackRange = troop.getRange(this.model.map.mapArray[0].length, this.model.map.mapArray.length);
        for (int[] field : attackRange) {
            if (this.model.troops[field[1]][field[0]] != null && this.model.troops[field[1]][field[0]].team != troop.team) {
                attackPossible = true;
                break;
            }
        }

        if (attackPossible) {
            Button attakButton = new Button("Attack");
            attakButton.setPrefHeight(50);
            attakButton.setPrefWidth(100);
            allButtons.add(attakButton);
            attakButton.setOnMouseClicked(mouseEvent -> troopAttack(troop, allButtons,attackRange));
        }

        if (troop.xpos > mapGridPane.getColumnCount() / 2) {
            for (int i = 0; i < allButtons.size(); i++) {
                mapGridPane.add(allButtons.get(i), 0, i);
                GridPane.setColumnSpan(allButtons.get(i), 2);
            }
        } else {
            for (int i = 0; i < allButtons.size(); i++) {
                mapGridPane.add(allButtons.get(i), mapGridPane.getColumnCount() - 2, i);
                GridPane.setColumnSpan(allButtons.get(i), 2);
            }
        }

        waitButton.setOnMouseClicked(mouseEvent -> troopWait(troop, allButtons));
    }

    private void troopWait(Troop troop, ArrayList<Button> allButtons) {
        troop.moved = true;
        for (Button button : allButtons) {
            mapGridPane.getChildren().remove(button);
        }
        this.mooving = false;

    }
    private void troopAttack(Troop attakingTroop,ArrayList<Button> allButtons,ArrayList<int[]> attackRange){
        for (int[] field : attackRange) {
            if (this.model.troops[field[1]][field[0]] != null && this.model.troops[field[1]][field[0]].team != attakingTroop.team) {
                Image Target = new Image(getClass().getResourceAsStream("/images/Target.png"));
                ImageView TargetImageView = new ImageView(Target);
                TargetImageView.getStyleClass().add("TargetImageView");
                TargetImageView.setFitWidth(50);
                TargetImageView.setFitHeight(50);

                mapGridPane.add(TargetImageView, field[1], field[0]);

            }
        }






        for (Button button : allButtons) {
            mapGridPane.getChildren().remove(button);
        }
    }
}