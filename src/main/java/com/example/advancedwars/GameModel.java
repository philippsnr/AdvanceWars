package com.example.advancedwars;

import java.util.Arrays;

public class GameModel {

    public Map map;
    public Troop[][] troops;
    protected int turn = 1;

    public GameModel(String selectedMap) {
        initMap(selectedMap);
        initTroops();
    }

    protected void switchTurn() {
        for(int y = 0; y < troops.length; y++) {
            for(int x = 0; x < troops[y].length; x++) {
                if(this.troops[y][x] != null) {
                    this.troops[y][x].moved = false;
                }
            }
        }
        if (turn == 2) { turn = 1; }
        else { turn = 2; }
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
}