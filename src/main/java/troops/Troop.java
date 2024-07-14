package troops;

import java.util.ArrayList;

public abstract class Troop {
    public int health;
    public int team;
    public int xpos;
    public int ypos;
    public int price;
    public boolean moved = false;
    public int stepRange;
    public int identification;


    public Troop(int _team, int _xpos, int _ypos, int _stepRange, int _identification, int _price) {
        this.health = 10;
        this.team = _team;
        this.xpos = _xpos;
        this.ypos = _ypos;
        this.price = _price;
        this.stepRange = _stepRange;
        this.identification = _identification;
    }

    public int getTeam() {
        return team;
    }

    public int getHealth() {
        return health;
    }

    public int getIdentification() {
        return identification;
    }
    public void recieveDamage(int damage){
        this.health-=damage;
    }

    public abstract String getTroopImg();

    public ArrayList<int[]> getAttackRange(int xlength, int ylength) {
        ArrayList<int[]> range = new ArrayList<>();

        if (this.ypos > 0) {
            range.add(new int[]{this.xpos, this.ypos - 1});
        }
        if (this.xpos < xlength - 1) {
            range.add(new int[]{this.xpos + 1, this.ypos});
        }
        if (this.ypos < ylength - 1) {
            range.add(new int[]{this.xpos, this.ypos + 1});
        }
        if (this.xpos > 0) {
            range.add(new int[]{this.xpos - 1, this.ypos});
        }
        return range;
    }
}