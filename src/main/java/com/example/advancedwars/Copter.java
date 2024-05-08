package com.example.advancedwars;

public class Copter extends Troop{
    public Copter(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos);
    }

    public String getTroopImg() {
        return "/images/troops/copter" + this.team + ".png";
    }
}
