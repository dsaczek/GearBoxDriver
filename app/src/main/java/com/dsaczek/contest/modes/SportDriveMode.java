package com.dsaczek.contest.modes;

import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.decorators.AgressiveDecorator;
import com.dsaczek.contest.decorators.HeavyKickdownDecorator;

public class SportDriveMode extends DriveModeBase {
    private IGasChange gasChange;
    final private AgressiveMode agressiveMode;
    public SportDriveMode(IGearbox gearbox, IExternal externals, AgressiveMode mode) {
        super(gearbox, externals, new RPM(Characteristics.SPORT_RPM_INCREASE_IN_ACCELERATION));
        this.agressiveMode = mode;
        rPMHandler = createRPMHandler();
        gasChange = createGasHandler();

    }

    @Override
    public void recalculateGear() {
        if (reductionDueCarTrailer(new RPM(Characteristics.SPORT_RPM_DECREASE_IN_BRAKING))){
            gearbox.decreaseGear();
        } else {
            GearChange kickdown_change = gasChange.getGearDecreaseDueKickdown(externals.getCurrentGas(), externals.getCurrentRPM());
            if (kickdown_change.getChange() == -1) {
                gearbox.decreaseGear();
            } else if (kickdown_change.getChange() == -2) {
                gearbox.decreaseGearTwice();
            } else if (kickdown_change.getChange() == 0){
                super.recalculateGear();
            }
        }
    }

    @Override
    public void changeGear(boolean up) {
        super.changeGear(up);
    }

    protected IRPMChange createRPMHandler() {
        return new AgressiveDecorator(new RPMCalculator(new RPM(Characteristics.SPORT_RPM_DECREASE_IN_ACCELERATION)), agressiveMode);
    }

    protected IGasChange createGasHandler() {
        return new HeavyKickdownDecorator(new Kickdown(new RPM(Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN), new Threshold(Characteristics.SPORT_KICKDOWN_THRESHOLD)), new RPM(Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN), new Threshold(Characteristics.SPORT_HEAVY_KICKDOWN_THRESHOLD));
    }
}
