package com.example.advancedwars;

public class Infantry extends Troop{
    public Infantry(int _team) {
        super(_team);
    }

    public String getTroopImg() {
        if(this.team == 1) {
            return "/images/infantry1.png";
        }
        else {
            return "/images/infantry2.png";
        }
    }
}
