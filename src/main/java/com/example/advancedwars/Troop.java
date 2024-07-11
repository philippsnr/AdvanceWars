package com.example.advancedwars;

import java.util.ArrayList;
import java.util.List;

public abstract class Troop {
    protected int health;
    protected int team;
    protected int xpos;
    protected int ypos;
    protected int price;
    protected boolean moved = false;
    protected int stepRange;
    public int identification;


    public Troop(int _team, int _xpos, int _ypos, int _stepRange, int _identification, int _price) {
        this.health = 10;
        this.team = _team;
        this.xpos = _xpos;
        this.ypos = _ypos;
        this.price = _price;
        this.stepRange = _stepRange;
        this.identification = _identification;
    }

    public int getTeam() {
        return team;
    }

    public int getHealth() {
        return health;
    }

    public int getIdentification() {
        return identification;
    }
    public void recieveDamage(int damage){
        this.health-=damage;
    }

    public abstract String getTroopImg();

    public ArrayList<int[]> getAttackRange(int xlength, int ylength) {
        ArrayList<int[]> range = new ArrayList<int[]>();

        if (this.ypos > 0) {
            range.add(new int[]{this.xpos, this.ypos - 1});
        }
        if (this.xpos < xlength - 1) {
            range.add(new int[]{this.xpos + 1, this.ypos});
        }
        if (this.ypos < ylength - 1) {
            range.add(new int[]{this.xpos, this.ypos + 1});
        }
        if (this.xpos > 0) {
            range.add(new int[]{this.xpos - 1, this.ypos});
        }





        return range;
    }

    /*public List<int[]> getMovingRange(int[][] map, Troop[][] troops) {
        List<int[]> movingRange = new ArrayList<>();
        move(xpos, ypos, stepRange, movingRange, map, troops);
        return movingRange;
    }

    private void move(int x, int y, int steps, List<int[]> movingRange, int[][] map, Troop[][] troops) {
        if (steps <= 0) return;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];

            if(!this.canStandOnField(map, troops, nextX, nextY)) { continue; }

            boolean alreadyExists = false;
            for (int[] position : movingRange) {
                if (position[0] == nextX && position[1] == nextY) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                movingRange.add(new int[]{nextX, nextY});
                move(nextX, nextY, steps - 1, movingRange, map, troops);
            }

            move(nextX, nextY, steps - 1, movingRange, map, troops);
        }
    }*/
}