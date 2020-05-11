package com.dsaczek.contest.modes;

import com.dsaczek.contest.modes.IGasChange;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

public class Kickdown implements IGasChange {
    public Kickdown(RPM rPMThreshold, Threshold gasThreshold) {
        this.rPMThreshold = rPMThreshold;
        this.gasThreashold = gasThreshold;
    }
    private RPM rPMThreshold;
    private Threshold gasThreashold;

    @Override
    public GearChange getGearDecreaseDueKickdown(Threshold currentGas, RPM currentRPM) {
        if (currentGas.isGreaterOrEqual(this.gasThreashold) && currentRPM.isLowerOrEqual(rPMThreshold)) {
            return new GearChange(-1);
        }
        return new GearChange(0);
    }
}
