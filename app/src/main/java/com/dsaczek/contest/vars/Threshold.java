package com.dsaczek.contest.vars;

public class Threshold {
    final private double data;

    public Threshold(double data) {
        if (data >= 0 && data <= 1) {
            this.data = data;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isGreater(Threshold th)
    {
        if (data > th.data) {
            return true;
        }
        return false;
    }

    public boolean isEqual(Threshold th)
    {
        if (data == th.data) {
            return true;
        }
        return false;
    }

    public boolean isGreaterOrEqual(Threshold th)
    {
        if (data > th.data || data == th.data) {
            return true;
        }
        return false;
    }

    public boolean isLower(Threshold th)
    {
        if (data < th.data) {
            return true;
        }
        return false;
    }

    public boolean isLowerOrEqual(Threshold th)
    {
        if (data < th.data || data == th.data) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return Double.toString(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Threshold)) {
            return false;
        }
        if (data == ((Threshold)obj).data) {
            return true;
        }
        return false;
    }

}
