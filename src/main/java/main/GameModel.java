package main;

import maps.*;
import troops.TargetField;
import troops.Troop;

import java.util.ArrayList;
import java.util.Random;

public class GameModel {

    public Map map;
    public Troop[][] troops;
    private int turn = 1;
    private int round = 0;
    private int[] money = {3000, 3000};
    public static char[][] KIA = {{'d', 'd', 'e', 'd', 'e', 'x', 'x', 'e'}, {'c', 'c', 'c', 'b', 'c', 'x', 'x', 'e'}, {'b', 'b', 'd', 'b', 'c', 'x', 'x', 'e'}, {'a', 'a', 'b', 'c', 'c', 'x', 'x', 'x'}, {'a', 'a', 'd', 'c', 'd', 'c', 'c', 'a'}, {'x', 'x', 'x', 'x', 'x', 'c', 'a', 'a'}, {'a', 'a', 'a', 'a', 'a', 'x', 'x', 'x'}, {'b', 'b', 'c', 'c', 'd', 'x', 'x', 'c'}};
    private final int[] Bellcurve = {1, 1, 1,1,  2, 2, 2,  2, 3, 3,3, 3, 4,4, 5};
    private final int[] GroudnToStars = {1, 2, 4, 0, 0,0,0,0,0,0,0,0,0,0,0, 3, 3 };
    private final int[][] movementCost = {{1, 1, 2, 0, 1, 1,1,1, 1,1, 1,1,1, 1,1, 1,1}, {1, 1, 1, 0, 1, 1,1,1, 1,1, 1,1,1, 1,1, 1,1}, {1, 2, 0, 0, 1, 1,1,1, 1,1, 1,1,1, 1,1, 1,1}, {1, 2, 0, 0, 1, 1,1,1, 1,1, 1,1,1, 1,1, 1,1}, {1, 2, 0, 0, 1, 1,1,1, 1,1, 1,1,1, 1,1, 1,1}, {1, 1, 1, 1, 1, 1,1,1, 1,1, 1,1,1, 1,1, 1,1}, {1, 1, 1, 1, 1, 1,1,1, 1,1, 1,1,1, 1,1, 1,1}, {1, 1, 1, 1, 1, 1,1,1, 1,1, 1,1,1, 1,1, 1,1}};
    private final int[] moneyPerRound = {1000, 1000, 2000, 2000, 2000, 3000, 3000, 3000, 4000, 4000, 5000, 5000, 6000, 6000, 7000, 7000, 8000, 8000, 9000, 10000};

    public GameModel(String selectedMap) {
        initMap(selectedMap);
        initTroops();
    }

    public static double getKIAFaktor(int unit1, int unit2) {
        if (KIA[unit1][unit2] == 'a') {
            return 0.4;
        } else if (KIA[unit1][unit2] == 'b') {
            return 0.3;
        } else if (KIA[unit1][unit2] == 'c') {
            return 0.22;
        } else if (KIA[unit1][unit2] == 'd') {
            return 0.12;
        } else if (KIA[unit1][unit2] == 'e') {
            return 0.035;
        } else return 0;
    }

    private double getGroundFaktor(Troop attakingTroop, Troop defedingtroop) {
        int attakGround = this.map.mapArray[attakingTroop.ypos][attakingTroop.xpos];
        int defendGround = this.map.mapArray[defedingtroop.ypos][defedingtroop.xpos];
        int attakStars = GroudnToStars[attakGround];
        int defendStars = GroudnToStars[defendGround];
        int StarsDiff = attakStars - defendStars;
        if (StarsDiff == 0) {
            return 1;
        } else if (StarsDiff == 1) {
            return 1.2;
        } else if (StarsDiff == 2) {
            return 1.3;
        } else if (StarsDiff == 3) {
            return 1.4;
        } else if (StarsDiff == 4) {
            return 1.5;
        } else if (StarsDiff == -1) {
            return 1 / 1.2;
        } else if (StarsDiff == -2) {
            return 1 / 1.3;
        } else if (StarsDiff == -3) {
            return 1 / 1.4;
        } else if (StarsDiff == -4) {
            return 1 / 1.5;
        }

        return 0;
    }

