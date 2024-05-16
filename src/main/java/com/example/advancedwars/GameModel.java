package com.example.advancedwars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameModel {

    public Map map;
    public Troop[][] troops;
    private int turn = 1;
    public static char[][] KIA={{'d','d','e','d','e','x','x','e'},{'c','c','c','b','c','x','x','e'},{'b','b','d','b','c','x','x','e'},{'a','a','b','c','c','x','x','x'},{'a','a','d','c','d','c','c','a'},{'x','x','x','x','x','c','a','a'},{'a','a','a','a','a','x','x','x'},{'b','b','c','c','d','x','x','c'}};
    private final int[] Bellcurve={1,1,1,2,2,2,2,2,2,3,3,3,4,4,5};
    private final int[] GroudnToStars={1,2,4,0};
    private final int[][] movementCost = {{1,1,2,0}, {1,1,1,0}, {1,2,0,0}, {1,2,0,0}, {1,2,0,0}, {1,1,1,1}, {1,1,1,1}, {1,1,1,1}};

    public static double getKIAFaktor(int unit1, int unit2) {
        if (KIA[unit1][unit2]=='a'){
            return 0.4;
        }
        else if (KIA[unit1][unit2]=='b'){
            return 0.3;
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
    private double getGroundFaktor(Troop attakingTroop, Troop defedingtroop) {
        int attakGround=this.map.mapArray[attakingTroop.ypos][attakingTroop.xpos];
        int defendGround=this.map.mapArray[defedingtroop.ypos][defedingtroop.xpos];
        int attakStars=GroudnToStars[attakGround];
        int defendStars=GroudnToStars[defendGround];
        int StarsDiff=attakStars-defendStars;
        if (StarsDiff==0){
            return 1;
        }
        else if(StarsDiff==1){
            return 1.2;
        }
        else if (StarsDiff==2){
            return 1.3;
        }
        else if (StarsDiff==3){
            return 1.4;
        }
        else if (StarsDiff==4){
            return 1.5;
        }
        else if (StarsDiff==-1){
            return 1/1.2;
        }
        else if (StarsDiff==-2){
            return 1/1.3;
        }
        else if (StarsDiff==-3){
            return 1/1.4;
        }
        else if (StarsDiff==-4){
            return 1/1.5;
        }

        return 0;
    }




    public GameModel(String selectedMap) {
        initMap(selectedMap);
        initTroops();
    }

    public void calculateDamage(Troop attakingTroop,Troop defendingTroop){

        double kiaFaktor= getKIAFaktor(attakingTroop.getIdentification(), defendingTroop.getIdentification());
        double GroundFaktor = getGroundFaktor(attakingTroop,defendingTroop);
        System.out.println("Ground Faktor: "+GroundFaktor);
        System.out.println(kiaFaktor);
        Random rand = new Random();
        int beforFaktor=0;
        for(int i=1; i<= attakingTroop.getHealth(); i++){
            int randomNumber = Bellcurve[rand.nextInt(15)];
            beforFaktor+=randomNumber;

        }
        System.out.println("befor Faktor"+beforFaktor);
        double afterFaktor=beforFaktor*kiaFaktor*GroundFaktor;
        System.out.println("after faktor"+afterFaktor);
        int damage= Math.toIntExact(Math.round(afterFaktor));
        System.out.println(attakingTroop+" hat "+damage+" schaden gemacht");
        defendingTroop.recieveDamage(damage);


        kiaFaktor=getKIAFaktor(defendingTroop.getIdentification(),attakingTroop.getIdentification());
        System.out.println(kiaFaktor);

        beforFaktor=0;
        for(int i=1; i<= defendingTroop.getHealth(); i++){
            int randomNumber = Bellcurve[rand.nextInt(15)];
            beforFaktor+=randomNumber;

        }
        System.out.println("befor faktor"+beforFaktor);
        afterFaktor=beforFaktor*kiaFaktor*(1/GroundFaktor);
        System.out.println("after faktor"+afterFaktor);
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
        List<int[]> movingRange = new ArrayList<>();
        movingField(troop, troop.xpos, troop.ypos, troop.stepRange, movingRange);
        return movingRange;
    }

    private void movingField(Troop troop, int x, int y, int steps, List<int[]> movingRange) {
        if (steps <= 0) return;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];

            if(nextX < 0 || nextX >= this.map.mapArray[0].length || nextY < 0 || nextY >= this.map.mapArray.length) { continue; }
            if(this.troops[nextY][nextX] != null && this.troops[nextY][nextX] != troop) { continue; }

            int stepLoose = movementCost[troop.identification][map.mapArray[nextY][nextX]];

            if(stepLoose == 0 || stepLoose > steps) { continue; }

            boolean alreadyExists = false;
            for (int[] position : movingRange) {
                if (position[0] == nextX && position[1] == nextY) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                movingRange.add(new int[]{nextX, nextY});
            }

            movingField(troop, nextX, nextY, steps - stepLoose, movingRange);
        }
    }
}