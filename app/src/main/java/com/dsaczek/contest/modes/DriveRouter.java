package com.dsaczek.contest.modes;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.decorators.DriveBlockerDecorator;
import com.dsaczek.contest.modes.ComfortDriveMode;
import com.dsaczek.contest.modes.IDriveMode;
import com.dsaczek.contest.vars.DriveMode;
import com.dsaczek.contest.modes.EcoDriveMode;
import com.dsaczek.contest.modes.SportDriveMode;
import com.dsaczek.contest.vars.AgressiveMode;

public class DriveRouter {
    private IGearbox gearbox;
    private IExternal externals;
    private IDriveMode currentMode;
    private boolean mDynamics = false;
    private AgressiveMode agressiveMode = AgressiveMode.Agressive_1;
    private DriveMode driveMode = DriveMode.Comfort;
    boolean manual = false;

    public DriveRouter(IGearbox gearbox, IExternal externals) {
        this.gearbox = gearbox;
        this.externals = externals;

        changeDriveMode(driveMode);
    }

    public void changeDriveMode(DriveMode mode)
    {
        this.driveMode = mode;
        currentMode = createDriveMode();
    }

    public void changeManual(boolean manual) {
        this.manual = manual;
        currentMode = createDriveMode();
    };

    public void changeMDynamics(boolean mDynamics) {
        this.mDynamics = mDynamics;
        currentMode = createDriveMode();
    };

    private IDriveMode createDriveMode()
    {
        switch (driveMode) {
            case Eco: {
                return createEcoHandler();
            }
            case Sport: {
                return createSportHandler();
            }
            default: {
                return createComfortHandler();
            }
        }
    }

    public String getInformation() {
        return new String("mode:"+driveMode.name()+", manual:"+manual+", mDynamics:"+mDynamics+", agressive:"+agressiveMode.name());
    }

    public void changeAgressive(AgressiveMode mode) {
        agressiveMode = mode;
        currentMode = createDriveMode();
    }

    public void recalculateGear() {
        currentMode.recalculateGear();
    }

    public void changeGear(boolean up) {
        currentMode.changeGear(up);
    }

    protected IDriveMode createEcoHandler() {
        return new DriveBlockerDecorator(externals, new EcoDriveMode(gearbox, externals), manual, mDynamics);
    }
    protected IDriveMode createComfortHandler() {
        return new DriveBlockerDecorator(externals, new ComfortDriveMode(gearbox, externals, agressiveMode), manual, mDynamics);
    }
    protected IDriveMode createSportHandler() {
        return new DriveBlockerDecorator(externals, new SportDriveMode(gearbox, externals, agressiveMode), manual, mDynamics);
    }
}
