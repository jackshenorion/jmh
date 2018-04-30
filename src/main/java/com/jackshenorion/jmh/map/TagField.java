package com.jackshenorion.jmh.map;

import java.io.Serializable;

public class TagField implements Serializable {
    private char key;
    private double value;

    public TagField(char key, double value) {
        this.key = key;
        this.value = value;
    }

    public char getKey() {
        return key;
    }

    public double getValue() {
        return value;
    }
}
