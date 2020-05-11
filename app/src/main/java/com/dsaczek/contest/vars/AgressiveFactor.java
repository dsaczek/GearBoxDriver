package com.dsaczek.contest.vars;

public class AgressiveFactor {

    final private double factor;

    public AgressiveFactor(AgressiveMode mode) {
        switch (mode) {
            case Agressive_2: {
                factor = 130/100.0;
                break;
            }
            case Agressive_3: {
                factor = 130/100.0;
                break;
            }
            default: {
                factor = 1;
                break;
            }
        }
    }

    public double getFactor() {
        return factor;
    }
}
