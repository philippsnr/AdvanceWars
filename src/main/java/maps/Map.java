package maps;

import troops.Troop;

public abstract class Map {
    public int[][] mapArray;


    public abstract Troop[] getInitTroops();

    public abstract int[][] getFactoryCoordinates();

    // 0: Gras
    // 1: Wald
    // 2: Berg
    // 3: Wasser
    // 4: Straße
    // 5: Factory Red
    // 6: Factory Blue
}