    public void calculateDamage(Troop attakingTroop, Troop defendingTroop) {

        double kiaFaktor = getKIAFaktor(attakingTroop.getIdentification(), defendingTroop.getIdentification());
        double GroundFaktor = getGroundFaktor(attakingTroop, defendingTroop);
        System.out.println("Ground Faktor: " + GroundFaktor);
        System.out.println(kiaFaktor);
        Random rand = new Random();
        int beforFaktor = 0;
        for (int i = 1; i <= attakingTroop.getHealth(); i++) {
            int randomNumber = Bellcurve[rand.nextInt(15)];
            beforFaktor += randomNumber;

        }
        System.out.println("befor Faktor" + beforFaktor);
        double afterFaktor = beforFaktor * kiaFaktor * GroundFaktor;
        System.out.println("after faktor" + afterFaktor);
        int damage = Math.toIntExact(Math.round(afterFaktor));
        System.out.println(attakingTroop + " hat " + damage + " schaden gemacht");
        defendingTroop.recieveDamage(damage);


        kiaFaktor = getKIAFaktor(defendingTroop.getIdentification(), attakingTroop.getIdentification());
        System.out.println(kiaFaktor);

        beforFaktor = 0;
        if ((defendingTroop.identification!=3&& attakingTroop.identification!=3)&&kiaFaktor!=0) {
            for (int i = 1; i <= defendingTroop.getHealth(); i++) {
                int randomNumber = Bellcurve[rand.nextInt(15)];
                beforFaktor += randomNumber;

            }
            System.out.println("befor faktor" + beforFaktor);
            afterFaktor = beforFaktor * kiaFaktor * (1 / GroundFaktor);
            System.out.println("after faktor" + afterFaktor);
            damage = Math.toIntExact(Math.round(afterFaktor));
            System.out.println(defendingTroop + " hat " + damage + " schaden gemacht");
            attakingTroop.recieveDamage(damage);
        }
    }

    public int getMoney(int team) {
        return money[team - 1];
    }

    public int getTurn() {
        return turn;
    }

    protected void switchTurn() {
        for (int y = 0; y < troops.length; y++) {
            for (int x = 0; x < troops[y].length; x++) {
                if (this.troops[y][x] != null) {
                    this.troops[y][x].moved = false;
                }
            }
        }
        int income = 0;
        if(round <= 19) { income = moneyPerRound[round]; }
        else { income = 10000; }
        if (turn == 1) {
            money[0] += income;
            turn = 2;
        } else {
            money[1] += income;
            turn = 1;
        }
        if(turn == 1) { round++; }
    }

