package com.example.advancedwars;

import java.util.ArrayList;

public class TargetField {

    int x;
    int y;
    ArrayList<TargetField> path;

    public TargetField(int _x, int _y, ArrayList<TargetField> _path) {
        this.x = _x;
        this.y = _y;
        this.path = _path;
    }

    public TargetField(TargetField element) {
        this.x = element.x;
        this.y = element.y;
        this.path = element.path != null ? new ArrayList<>(element.path) : null;
    }

}
