package com.example.advancedwars;

public class AntiAir extends Troop{
    public AntiAir(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 6, 4);
    }

    public String getTroopImg() {
        return "/images/troops/antiair" + this.team + ".png";
    }

    public boolean canStandOnField(int field) {
        if(field == 3 || field == 2) return false;

        return true;
    }
}
