package com.example.advancedwars;

import Janne.faktor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private final GameModel model;
    private boolean mooving = false;
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
                    int troopDirection = 1;
                    if (x < this.model.map.mapArray[0].length / 2) {
                        troopDirection = -1;
                    }
                    placeTroopOnMap(this.model.troops[y][x], troopDirection);
                }
            }
        }
        Button endButton = new Button("End Turn");
        endButton.setPrefHeight(50);
        endButton.setPrefWidth(100);
        endButton.setOnMouseClicked(mouseEvent -> endTurn());
        mapGridPane.setColumnSpan(endButton, 2);
        mapGridPane.add(endButton, 0, mapGridPane.getRowCount());
        System.out.println("heeeeee");


    }

    private void placeTroopOnMap(Troop troop, int direction) {
        String troopImgPath = troop.getTroopImg();
        Image troopImg = new Image(getClass().getResourceAsStream(troopImgPath));
        ImageView troopImageView = new ImageView(troopImg);
        troopImageView.getStyleClass().add("troopImageView");
        troopImageView.setScaleX(-1);
        troopImageView.setFitWidth(35);
        troopImageView.setFitHeight(35);
        troopImageView.setScaleX(direction);

        Label healthLabel = new Label(String.valueOf(troop.getHealth()));
        healthLabel.setTextFill(Color.WHITE);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(troopImageView, healthLabel);

        StackPane.setMargin(healthLabel, new Insets(-50, 0, 0, 0));

        stackPane.setOnMouseClicked(event -> selectTroop(troop));

        mapGridPane.add(stackPane, troop.xpos, troop.ypos);


    }

    private void selectTroop(Troop troop) {
        System.out.println("hyyyyyyyyyyy");
        if (this.mooving == true) {
            return;
        }

        if (this.model.getTurn() != troop.team) {
            return;
        }

        clearHighlights();
        if (this.model.getTurn() != troop.getTeam()) {
            return;
        }

        if (troop.moved == true) {
            return;
        }
        System.out.println("Truppe ausgewählt: bei Koordinaten (" + troop.xpos + ", " + troop.ypos + ")");

        List<int[]> movingRange = this.model.getTroopRange(troop);
        for (int[] field : movingRange) {
            int x = field[0];
            int y = field[1];

            Image blue = new Image(getClass().getResourceAsStream("/images/possible.png"));
            ImageView blueImageView = new ImageView(blue);
            blueImageView.getStyleClass().add("blueImageView");
            blueImageView.setFitWidth(50);
            blueImageView.setFitHeight(50);
            blueImageView.setOnMouseClicked(event -> selectTargetField(troop, x, y));
            mapGridPane.add(blueImageView, x, y);

        }

        /*for (int i = -2; i <= 2; i++) {
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
        }*/
    }

    private void selectTargetField(Troop troop, int x, int y) {

        this.mooving = true;

        for (Node node : mapGridPane.getChildren()) {
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == troop.xpos && GridPane.getRowIndex(node) == troop.ypos && node.getStyleClass().contains("troopImageView")) {
                mapGridPane.getChildren().remove(node);
                break;
            }
        }

        for (Node node : mapGridPane.getChildren()) {
            if (node instanceof StackPane && GridPane.getColumnIndex(node) == troop.xpos && GridPane.getRowIndex(node) == troop.ypos) {
                mapGridPane.getChildren().remove(node);
                break;
            }
        }

        int troopDirection = 1;
        if (troop.xpos < x) {
            troopDirection = -1;
        }

        model.moveTroop(troop, x, y);
        placeTroopOnMap(troop, troopDirection);
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
        ArrayList<int[]> attackRange = troop.getAttackRange(this.model.map.mapArray[0].length, this.model.map.mapArray.length);
        for (int[] field : attackRange) {
            if (this.model.troops[field[1]][field[0]] != null && this.model.troops[field[1]][field[0]].team != troop.team) {
                attackPossible = true;
                break;
            }
        }

        Button attakButton = new Button("Attack");
        if (attackPossible) {
            attakButton.setPrefHeight(50);
            attakButton.setPrefWidth(100);
            allButtons.add(attakButton);
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
        attakButton.setOnMouseClicked(mouseEvent -> troopAttack(troop, allButtons, attackRange));
    }

    private void troopWait(Troop troop, ArrayList<Button> allButtons) {
        troop.moved = true;
        for (Button button : allButtons) {
            mapGridPane.getChildren().remove(button);
        }
        this.mooving = false;

    }

    private void troopAttack(Troop attakingTroop, ArrayList<Button> allButtons, ArrayList<int[]> attackRange) {
        this.mooving = false;
        for (int[] field : attackRange) {
            if (this.model.troops[field[1]][field[0]] != null && this.model.troops[field[1]][field[0]].team != attakingTroop.team) {
                Image target = new Image(getClass().getResourceAsStream("/images/target.png"));
                ImageView targetImageView = new ImageView(target);
                targetImageView.getStyleClass().add("TargetImageView");
                targetImageView.setFitWidth(50);
                targetImageView.setFitHeight(50);
                targetImageView.setPickOnBounds(true);


                targetImageView.setOnMouseClicked(event -> troopFight(attakingTroop, this.model.troops[field[1]][field[0]]));
                mapGridPane.add(targetImageView, field[0], field[1]);

            }
        }

        for (Button button : allButtons) {
            mapGridPane.getChildren().remove(button);
        }
        this.mooving = false;
    }


    private void troopFight(Troop attakingTroop, Troop defendingTroop) {

        clearTargets();
        model.calculateDamage(attakingTroop, defendingTroop);

        updateHealthLabel(attakingTroop);
        updateHealthLabel(defendingTroop);
    }

    private void clearTargets() {
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : mapGridPane.getChildren()) {
            if (node instanceof ImageView && ((ImageView) node).getStyleClass().contains("TargetImageView")) {
                nodesToRemove.add(node);
            }
        }

        mapGridPane.getChildren().removeAll(nodesToRemove);
    }


    private void updateHealthLabel(Troop troop) {

        for (Node stackPane : mapGridPane.getChildren()) {
            if (stackPane instanceof StackPane && GridPane.getColumnIndex(stackPane) == troop.xpos && GridPane.getRowIndex(stackPane) == troop.ypos) {
                for (Node label : ((StackPane) stackPane).getChildren()) {
                    if (label instanceof Label) {
                        ((StackPane) stackPane).getChildren().remove(label);
                        Label healthLabel = new Label(String.valueOf(troop.getHealth()));
                        healthLabel.setTextFill(Color.WHITE);
                        ((StackPane) stackPane).getChildren().add(healthLabel);
                        StackPane.setMargin(healthLabel, new Insets(-50, 0, 0, 0));
                        break;
                    }
                }
                break;
            }
        }
    }

    private void endTurn() {
        if (this.mooving == true) {
            return;
        }
        this.model.switchTurn();
    }
}