package com.dsaczek.contest.modes;

import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.vars.RPM;

public class EcoDriveMode extends DriveModeBase {

    public EcoDriveMode(IGearbox gearbox, IExternal externals) {
        super(gearbox, externals, new RPM(Characteristics.ECO_RPM_INCREASE_IN_ACCELERATION));
        rPMHandler = createRPMHandler();
    }

    @Override
    public void recalculateGear() {
        if (reductionDueCarTrailer(new RPM(Characteristics.ECO_RPM_DECREASE_IN_BRAKING))){
            gearbox.decreaseGear();
        } else {
            super.recalculateGear();
        }
    }

    @Override
    public void changeGear(boolean up) {
        super.changeGear(up);
    }

    protected IRPMChange createRPMHandler() {
        return new RPMCalculator(new RPM(Characteristics.ECO_RPM_DECREASE_IN_ACCELERATION));
    }
}
