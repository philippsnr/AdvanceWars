package com.example.advancedwars;

public class Bomber extends Troop{
    public Bomber(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos);
    }

    public String getTroopImg() {
        return "/images/troops/bomber" + this.team + ".png";
    }

    public int[][] getRange() {
        int[] up = new int[] {this.xpos, this.ypos -1};
        int[] right = new int[] {this.xpos + 1, this.ypos};
        int[] down = new int[] {this.xpos, this.ypos +1};
        int[] left = new int[] {this.xpos -1 , this.ypos};

        return new int [][] {up, right, down, left};
    }
}
