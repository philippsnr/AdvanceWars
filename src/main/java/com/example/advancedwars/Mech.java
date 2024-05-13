package com.example.advancedwars;

public class Mech extends Troop{
    public Mech(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 2);
    }

    public String getTroopImg() {
        return "/images/troops/mech" + this.team + ".png";
    }
}
