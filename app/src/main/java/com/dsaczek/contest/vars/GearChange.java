package com.dsaczek.contest.vars;

public class GearChange {
    final private int change;

    public GearChange(int data) {
        if (data >= -2 && data <= 1) {
            this.change = data;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getChange() {
        return change;
    }
}
