package com.example.advancedwars;

public abstract class Map {
    public int[][] mapArray;


    protected abstract Troop[] getInitTroops();

    protected abstract int[][] getFactoryCoordinates();

    // 0: Gras
    // 1: Wald
    // 2: Berg
    // 3: Wasser
    // 4: Straße
    // 5: Factory Red
    // 6: Factory Blue
}
