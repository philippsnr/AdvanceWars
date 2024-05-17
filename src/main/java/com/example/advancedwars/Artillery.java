package com.example.advancedwars;

import java.util.ArrayList;

public class Artillery extends Troop{
    public Artillery(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 5, 3);
    }
    public ArrayList<int[]> getAttackRange(int xlength, int ylength){
        ArrayList<int[]> range = new ArrayList<int[]>();



        return range;

    }

    public String getTroopImg() {
        return "/images/troops/artillery" + this.team + ".png";
    }
}
