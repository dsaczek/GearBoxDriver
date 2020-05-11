package com.dsaczek.contest.utils;

import com.dsaczek.contest.modes.DriveRouter;
import com.dsaczek.contest.modes.IDriveMode;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;

import static org.mockito.Mockito.mock;

public class DriveRouterExposedForwarder extends DriveRouter {

    public IDriveMode driveModeHandlerMock;

    public DriveRouterExposedForwarder(IGearbox gearbox, IExternal externals) {
        super(gearbox, externals);
    }

    protected IDriveMode createEcoHandler() {
        return createDecoratorMock();
    }

    protected IDriveMode createComfortHandler() {
        return createDecoratorMock();
    }

    protected IDriveMode createSportHandler() {
        return createDecoratorMock();
    }

    private IDriveMode createDecoratorMock()
    {
        driveModeHandlerMock = mock(IDriveMode.class);
        return driveModeHandlerMock;
    }
}