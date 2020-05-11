package com.dsaczek.contest;

import com.dsaczek.contest.adapters.GearboxAdapter;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.libs.Gearbox;
import com.dsaczek.contest.modes.DriveRouter;
import com.dsaczek.contest.vars.DriveMode;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.vars.GearboxState;

public class GearboxDriver implements IGearboxDriver {
    private IGearbox gearbox;
    private IExternal externals;
    final private int gearAmount = 8;

    private DriveRouter driveRouter;


    public GearboxDriver(IExternal externals) {
        this.gearbox = createGearboxAdapter();
        this.externals = externals;

        gearbox.configureGearbox(gearAmount);
        gearbox.changeGearboxState(GearboxState.Park);

        driveRouter = createDrive();
    }

    @Override
    public void recalculateGear() {
        if (gearbox.isInDriveState()) {
            driveRouter.recalculateGear();
        }
    }

    @Override
    public void changeGear(boolean up) {
        if (gearbox.isInDriveState()) {
            driveRouter.changeGear(up);
        }
    }

    @Override
    public void changeAgressive(AgressiveMode mode) {
        driveRouter.changeAgressive(mode);
    }

    @Override
    public void changeDriveMode(DriveMode driveMode) {
        driveRouter.changeDriveMode(driveMode);

    }

    public void changeManual(boolean enable) {
        driveRouter.changeManual(enable);
    }

    @Override
    public void changeMDynamics(boolean enable) {
        driveRouter.changeMDynamics(enable);
    }

    @Override
    public void changeGearboxState(GearboxState state) {
        gearbox.changeGearboxState(state);
    }

    @Override
    public String getInformation() {
        String gearInformation = new String("GearBox:{"+ gearbox.getInformation() +"} ");
        String driveInformation = new String("Drive:{"+driveRouter.getInformation()+"} ");
        return gearInformation + driveInformation;
    }

    protected DriveRouter createDrive() {
        return new DriveRouter(gearbox, externals);
    }

    protected IGearbox createGearboxAdapter() {
        return new GearboxAdapter(new Gearbox());
    }
}
