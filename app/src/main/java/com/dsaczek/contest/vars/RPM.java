package com.dsaczek.contest.vars;

public class RPM {

    public double getData() {
        return data;
    }

    final private double data;

    public RPM(double data) {
        if (data >= 0) {
            this.data = data;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isGreater(RPM th)
    {
        if (data > th.data) {
            return true;
        }
        return false;
    }

    public boolean isGreaterOrEqual(RPM th)
    {
        if (data > th.data || data == th.data) {
            return true;
        }
        return false;
    }

    public boolean isLower(RPM th)
    {
        if (data < th.data) {
            return true;
        }
        return false;
    }

    public boolean isLowerOrEqual(RPM th)
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
        if (!(obj instanceof RPM)) {
            return false;
        }
        if (data == ((RPM)obj).data) {
            return true;
        }
        return false;
    }

    public RPM timesFactor(AgressiveFactor factor) {
        return new RPM(data * factor.getFactor());
    }
}
