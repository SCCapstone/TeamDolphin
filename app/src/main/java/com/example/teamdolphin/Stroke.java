package com.example.teamdolphin;

import android.graphics.Path;

public class Stroke {

    //used for color of the current stroke
    public int color;

    //used to store width of the stroke
    public int strokeWidth;

    //path object is the current path being drawn
    public Path path;

    //constructor sets the attributes of current stroke being used
    public Stroke(int color, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }

}
