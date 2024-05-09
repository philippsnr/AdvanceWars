package com.example.advancedwars;

import java.util.ArrayList;

public abstract class Troop {
    protected int health;
    protected int team;
    protected int xpos;
    protected int ypos;
    protected boolean moved = false;

    public Troop(int _team, int _xpos, int _ypos) {
        this.health = 10;
        this.team = _team;
        this.xpos = _xpos;
        this.ypos = _ypos;
    }

    public int getTeam() {
        return team;
    }

    public int getHealth() {
        return health;
    }

    public abstract String getTroopImg();

    public ArrayList<int[]> getRange(int xlength, int ylength) {
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
}