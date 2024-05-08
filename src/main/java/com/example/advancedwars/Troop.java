package com.example.advancedwars;

public abstract class Troop {
    protected int health;
    protected int team;
    protected int xpos;
    protected int ypos;

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

}
