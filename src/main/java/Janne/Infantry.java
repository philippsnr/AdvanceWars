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
}
