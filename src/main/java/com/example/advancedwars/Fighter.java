package com.example.advancedwars;

public class Fighter extends Troop{
    public Fighter(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 9, 5);
    }

    public String getTroopImg() {
        return "/images/troops/fighter" + this.team + ".png";
    }

}
