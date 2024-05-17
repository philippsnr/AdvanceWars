package com.example.advancedwars;

import java.util.ArrayList;

public class Artillery extends Troop{
    public Artillery(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 5, 3);
    }
    public ArrayList<int[]> getAttackRange(int xlength, int ylength){
        ArrayList<int[]> range = new ArrayList<int[]>();
        int Y=-3;
        int x=0;
        //unten rechts
        for(int i = 3; i >0 ; i--){
            range.add(new int[]{this.xpos+x, this.ypos - Y});
            Y+=1;
            range.add(new int[]{this.xpos+x, this.ypos-Y });
            x+=1;
        }
        //oben rechts
         Y=3;
         x=0;
        for(int i = 0; i <3 ; i++){
            range.add(new int[]{this.xpos+x, this.ypos - Y});
            Y-=1;
            range.add(new int[]{this.xpos+x, this.ypos-Y });
            x+=1;
        }
        //oben links
        Y=3;
        x=0;
        for(int i = 0; i <3 ; i++){
            range.add(new int[]{this.xpos+x, this.ypos - Y});
            Y-=1;
            range.add(new int[]{this.xpos+x, this.ypos-Y });
            x-=1;
        }
        //unten links
        Y=-3;
        x=0;
        for(int i = 3; i >0 ; i--){
            range.add(new int[]{this.xpos+x, this.ypos - Y});
            Y+=1;
            range.add(new int[]{this.xpos+x, this.ypos-Y });
            x-=1;
        }
        //zusatz
        range.add(new int[]{this.xpos+3, this.ypos });
        range.add(new int[]{this.xpos-3, this.ypos });




        return range;





    }

    public String getTroopImg() {
        return "/images/troops/artillery" + this.team + ".png";
    }
}
