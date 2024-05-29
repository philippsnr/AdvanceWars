package com.example.advancedwars;

public abstract class Map {
    public int[][] mapArray;


    protected abstract Troop[] getInitTroops();

    // 0: Gras
    // 1: Wald
    // 2: Berg
    // 3: Wasser
    // 5: Factory Red
    // 6: Factory Blue
}
