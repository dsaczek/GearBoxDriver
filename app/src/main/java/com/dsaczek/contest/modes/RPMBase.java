package com.dsaczek.contest.modes;

import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;


public class RPMBase implements IRPMChange {
    protected final RPM begin;

    public RPMBase(RPM begin) {
        this.begin = begin;
    }

    @Override
    public GearChange recalculateGear(RPM end, RPM current) {
        return new GearChange(0);
    }
}
