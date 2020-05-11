package com.dsaczek.contest.decorators;

import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.AgressiveFactor;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.modes.IRPMChange;
import com.dsaczek.contest.vars.RPM;


public class AgressiveDecorator implements IRPMChange {
    private IRPMChange handler;
    final private AgressiveFactor agressiveFactor;

    public AgressiveDecorator(IRPMChange obj, AgressiveMode mode) {
        this.handler = obj;
        this.agressiveFactor = new AgressiveFactor(mode);
    }

    @Override
    public GearChange recalculateGear(RPM end, RPM current) {
        return handler.recalculateGear(end.timesFactor(agressiveFactor), current);
    }
}