    protected int getWinner() {
        int[][] factoryCoordinates = this.map.getFactoryCoordinates();
        if (this.troops[factoryCoordinates[0][0]][factoryCoordinates[0][1]] != null ) {
            if (this.troops[factoryCoordinates[0][0]][factoryCoordinates[0][1]].getTeam()==2){
                int count =0;
                for (Troop[] t : troops) {
                    for (Troop x : t) {
                        if(x != null){if (x.getTeam()==1) {
                            count +=1;
                        }}
                    }
                }
                if (count ==0) {
                    return 2;
                }
            }

        }
        if (this.troops[factoryCoordinates[1][0]][factoryCoordinates[1][1]] != null ) {
            if (this.troops[factoryCoordinates[1][0]][factoryCoordinates[1][1]].getTeam()==1){
                int count =0;
                for (Troop[] t : troops) {
                    for (Troop x : t) {
                        if(x != null){if (x.getTeam()==2) {
                            count +=1;
                        }}
                    }
                }
                if (count ==0) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private void initMap(String selectedMap) {
        switch (selectedMap) {
            case "Little Island":
                map = new LittleIsland();
                break;
            case "Eon Springs":
                map = new EonSprings();
                break;
            case "Zero Wood":
                map = new ZeroWood();
                break;
            case "Sabre Range":
                map = new SabreRange();
                break;
            case "Cog Isle":
                map = new CogIsle();
                break;
            case "Piston Dam":
                map = new PistonDam();
                break;
        }
    }

    public boolean buyPossible(int price) {
        System.out.println(price);
        return money[turn - 1] >= price;
    }

    protected void buyTroop(Troop newTroop) {

        money[turn - 1] -= newTroop.price;

        newTroop.moved = true;
        placeTroop(newTroop);
    }

    private void initTroops() {
        this.troops = new Troop[map.mapArray.length][map.mapArray[0].length];
        Troop[] startTroops = this.map.getInitTroops();

        for (Troop t : startTroops) {
            placeTroop(t);
        }
    }

    protected void placeTroop(Troop t) {
        this.troops[t.ypos][t.xpos] = t;
    }

    protected void mergeTroops(Troop troop1, Troop troop2) {
        troop2.health += troop1.health;
        if (troop2.health >= 10) {
            troop2.health = 10;
        }
        this.troops[troop1.ypos][troop1.xpos] = null;
    }

    protected void moveTroop(Troop troop, int x, int y) {

        troops[troop.ypos][troop.xpos] = null;
        troops[y][x] = troop;
        troop.xpos = x;
        troop.ypos = y;
        troop.moved = true;

        System.out.println("Truppe bewegt nach: (" + x + ", " + y + ")");
    }


    public ArrayList<TargetField> getTroopRange(Troop troop) {
        ArrayList<TargetField> movingRange = new ArrayList<>();
        movingRange.add(new TargetField(troop.xpos, troop.ypos, null));
        ArrayList<TargetField> path = new ArrayList<>();
        path.add(new TargetField(troop.xpos, troop.ypos, null));
        movingField(troop, troop.xpos, troop.ypos, troop.stepRange, path, movingRange);
        return movingRange;
    }

    private void movingField(Troop troop, int x, int y, int steps, ArrayList<TargetField> path, ArrayList<TargetField> movingRange) {
        if (steps <= 0) return;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];

            if (nextX < 0 || nextX >= this.map.mapArray[0].length || nextY < 0 || nextY >= this.map.mapArray.length) {
                continue;
            }

            if(troop.identification < 5 && troops[nextY][nextX] != null && troops[nextY][nextX].identification < 5 && troops[nextY][nextX].team != troop.team) { continue; }
            if(troop.identification >= 5 && troops[nextY][nextX] != null && troops[nextY][nextX].team != troop.team && troops[nextY][nextX].identification >= 5) { continue; }


            /*if (troops[nextY][nextX] != null &&
                    troops[nextY][nextX] != troop &&
                    (troops[nextY][nextX].team != troop.team ||
                            troops[nextY][nextX].identification != troop.identification ||
                            (troops[nextY][nextX].getHealth() == 10 && troop.getHealth() == 10))) {
                continue;
            }*/

            int stepLoose = movementCost[troop.identification][map.mapArray[nextY][nextX]];

            if (stepLoose == 0 || stepLoose > steps) {
                continue;
            }

            boolean alreadyExists = false;
            for (TargetField position : movingRange) {
                if (position.x == nextX && position.y == nextY) {
                    alreadyExists = true;
                    break;
                }
            }

            ArrayList<TargetField> newPath = deepClone(path);

            TargetField newField = new TargetField(nextX, nextY, null);
            newPath.add(newField);

            if(!alreadyExists && (troops[nextY][nextX] == null || troops[nextY][nextX] == troop ||(troops[nextY][nextX].team == troop.team && troops[nextY][nextX].identification == troop.identification && troop.health < 10 && troops[nextY][nextX].health < 10))) {
                movingRange.add(new TargetField(nextX, nextY, newPath));
            }

            ArrayList<TargetField> newPathCopy = deepClone(newPath);
            movingField(troop, nextX, nextY, steps - stepLoose, newPathCopy, movingRange);
        }
    }

    public static ArrayList<TargetField> deepClone(ArrayList<TargetField> list) {
        ArrayList<TargetField> newList = new ArrayList<>();
        for (TargetField element : list) {
            newList.add(new TargetField(element));
        }
        return newList;
    }
}