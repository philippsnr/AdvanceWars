package com.example.advancedwars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameModel {

    public Map map;
    public Troop[][] troops;
    private int turn = 1;
    public static char KIA[][]={{'d','d','e','d','e','x','x','e'},{'c','d','c','b','c','x','x','e'},{'b','b','d','b','c','x','x','e'},{'a','a','b','c','c','x','x','x'},{'a','a','d','c','d','c','c','a'},{'x','x','x','x','x','c','a','a'},{'a','a','a','a','a','x','x','x'},{'b','b','c','c','d','x','x','c'}};
    private final int Bellcurve[]={1,1,1,2,2,2,2,2,2,3,3,3,4,4,5};

    public static double getFaktor(int unit1, int unit2) {
        if (KIA[unit1][unit2]=='a'){
            return 0.4;
        }
        else if (KIA[unit1][unit2]=='b'){
            return 0.32;
        }
        else if (KIA[unit1][unit2]=='c'){
            return 0.22;
        }
        else if (KIA[unit1][unit2]=='d'){
            return 0.12;
        }
        else if (KIA[unit1][unit2]=='e'){
            return 0.035;
        }
        else return 0;
    }


    public GameModel(String selectedMap) {
        initMap(selectedMap);
        initTroops();
    }

    public void calculateDamage(Troop attakingTroop,Troop defendingTroop){
        double Faktor= getFaktor(attakingTroop.getIdentification(), defendingTroop.getIdentification());
        System.out.println(Faktor);
        Random rand = new Random();
        int beforFaktor=0;
        for(int i=1; i<= attakingTroop.getHealth(); i++){
            int randomNumber = Bellcurve[rand.nextInt(15)];
            beforFaktor+=randomNumber;

        }
        System.out.println(beforFaktor);
        double afterFaktor=beforFaktor*Faktor;
        System.out.println(afterFaktor);
        int damage= Math.toIntExact(Math.round(afterFaktor));
        System.out.println(attakingTroop+" hat "+damage+" schaden gemacht");
        defendingTroop.recieveDamage(damage);


        Faktor=getFaktor(defendingTroop.getIdentification(),attakingTroop.getIdentification());
        System.out.println(Faktor);

        beforFaktor=0;
        for(int i=1; i<= defendingTroop.getHealth(); i++){
            int randomNumber = Bellcurve[rand.nextInt(15)];
            beforFaktor+=randomNumber;

        }
        System.out.println(beforFaktor);
        afterFaktor=beforFaktor*Faktor;
        System.out.println(afterFaktor);
        damage= Math.toIntExact(Math.round(afterFaktor));
        System.out.println(defendingTroop+" hat "+damage+" schaden gemacht");
        attakingTroop.recieveDamage(damage);
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