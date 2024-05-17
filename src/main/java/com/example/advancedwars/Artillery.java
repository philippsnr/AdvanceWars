package com.example.advancedwars;

import java.util.ArrayList;

public class Artillery extends Troop{
    public Artillery(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 5, 3);
    }
    public ArrayList<int[]> getAttackRange(int xlength, int ylength){
        ArrayList<int[]> range = new ArrayList<int[]>();
        int Y=4;
        int x=-1;
        for(int i = 3; i >0 ; i++){
            Y-=1;

            range.add(new int[]{this.xpos, this.ypos - Y});
            for(int j = 0; j < 3; j++){
                x+=1;
                range.add(new int[]{this.xpos+x, this.ypos });
            }
        }




        return range;





    }

    public String getTroopImg() {
        return "/images/troops/artillery" + this.team + ".png";
    }
}
