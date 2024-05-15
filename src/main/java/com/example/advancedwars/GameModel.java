package com.example.advancedwars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModel {

    public Map map;
    public Troop[][] troops;
    private int turn = 1;
    public double KIA[][]={{0.5,0.5},{0.6,0.5}};

    public GameModel(String selectedMap) {
        initMap(selectedMap);
        initTroops();
    }

    public double getKIA(int attakingTroop,int defendingTroop) {
        return KIA[attakingTroop][defendingTroop];
    }

    public int getTurn() {
        return turn;
    }

    protected void switchTurn() {
        for(int y = 0; y < troops.length; y++) {
            for(int x = 0; x < troops[y].length; x++) {
                if(this.troops[y][x] != null) {
                    this.troops[y][x].moved = false;
                }
            }
        }
        if (turn == 1) { turn = 2; }
        else { turn = 1; }
    }

    private void initMap(String selectedMap) {
        //Plain: 0, Wood: 1, Mountain: 2, Sea: 3
        switch (selectedMap) {
            case "Little Island":
                map = new LittleIsland();
                break;
            case "Eon Springs":
                map = new EonSprings();
                break;
            case "Piston Dam":
                map = new PistonDam();
                break;
        }
    }

    private void initTroops() {
        this.troops = new Troop[map.mapArray.length][map.mapArray[0].length];
        Troop[] startTroops = this.map.getInitTroops();

        for (Troop t : startTroops) {
            this.troops[t.ypos][t.xpos] = t;
        }
    }

    protected void moveTroop(Troop troop, int x, int y) {

        troops[troop.ypos][troop.xpos] = null;
        troops[y][x] = troop;
        troop.xpos = x;
        troop.ypos = y;

        System.out.println("Truppe bewegt nach: (" + x + ", " + y + ")");
    }

    public List<int[]> getTroopRange(Troop troop) {
        List<int[]> range = new ArrayList<int[]>();

        List<int[]> movingRange = troop.getMovingRange();

        for (int[] field : movingRange) {
            int x = field[0];
            int y = field[1];

            if(x < 0 || x >= map.mapArray[0].length || y < 0 || y >= map.mapArray.length) { continue; }
            if(troops[y][x] != null && troops[y][x] != troop) { continue; }
            if (!(troop instanceof Copter || troop instanceof Fighter || troop instanceof Bomber) && this.map.mapArray[y][x] == 3) { continue; }

            range.add(field);
        }

        return range;
    }
}