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
    // 4: Straße oben/unten
    // 5: Straße rechts/links
    // 6: Straße L-Kurve
    // 7: Straße L-Kurve um 90°
    // 8: Straße L-Kurve um 180°
    // 9: Straße L-Kurve um 270°
    // 10: Kreuzung: _|_ = oben/unten in waagrecht beide Richtungen
    // 11: Kreuzung |- = oben/unten beide Richtungen in waagrecht
    // 12: Kreuzung-10 um 180° gedreht
    // 13: Kreuzung-11 um 180° gedreht
    // 14: Alle-Richtungen-Kreuzung
    // 15&16: Factory Red/Blue
}
