package com.example.advancedwars;

public class Infantry extends Troop{

    public Infantry(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 3);
        identification =0;
    }



    public String getTroopImg() {
        return "/images/troops/infantry" + this.team + ".png";
    }

    public boolean canStandOnField(int field) {
        if(field == 3) return false;

        return true;
    }
}
