package com.example.advancedwars;

public abstract class Troop {
    protected int health;
    protected int team;

    public Troop(int _team) {
        this.health = 10;
        this.team = _team;
    }

    public int getTeam() {
        return team;
    }

    public int getHealth() {
        return health;
    }

    public abstract String getTroopImg();

}
