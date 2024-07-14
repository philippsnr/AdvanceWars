package maps;

import troops.*;

public class LittleIsland extends Map {

    public LittleIsland() {
        this.mapArray = new int[][] {
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 1, 0, 0, 0, 1, 3},
                {3, 3, 3, 3, 3, 3, 3, 3, 0, 7, 5, 5, 5, 5, 5, 5, 16, 0, 3},
                {3, 3, 3, 3, 3, 3, 3, 2, 0, 4, 0, 1, 0, 0, 0, 0, 0, 0, 3},
                {3, 3, 3, 3, 3, 1, 0, 1, 1, 4, 0, 0, 0, 1, 0, 1, 0, 3, 3},
                {3, 3, 1, 0, 0, 0, 0, 0, 0, 4, 1, 1, 1, 0, 3, 3, 3, 3, 3},
                {3, 0, 0, 0, 0, 0, 1, 1, 0, 4, 0, 2, 3, 3, 3, 3, 3, 3, 3},
                {3, 0, 15, 5, 5, 5, 5, 5, 5, 9, 0, 3, 3, 3, 3, 3, 3, 3, 3},
                {3, 1, 0, 0, 0, 1, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}
        };
    }

    public int[][] getFactoryCoordinates(){
        return new int[][] {{7, 2}, {2, 16}};
    };

    public Troop[] getInitTroops() {
        Troop inf11 = new Infantry(1, 5, 4);
        Troop inf12 = new Infantry(1, 7, 5);
        Troop inf13 = new Infantry(1, 6, 7);
        Troop mech1 = new Mech(1, 4, 6);
        Troop tank1 = new Tank(1, 3, 7);
        Troop antiair1 = new AntiAir(1, 1, 7);
        Troop bomber1 = new Bomber(1, 0, 3);
        Troop copter1 = new Copter(1, 4, 8);

        Troop inf21 = new Infantry(2, 11, 2);
        Troop inf22 = new Infantry(2, 12, 4);
        Troop inf23 = new Infantry(2, 12, 5);
        Troop mech2 = new Mech(2, 13, 3);
        Troop tank2 = new Tank(2, 15, 2);
        Troop antiair2 = new AntiAir(2, 17, 2);
        Troop bomber2 = new Bomber(2, 18, 7);
        Troop copter2 = new Copter(2, 16, 1);

        return new Troop[] {inf11, inf12, inf13, inf21, inf22, inf23, mech1, mech2, tank1, tank2, antiair1, antiair2, bomber1, bomber2, copter1, copter2};
    }
}
