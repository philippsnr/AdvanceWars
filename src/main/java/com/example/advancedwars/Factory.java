package com.example.advancedwars;

public class Factory {
    int xpos;
    int ypos;
    int team;
    public Factory (int xpos_, int ypos_, int team_) {
        this.xpos=xpos_;
        this.ypos=ypos_;
        this.team=team_;
    }


    public String getimage() {
        return "/images/factory"+this.team+".png";
    }
}
