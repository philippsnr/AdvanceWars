package com.example.advancedwars;

public class Artillery extends Troop{
    public Artillery(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 5);
        identification=3;
    }

    public String getTroopImg() {
        return "/images/troops/artillery" + this.team + ".png";
    }

    public boolean canStandOnField(int field) {
        if(field == 3 || field == 2) return false;

        return true;
    }
}
