package com.example.advancedwars;

public class CogIsle extends Map{
    public CogIsle(){
        this.mapArray = new int[][] {
                {3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3},
                {3, 0, 0, 0, 0, 7, 5, 5, 5, 8, 0, 0, 0, 0, 3},
                {3, 0, 7, 5, 5, 9, 1, 2, 16, 6, 5, 5, 5, 8, 3},
                {3, 0, 4, 2, 1, 2, 2, 0, 2, 2, 2, 2, 2, 4, 3},
                {3, 0, 6, 5, 8, 2, 2, 0, 2, 2, 7, 5, 5, 9, 3},
                {3, 2, 2, 2, 4, 2, 0, 1, 0, 2, 4, 2, 2, 2, 3},
                {3, 7, 5, 5, 9, 2, 2, 0, 2, 2, 6, 5, 8, 0, 3},
                {3, 4, 2, 2, 2, 2, 2, 0, 2, 2, 1, 2, 4, 0, 3},
                {3, 6, 5, 5, 5, 8, 15, 2, 1, 7, 5, 5, 9, 0, 3},
                {3, 0, 0, 0, 0, 6, 5, 5, 5, 9, 0, 0, 0, 0, 3},
                {3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3},
        };
    }

    protected int[][] getFactoryCoordinates(){
        return new int[][] {{8, 6}, {2, 9}};
    };

    public  Troop[] getInitTroops() {


        Troop inf11 = new Infantry(1, 3, 9);
        Troop inf12 = new Infantry(1, 1, 7);
        Troop inf13 = new Infantry(1, 2, 4);

        Troop mech11 = new Mech(1, 4, 6);
        Troop mech12 = new Mech(1, 6, 10);

        Troop tank11 = new Tank(1, 4, 8);
        Troop tank12 = new Tank(1, 3, 5);

        Troop artillery11 = new Artillery(1, 1, 9);
        Troop artillery12 = new Artillery(1, 3, 8);

        Troop antiair11 = new AntiAir(1, 2, 8);
        Troop antiair12 = new AntiAir(1, 3, 5);

        Troop fighter11 = new Fighter(1, 3, 9);
        Troop fighter12 = new Fighter(1, 1, 6);

        Troop bomber11 = new Bomber(1, 1, 9);

        Troop copter1 = new Copter(1, 2, 6);





        Troop inf21 = new Infantry(2, 7, 1);
        Troop inf22 = new Infantry(2, 8, 5);
        Troop inf23 = new Infantry(2, 9, 9);

        Troop mech21 = new Mech(2, 14, 2);
        Troop mech22 = new Mech(2, 8, 3);

        Troop tank21 = new Tank(2, 10, 5);
        Troop tank22 = new Tank(2, 13, 8);

        Troop artillery21 = new Artillery(2, 12, 4);
        Troop artillery22 = new Artillery(2, 13, 6);

        Troop antiair21 = new AntiAir(2, 14, 2);
        Troop antiair22 = new AntiAir(2, 10, 3);

        Troop fighter21 = new Fighter(2, 14, 6);
        Troop fighter22 = new Fighter(2, 10, 0);

        Troop bomber21 = new Bomber(2, 12, 2);

        Troop copter2 = new Copter(2, 9, 2);

        return new Troop[] { inf11, inf12, inf13, mech11, mech12,  tank11, tank12,  artillery11, artillery12,  antiair11, antiair12,  fighter11, fighter12, bomber11, copter1,  inf21, inf22, inf23, mech21, mech22, tank21,  tank22, artillery21,artillery22, antiair21, antiair22,  fighter21, fighter22, bomber21, copter2};

    }
}
