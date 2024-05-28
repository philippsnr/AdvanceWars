package com.example.advancedwars;

public abstract class Map {
    public int[][] mapArray;


    protected abstract Troop[] getInitTroops();

    protected abstract Factory[] getInitFactorys();

    // 0: Gras
    // 1: Wald
    // 2: Berg
    // 3: Wasser
}
