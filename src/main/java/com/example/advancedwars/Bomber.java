package com.example.advancedwars;

public class Bomber extends Troop{
    public Bomber(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 7);
    }

    public String getTroopImg() {
        return "/images/troops/bomber" + this.team + ".png";
    }
}
