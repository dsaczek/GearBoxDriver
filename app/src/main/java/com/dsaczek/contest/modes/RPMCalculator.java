package com.dsaczek.contest.modes;

import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;

public class RPMCalculator extends RPMBase {

    public RPMCalculator(RPM begin) {
        super(begin);
    }

    @Override
    public GearChange recalculateGear(RPM end, RPM current)
    {
        if (current.isGreater(end)) {
            return new GearChange(1);
        } else if (current.isLower(begin)) {
            return new GearChange(-1);
        }
        return new GearChange(0);
    }
}