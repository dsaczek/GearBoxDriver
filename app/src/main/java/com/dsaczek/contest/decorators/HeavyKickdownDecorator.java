package com.dsaczek.contest.decorators;

import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.modes.IGasChange;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

public class HeavyKickdownDecorator implements IGasChange {
    public HeavyKickdownDecorator(IGasChange handler, RPM rPMThreshold, Threshold gasThreshold) {
        this.handler = handler;
        this.rPMThreshold = rPMThreshold;
        this.gasThreashold = gasThreshold;
    }
    private IGasChange handler;
    private RPM rPMThreshold;
    private Threshold gasThreashold;

    @Override
    public GearChange getGearDecreaseDueKickdown(Threshold currentGas, RPM currentRPM) {
        GearChange change = handler.getGearDecreaseDueKickdown(currentGas, currentRPM);
        if (currentGas.isGreaterOrEqual(this.gasThreashold) && currentRPM.isLowerOrEqual(rPMThreshold)) {
           change = new GearChange(change.getChange() - 1);
        }

        return change;
    }


}
