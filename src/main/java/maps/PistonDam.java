package maps;

import troops.*;

public class PistonDam extends Map{
    public PistonDam(){
        this.mapArray = new int[][] {
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                {1, 0, 7, 5, 5, 5, 5, 5, 5, 5, 5, 5, 12, 12, 5, 5, 5, 5, 5, 5, 5, 5, 5, 8, 0, 0},
                {1, 0, 4, 1, 0, 0, 1, 0, 0, 1, 0, 1, 4, 4, 1, 0, 0, 1, 0, 0, 1, 0, 16, 4, 0, 1},
                {0, 0, 4, 0, 1, 0, 0, 0, 1, 3, 3, 1, 4, 4, 1, 3, 3, 1, 0, 1, 0, 0, 0, 4, 0, 1},
                {0, 0, 4, 0, 0, 0, 0, 1, 3, 3, 3, 1, 4, 4, 1, 3, 3, 3, 1, 0, 0, 0, 0, 4, 0, 0},
                {3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 3, 0, 4, 4, 0, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3},
                {3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 0, 0, 4, 4, 0, 0, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3},
                {3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 1, 0, 4, 4, 0, 1, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3},
                {3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 3, 0, 4, 4, 0, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3},
                {0, 0, 4, 0, 1, 1, 1, 0, 3, 3, 3, 1, 4, 4, 1, 3, 3, 3, 0, 1, 1, 1, 0, 4, 0, 0},
                {0, 0, 4, 0, 0, 0, 0, 0, 0, 3, 3, 1, 4, 4, 1, 3, 3, 0, 1, 0, 0, 0, 0, 4, 0, 0},
                {1, 0, 4, 15, 0, 0, 0, 0, 0, 0, 0, 1, 4, 4, 1, 0, 0, 1, 0, 0, 0, 0, 0, 4, 0, 1},
                {1, 0, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 10, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 0, 1},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
        };
    }

    public int[][] getFactoryCoordinates(){
        return new int[][] {{11, 3}, {2, 22}};
    }

    public  Troop[] getInitTroops() {
        Troop inf11 = new Infantry(1, 10, 0);
        Troop inf12 = new Infantry(1, 10, 7);
        Troop inf13 = new Infantry(1, 8, 10);
        Troop inf14 = new Infantry(1, 9, 13);
        Troop mech11 = new Mech(1, 8, 2);
        Troop mech12 = new Mech(1, 10, 6);
        Troop mech13 = new Mech(1, 7, 9);
        Troop tank11 = new Tank(1, 3, 1);
        Troop tank12 = new Tank(1, 2, 2);
        Troop tank13 = new Tank(1, 2, 10);
        Troop tank14 = new Tank(1, 3, 12);
        Troop artillery11 = new Artillery(1, 5, 3);
        Troop artillery12 = new Artillery(1, 2, 6);
        Troop antiair1 = new AntiAir(1, 1, 3);
        Troop fighter1 = new Fighter(1, 1, 6);
        Troop bomber1 = new Bomber(1, 5, 10);
        Troop copter1 = new Copter(1, 5, 13);

        Troop inf21 = new Infantry(2, 15, 0);
        Troop inf22 = new Infantry(2, 15, 6);
        Troop inf23 = new Infantry(2, 15, 13);
        Troop inf24 = new Infantry(2, 18, 11);
        Troop mech21 = new Mech(2, 18, 2);
        Troop mech22 = new Mech(2, 17, 10);
        Troop mech23 = new Mech(2, 18, 9);
        Troop tank21 = new Tank(2, 21, 1);
        Troop tank22 = new Tank(2, 23, 3);
        Troop tank23 = new Tank(2, 23, 11);
        Troop tank24 = new Tank(2, 22, 12);
        Troop artillery21 = new Artillery(2, 21, 10);
        Troop artillery22 = new Artillery(2, 23, 7);
        Troop antiair2 = new AntiAir(2, 24, 10);
        Troop fighter2 = new Fighter(2, 25, 6);
        Troop bomber2 = new Bomber(2, 20, 3);
        Troop copter2 = new Copter(2, 19, 0);




        return new Troop[] {inf11, inf12, inf13, inf14, mech11, mech12, mech13, tank11, tank12, tank13, tank14, artillery11, artillery12, antiair1, fighter1, bomber1, copter1, inf21, inf22, inf23, inf24, mech21, mech22, mech23, tank21, tank22, tank23, tank24, artillery21, artillery22, antiair2, fighter2, bomber2, copter2};
    }
}
