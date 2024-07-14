package troops;

public class Mech extends Troop{

    public Mech(int _team, int _xpos, int _ypos) {
        super(_team, _xpos, _ypos, 2, 1, 3000);
    }

    public String getTroopImg() {
        return "/images/troops/mech" + this.team + ".png";
    }

}