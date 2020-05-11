package com.dsaczek.contest.utils;

import com.dsaczek.contest.modes.DriveRouter;
import com.dsaczek.contest.GearboxDriver;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;


import static org.mockito.Mockito.mock;


public class GearboxDriverExposed extends GearboxDriver {

    public DriveRouter driveRouterMock;
    public IGearbox gearboxAdapterMock;

    public GearboxDriverExposed(IExternal externals) {
        super(externals);
    }

    protected DriveRouter createDrive() {
        driveRouterMock = mock(DriveRouter.class);
        return driveRouterMock;
    }

    protected IGearbox createGearboxAdapter() {
        gearboxAdapterMock = mock(IGearbox.class);
        return gearboxAdapterMock;
    }
}
