package com.example.advancedwars;



import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.text.Text;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class HelloController implements Initializable {

    private final GameModel model;
    private boolean mooving = false;
    @FXML
    private GridPane mapGridPane;
    @FXML
    private Button waitButton;
    @FXML
    private Button attackButton;
    @FXML
    private Button testButton;
    @FXML
    private HBox turnElement;
    @FXML
    private Text turnText;

    public HelloController() {
        this.model = new GameModel("Little Island");
        System.out.println("Controller created");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMap();
    }

    private void loadMap() {

        for (int y = 0; y < this.model.map.mapArray.length; y++) {
            for (int x = 0; x < this.model.map.mapArray[y].length; x++) {
                final int finalX = x;
                final int finalY = y;
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
                    case 5:
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/factory1.png")));
                        imageView.setOnMouseClicked(mouseEvent -> factoryClicked(1, finalX, finalY));
                        break;
                    case 6:
                        imageView.setImage(new Image(getClass().getResourceAsStream("/images/factory2.png")));
                        imageView.setOnMouseClicked(mouseEvent -> factoryClicked(2, finalX, finalY));
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
    }

    private void placeTroopOnMap(Troop troop, int direction) {
        String troopImgPath = troop.getTroopImg();
        Image troopImg = new Image(getClass().getResourceAsStream(troopImgPath));
        ImageView troopImageView = new ImageView(troopImg);
        troopImageView.getStyleClass().add("troopImageView");
        if (troop.moved) {
            troopImageView.getStyleClass().add("moved");
        }

        troopImageView.setFitWidth(35);
        troopImageView.setFitHeight(35);
        troopImageView.setScaleX(direction);

        Label healthLabel = new Label(String.valueOf(troop.getHealth()));
        healthLabel.setTextFill(Color.WHITE);

        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("troopStackPane");
        stackPane.getChildren().addAll(troopImageView, healthLabel);

        StackPane.setMargin(healthLabel, new Insets(-50, 0, 0, 0));

        stackPane.setOnMouseClicked(event -> selectTroop(troop));

        mapGridPane.add(stackPane, troop.xpos, troop.ypos);


    }
    private void factoryClicked(int team, int x, int y) {
        System.out.println("Factory clicked");
        if (this.mooving == true) {
            return;
        }
        if (this.model.getTurn() != team) {
            return;
        }

        testButton.getStyleClass().remove("disabled");
        testButton.setOnMouseClicked(mouseEvent -> gekauft());


    }
    private void gekauft(){
        System.out.println("gekauft");
    }

    private void selectTroop(Troop troop) {





        if (this.mooving == true) {
            return;
        }

        if (this.model.getTurn() != troop.team) {
            return;
        }




        if (troop.moved == true) {
            return;
        }
        boolean attackPossible = false;
        ArrayList<int[]> attackRange = troop.getAttackRange(this.model.map.mapArray[0].length, this.model.map.mapArray.length);
        for (int[] field : attackRange) {
            if (this.model.troops[field[1]][field[0]] != null && this.model.troops[field[1]][field[0]].team != troop.team && (model.KIA[troop.getIdentification()][this.model.troops[field[1]][field[0]].identification] != 'x')) {
                attackPossible = true;
                break;
            }
        }

        if (attackPossible&&(troop.identification!=3||troop.moved==false)) {
            attackButton.getStyleClass().remove("disabled");
            attackButton.setOnMouseClicked(mouseEvent -> troopAttack(troop, attackRange));
        }
        System.out.println("Truppe ausgewählt: bei Koordinaten (" + troop.xpos + ", " + troop.ypos + ")");
        this.mooving = true;
        ArrayList<TargetField> movingRange = this.model.getTroopRange(troop);
        for (TargetField field : movingRange) {
            Image blue = new Image(getClass().getResourceAsStream("/images/possible.png"));
            ImageView blueImageView = new ImageView(blue);
            blueImageView.getStyleClass().add("blueImageView");
            blueImageView.setFitWidth(50);
            blueImageView.setFitHeight(50);
            blueImageView.setOnMouseClicked(event -> selectTargetField(troop, field));
            mapGridPane.add(blueImageView, field.x, field.y);
        }
    }

    private void selectTargetField(Troop troop, TargetField field) {

        this.mooving = true;
        attackButton.getStyleClass().add("disabled");
        attackButton.setOnMouseClicked(null);
        int x = field.x;
        int y = field.y;


        for (Node node : mapGridPane.getChildren()) {
            if (node instanceof StackPane && GridPane.getColumnIndex(node) == troop.xpos && GridPane.getRowIndex(node) == troop.ypos) {
                for (Node n : ((StackPane) node).getChildren()) {
                    if (n.getStyleClass().contains("troopImageView")) {
                        if (x > troop.xpos) {
                            n.setScaleX(-1);
                        } else if (x < troop.xpos) {
                            n.setScaleX(1);
                        }
                    }
                }
                troopMoveTransition(node, troop, field).thenRun(() -> {
                    mapGridPane.getChildren().remove(node);
                    if (this.model.troops[y][x] == null || this.model.troops[y][x] == troop) {
                        model.moveTroop(troop, x, y);
                        mapGridPane.add(node, x, y);
                        ListActions(troop);
                    } else {
                        this.model.mergeTroops(troop, this.model.troops[y][x]);
                        updateHealthLabel(this.model.troops[y][x]);
                        this.mooving = false;
                    }
                    disableTroop((StackPane) node);
                });

                break;
            }
        }
        clearHighlights();
    }

    private CompletableFuture<Void> troopMoveTransition(Node troopNode, Troop troop, TargetField field) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        Bounds startCellBounds = mapGridPane.getCellBounds(troop.xpos, troop.ypos);
        double startX = startCellBounds.getWidth() / 2;
        double startY = startCellBounds.getHeight() / 2;

        Path path = new Path();
        path.getElements().add(new MoveTo(startX, startY));

        if(field.path == null) {
            future.complete(null);
            return future;
        }
        int i = 0;
        for(TargetField f : field.path) {
            if(i == 0) { i++; continue; }
            double fx = 50 * (f.x - field.path.get(i - 1).x) + startCellBounds.getWidth() / 2;
            double fy = 50 * (f.y - field.path.get(i - 1).y) + startCellBounds.getHeight() / 2;

            path.getElements().add(new LineTo(fx, fy));
        }

        int pathLength = field.path.toArray().length - 1;
        System.out.println(field.path.toArray().length);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds((double) pathLength / 2));
        pathTransition.setPath(path);
        pathTransition.setNode(troopNode);
        pathTransition.setCycleCount(1);

        pathTransition.setOnFinished(event -> {
            future.complete(null);
            troopNode.setTranslateX(0);
            troopNode.setTranslateY(0);
            pathTransition.stop();
        });

        troopNode.toFront();
        pathTransition.play();

        return future;
    }

    private void disableTroop(StackPane troop) {
        for (Node n : troop.getChildren()) {
            if (n.getStyleClass().contains("troopImageView")) {
                n.getStyleClass().add("moved");
            }
            break;
        }
    }

    private void clearHighlights() {
        ObservableList<Node> children = mapGridPane.getChildren();
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : children) {
            if (node instanceof ImageView && node.getStyleClass().contains("blueImageView")) {
                nodesToRemove.add(node);
            }
        }

        children.removeAll(nodesToRemove);
    }

    private void ListActions(Troop troop) {

        waitButton.getStyleClass().remove("disabled");
        waitButton.setOnMouseClicked(mouseEvent -> troopWait(troop));

        boolean attackPossible = false;
        ArrayList<int[]> attackRange = troop.getAttackRange(this.model.map.mapArray[0].length, this.model.map.mapArray.length);
        for (int[] field : attackRange) {
            if (this.model.troops[field[1]][field[0]] != null && this.model.troops[field[1]][field[0]].team != troop.team && (model.KIA[troop.getIdentification()][this.model.troops[field[1]][field[0]].identification] != 'x')) {
                attackPossible = true;
                break;
            }
        }

        if (attackPossible&&(troop.identification!=3||troop.moved==false)) {
            while(attackButton.getStyleClass().contains("disabled")) {
                attackButton.getStyleClass().remove("disabled");
            }
            attackButton.setOnMouseClicked(mouseEvent -> troopAttack(troop, attackRange));
        }


    }

    private void troopWait(Troop troop) {
        troop.moved = true;
        waitButton.getStyleClass().add("disabled");
        waitButton.setOnMouseClicked(null);
        if (!attackButton.getStyleClass().contains("disabled")) {
            attackButton.getStyleClass().add("disabled");
        }
        attackButton.setOnMouseClicked(null);
        this.mooving = false;

    }

    private void troopAttack(Troop attakingTroop, ArrayList<int[]> attackRange) {
        this.mooving = false;
        clearHighlights();
        for (int[] field : attackRange) {
            if (this.model.troops[field[1]][field[0]] != null && this.model.troops[field[1]][field[0]].team != attakingTroop.team && (model.KIA[attakingTroop.getIdentification()][this.model.troops[field[1]][field[0]].identification] != 'x')) {
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
        waitButton.getStyleClass().add("disabled");
        waitButton.setOnMouseClicked(null);
        attackButton.getStyleClass().add("disabled");
        attackButton.setOnMouseClicked(null);
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

        if (troop.getHealth() <= 0) {
            for (Node stackPane : mapGridPane.getChildren()) {
                if (stackPane instanceof StackPane && GridPane.getColumnIndex(stackPane) == troop.xpos && GridPane.getRowIndex(stackPane) == troop.ypos) {
                    mapGridPane.getChildren().remove(stackPane);
                    this.model.troops[troop.ypos][troop.xpos] = null;
                    return;
                }
            }
        }

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

    @FXML
    private void endTurn() {
        if (this.mooving == true) {
            return;
        }

        for (Node node : mapGridPane.getChildren()) {
            node.getStyleClass().remove("moved");
        }

        this.model.switchTurn();

        for (Node node : mapGridPane.getChildren()) {
            if (node.getStyleClass().contains("troopStackPane")) {
                StackPane stackPane = (StackPane) node;
                for (Node troop : stackPane.getChildren()) {
                    troop.getStyleClass().remove("moved");
                }
            }
        }

        Image target = new Image(getClass().getResourceAsStream("/images/Bums.png"));
        ImageView turnSwitchImageView = new ImageView(target);
        turnSwitchImageView.getStyleClass().add("TargetImageView");
        turnSwitchImageView.setFitWidth(50);
        turnSwitchImageView.setFitHeight(50);

        String turnColor = (this.model.getTurn() == 1) ? "Rot" : "Blau";
        turnText.setText(turnColor + " ist am Zug");

        if(model.getTurn() == 1) {
            turnElement.getStyleClass().remove("turnBlue");
            turnElement.getStyleClass().add("turnRed");
        }
        else {
            turnElement.getStyleClass().remove("turnRed");
            turnElement.getStyleClass().add("turnBlue");
        }
    }


}