package com.example.advancedwars;

public class Artillery extends Troop{
    public Artillery(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos);
    }

    public String getTroopImg() {
        return "/images/troops/artillery" + this.team + ".png";
    }
}
