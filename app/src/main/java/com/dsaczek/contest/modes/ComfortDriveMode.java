package com.dsaczek.contest.modes;

import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;
import com.dsaczek.contest.decorators.AgressiveDecorator;

public class ComfortDriveMode extends DriveModeBase {
    private IGasChange gasHandler;
    final private AgressiveMode agressiveMode;

    public ComfortDriveMode(IGearbox gearbox, IExternal externals, AgressiveMode mode) {
        super(gearbox, externals, new RPM(Characteristics.COMFORT_RPM_INCREASE_IN_ACCELERATION));
        this.agressiveMode = mode;
        rPMHandler = createRPMHandler();
        gasHandler = createGasHandler();
    }

    @Override
    public void recalculateGear() {

        if (reductionDueCarTrailer(new RPM(Characteristics.COMFORT_RPM_DECREASE_IN_BRAKING))){
            gearbox.decreaseGear();
        } else {
            GearChange kickdown_change = gasHandler.getGearDecreaseDueKickdown(externals.getCurrentGas(), externals.getCurrentRPM());
            if (kickdown_change.getChange() == 0) {
                super.recalculateGear();
            } else if (kickdown_change.getChange() == -1) {
                gearbox.decreaseGear();
            }
        }
    }

    @Override
    public void changeGear(boolean up) {
        super.changeGear(up);
    }
    
    protected IRPMChange createRPMHandler() {
        return new AgressiveDecorator(new RPMCalculator(new RPM(Characteristics.COMFORT_RPM_DECREASE_IN_ACCELERATION)), agressiveMode);
    }
    protected IGasChange createGasHandler() {
        return new Kickdown(new RPM(Characteristics.COMFORT_RPM_DECREASE_IN_KICKDOWN), new Threshold(Characteristics.COMFORT_KICKDOWN_THRESHOLD));
    }
}
