package com.example.kazntu.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Color {
    private int displayP3Red, green, blue;

    Color(int displayP3Red, int green, int blue) {
        this.displayP3Red = displayP3Red;
        this.green = green;
        this.blue = blue;
    }

    public int getDisplayP3Red() {
        return displayP3Red;
    }

    public void setDisplayP3Red(int displayP3Red) {
        this.displayP3Red = displayP3Red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

}
