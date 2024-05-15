package Janne;

import com.example.advancedwars.Troop;

public class Infantry extends Troop {

    public Infantry(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 3);
        identification =0;
    }



    public String getTroopImg() {
        return "/images/troops/infantry" + this.team + ".png";
    }

    public boolean canStandOnField(int[][] map, int x, int y) {
        if(x < 0 || x >= map[0].length || y < 0 || y >= map.length) return false;
        if(map[y][x] == 3) return false;

        return true;
    }
}
