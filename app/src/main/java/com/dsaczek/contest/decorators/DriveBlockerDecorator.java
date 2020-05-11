package com.dsaczek.contest.decorators;

import com.dsaczek.contest.modes.IDriveMode;
import com.dsaczek.contest.adapters.IExternal;

public class DriveBlockerDecorator implements IDriveMode {
    private IExternal externals;
    private IDriveMode handler;
    private boolean manual;
    private boolean mDynamics;

    public DriveBlockerDecorator(IExternal externals, IDriveMode obj, boolean manual, boolean mDynamics) {
        this.externals = externals;
        this.handler = obj;
        this.manual = manual;
        this.mDynamics = mDynamics;
    }

    @Override
    public void recalculateGear() {
        if (!manual && (!mDynamics || (mDynamics) && !externals.isCarSlipping())) {
            handler.recalculateGear();
        }
    }

    @Override
    public void changeGear(boolean up) {
        handler.changeGear(up);
    }

}
