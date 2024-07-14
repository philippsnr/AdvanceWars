package troops;

public class Copter extends Troop{
    public Copter(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 6, 7, 9000);
    }

    public String getTroopImg() {
        return "/images/troops/copter" + this.team + ".png";
    }

}
