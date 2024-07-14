package troops;

import java.util.ArrayList;

public class Artillery extends Troop{
    public Artillery(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 5, 3, 6000);
    }
    public ArrayList<int[]> getAttackRange(int xlength, int ylength){
        ArrayList<int[]> range = new ArrayList<>();
        //unten rechts
        int Y=-3;
        int x=0;

        for(int i = 3; i >0 ; i--){
            if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&this.ypos-Y>=0){range.add(new int[]{this.xpos+x, this.ypos - Y});}

            Y+=1;
            if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&this.ypos-Y>=0){range.add(new int[]{this.xpos+x, this.ypos-Y });}
            x+=1;
        }
        if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&this.ypos-Y>=0){range.add(new int[]{this.xpos+x, this.ypos-Y });}
        //oben rechts
         Y=3;
         x=0;
        for(int i = 0; i <3 ; i++){
            if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&this.ypos-Y>=0){range.add(new int[]{this.xpos+x, this.ypos - Y});
            }
            Y-=1;
            if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&(this.ypos-Y)>=0){range.add(new int[]{this.xpos+x, this.ypos-Y });}
            x+=1;
        }
        //oben links
        Y=3;
        x=0;
        for(int i = 0; i <3 ; i++){
            if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&this.ypos-Y>=0){range.add(new int[]{this.xpos+x, this.ypos - Y});}
            Y-=1;
            if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&this.ypos-Y>=0){range.add(new int[]{this.xpos+x, this.ypos-Y });}
            x-=1;
        }
        //unten links
        Y=-3;
        x=0;
        for(int i = 3; i >0 ; i--){
            if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&this.ypos-Y>=0){range.add(new int[]{this.xpos+x, this.ypos - Y});}
            Y+=1;
            if(this.xpos+x<xlength&&this.ypos-Y<ylength&&this.xpos+x>=0&&this.ypos-Y>=0){range.add(new int[]{this.xpos+x, this.ypos-Y });}
            x-=1;
        }
        //zusatz
        if(this.xpos+x<xlength&&this.ypos<ylength&&this.xpos+x>=0&&this.ypos>=0){ range.add(new int[]{this.xpos-3, this.ypos });}
        return range;
    }

    public String getTroopImg() {
        return "/images/troops/artillery" + this.team + ".png";
    }
}
