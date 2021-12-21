package com.mesut.math.ipret;

import java.util.ArrayList;
import java.util.List;

public class Output {
    public String text;
    List<Point> points = new ArrayList<>();
    //or graph data


    public Output(String text) {
        this.text = text;
    }

    public void print() {
        System.out.println(text);
    }
}
