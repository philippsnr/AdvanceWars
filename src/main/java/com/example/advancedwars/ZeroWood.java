package com.example.advancedwars;

public class ZeroWood extends Map{
    public ZeroWood(){
        this.mapArray = new int[][]{
                {3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3},
                {3, 0, 0, 0, 0, 7, 5, 5, 5, 5, 5, 8, 0, 0, 3},
                {3, 0, 7, 5, 5, 9, 1, 1, 1, 1, 16, 6, 5, 8, 3},
                {3, 0, 4, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 4, 3},
                {3, 0, 4, 1, 1, 1, 1, 0, 2, 1, 1, 1, 7, 9, 3},
                {3, 2, 4, 1, 1, 1, 0, 0, 0, 1, 1, 1, 4, 2, 3},
                {3, 7, 9, 1, 1, 1, 2, 0, 1, 1, 1, 1, 4, 0, 3},
                {3, 4, 2, 1, 1, 1, 1, 1, 1, 1, 1, 0, 4, 0, 3},
                {3, 6, 5, 8, 15, 1, 1, 1, 1, 7, 5, 5, 9, 0, 3},
                {3, 0, 0, 6, 5, 5, 5, 5, 5, 9, 0, 0, 0, 0, 3},
                {3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3},

        };
    }

    protected int[][] getFactoryCoordinates(){
        return new int[][] {{8, 4}, {2, 10}};
    };

    public  Troop[] getInitTroops() {
        Troop inf11 = new Infantry(1, 3, 9);
        Troop inf12 = new Infantry(1, 1, 7);

        Troop mech11 = new Mech(1, 4, 6);
        Troop mech12 = new Mech(1, 6, 10);

        Troop tank11 = new Tank(1, 4, 8);
        Troop tank12 = new Tank(1, 7, 10);

        Troop artillery11 = new Artillery(1, 1, 10);
        Troop artillery12 = new Artillery(1, 3, 8);

        Troop antiair11 = new AntiAir(1, 2, 7);
        Troop antiair12 = new AntiAir(1, 4, 9);

        Troop fighter11 = new Fighter(1, 3, 10);

        Troop bomber11 = new Bomber(1, 1, 9);





        Troop inf21 = new Infantry(2, 7, 1);

        Troop mech21 = new Mech(2, 14, 10);
        Troop mech22 = new Mech(2, 8, 3);
        Troop mech23 = new Mech(2, 11, 2);

        Troop tank22 = new Tank(2, 13, 8);


        Troop antiair21 = new AntiAir(2, 14, 2);

        Troop fighter21 = new Fighter(2, 14, 6);
        Troop fighter22 = new Fighter(2, 10, 0);

        Troop copter2 = new Copter(2, 9, 2);

        return new Troop[] { inf11, inf12, mech11, mech12,  tank11, tank12,  artillery11, artillery12,  antiair11, antiair12,  fighter11, bomber11,  inf21,  mech21, mech22, mech23,  tank22,  antiair21,  fighter21, fighter22,   copter2};
    }
}
