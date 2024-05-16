package com.example.advancedwars;

public class EonSprings extends Map{
    public EonSprings() {
        this.mapArray = new int[][]{
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,1},
                {0, 0, 0, 0, 3, 3, 2, 1, 0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0},
                {0, 0, 0, 0, 3, 3, 3, 1, 0, 0 ,0 ,0 ,1 ,0 ,0 ,0 ,0 ,0 ,1},
                {0, 0, 1, 3, 3, 3, 3, 3, 1, 1 ,3 ,3 ,0 ,0 ,0 ,1 ,0 ,0 ,0},
                {3, 0, 3, 3, 3, 3, 3, 3, 3, 3 ,3 ,3 ,2 ,1 ,0 ,0 ,0 ,2 ,0},
                {0, 0, 0, 0, 1, 3, 3, 3, 3, 3 ,3 ,3 ,3 ,1 ,0 ,1 ,0 ,1 ,0},
                {0, 0, 0, 0, 1, 3, 3, 0, 0, 3 ,3 ,3 ,3 ,1 ,0 ,0 ,0 ,0 ,0},
                {0, 0, 0, 0, 0, 3, 0, 0, 3, 3 ,3 ,3 ,3 ,1 ,0 ,1 ,0 ,0 ,1},
                {0, 0, 0, 0, 0, 0, 0, 3, 3, 3 ,3 ,3 ,0 ,0 ,0 ,0 ,0 ,0 ,0},
                {0, 2, 0, 0, 1, 1, 3, 3, 3, 3 ,3 ,0 ,0 ,3 ,1 ,0 ,0 ,0 ,0},
                {0, 0, 0, 0, 0, 1, 3, 3, 3, 3 ,0 ,0 ,3 ,3 ,1 ,0 ,0 ,0 ,0},
                {0, 0, 0, 1, 0, 1, 3, 3, 3, 3 ,3 ,3 ,3 ,3 ,3 ,3 ,0 ,0 ,2},
                {2, 0, 0, 0, 0, 0, 2, 1, 3, 3 ,3 ,3 ,3 ,3 ,3 ,3 ,3 ,0 ,3},
                {0, 1, 0, 0, 1, 0, 0, 0, 0, 1 ,0 ,3 ,3 ,3 ,3 ,3 ,0 ,0 ,0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0 ,1 ,0 ,3 ,3 ,3 ,3 ,1 ,0 ,0},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0 ,0 ,0 ,1 ,3 ,3 ,0 ,0 ,0 ,0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0 ,0 ,0 ,1 ,0 ,0 ,0 ,1}
        };
    }

    protected Troop[] getInitTroops() {
        Troop inf11 = new Infantry(1, 10, 14);
        Troop inf12 = new Infantry(1, 1, 7);
        Troop mech11 = new Mech(1, 4, 6);
        Troop mech12 = new Mech(1, 8, 13);
        Troop mech13 = new Mech(1, 6, 15);
        Troop tank11 = new Tank(1, 4, 8);
        Troop tank12 = new Tank(1, 2, 16);
        Troop artillery11 = new Artillery(1, 1, 10);
        Troop artillery12 = new Artillery(1, 3, 15);
        Troop antiair11 = new AntiAir(1, 2, 8);
        Troop antiair12 = new AntiAir(1, 5, 13);
        Troop fighter11 = new Fighter(1, 3, 10);
        Troop fighter12 = new Fighter(1, 7, 16);
        Troop bomber11 = new Bomber(1, 2, 12);
        Troop bomber12 = new Bomber(1, 3, 13);
        Troop copter1 = new Copter(1, 7, 14);

        Troop inf21 = new Infantry(2, 7, 1);
        Troop inf22 = new Infantry(2, 17, 8);
        Troop mech21 = new Mech(2, 14, 10);
        Troop mech22 = new Mech(2, 8, 3);
        Troop mech23 = new Mech(2, 11, 2);
        Troop tank21 = new Tank(2, 15, 0);
        Troop tank22 = new Tank(2, 13, 8);
        Troop artillery21 = new Artillery(2, 17, 7);
        Troop artillery22 = new Artillery(2, 17, 1);
        Troop antiair21 = new AntiAir(2, 14, 2);
        Troop antiair22 = new AntiAir(2, 16, 8);
        Troop fighter21 = new Fighter(2, 14, 6);
        Troop fighter22 = new Fighter(2, 10, 0);
        Troop bomber21 = new Bomber(2, 15, 4);
        Troop bomber22 = new Bomber(2, 16, 5);
        Troop copter2 = new Copter(2, 9, 2);


        //Test kämpfe
        Troop inft11 = new Infantry(1, 12, 5);

        return new Troop[] {inf11, inf12, mech11, mech12, mech13, tank11, tank12, artillery11, artillery12, antiair11, antiair12, fighter11, fighter12, bomber11, bomber12, copter1, inf21, inf22, mech21, mech22, mech23, tank21, tank22, artillery21, artillery22, antiair21, antiair22, fighter21, fighter22, bomber21, bomber22, copter2,inft11};
    }
}
