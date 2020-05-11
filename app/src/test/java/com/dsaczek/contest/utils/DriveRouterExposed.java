package com.dsaczek.contest.utils;

import com.dsaczek.contest.modes.IDriveMode;
import com.dsaczek.contest.modes.ComfortDriveMode;
import com.dsaczek.contest.modes.DriveRouter;
import com.dsaczek.contest.modes.EcoDriveMode;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.modes.SportDriveMode;

import static org.mockito.Mockito.mock;

public class DriveRouterExposed extends DriveRouter {

    public IDriveMode currentModeMock;

    public DriveRouterExposed(IGearbox gearbox, IExternal externals) {
        super(gearbox, externals);
    }

    protected IDriveMode createEcoHandler() {
        currentModeMock = mock(EcoDriveMode.class);
        return currentModeMock;
    }

    protected IDriveMode createComfortHandler() {
        currentModeMock = mock(ComfortDriveMode.class);
        return currentModeMock;
    }

    protected IDriveMode createSportHandler() {
        currentModeMock = mock(SportDriveMode.class);
        return currentModeMock;
    }
}
