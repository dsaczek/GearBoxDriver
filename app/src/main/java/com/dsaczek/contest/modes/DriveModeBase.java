package com.dsaczek.contest.modes;

import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

public class DriveModeBase implements IDriveMode {
    protected IGearbox gearbox;
    protected IExternal externals;
    protected IRPMChange rPMHandler;
    protected final RPM end;

    public DriveModeBase(IGearbox gearbox, IExternal externals, RPM end) {
        this.gearbox = gearbox;
        this.externals = externals;
        this.end = end;
    }

    protected boolean reductionDueCarTrailer(RPM rpm) {
        if (externals.isCarTrailer() && externals.getCurrentGas().isEqual(new Threshold(0)) && externals.isCarDrivingDown() && externals.getCurrentRPM().isLower(rpm)) {
            return true;
        }
        return false;
    }

    @Override
    public void recalculateGear() {
        GearChange regular_change = rPMHandler.recalculateGear(end, externals.getCurrentRPM());
        if (regular_change.getChange() == -1) {
            gearbox.decreaseGear();
        } else if (regular_change.getChange() == 1){
            gearbox.increaseGear();
        }
    }

    @Override
    public void changeGear(boolean up) {
        if (up) {
            gearbox.increaseGear();
        } else {
            gearbox.decreaseGear();
        }
    }
}
