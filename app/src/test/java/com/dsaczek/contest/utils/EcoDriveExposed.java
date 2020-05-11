package com.dsaczek.contest.utils;

import com.dsaczek.contest.modes.IRPMChange;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.modes.EcoDriveMode;

import static org.mockito.Mockito.mock;

public class EcoDriveExposed extends EcoDriveMode {
    public IRPMChange rPMHandlerMock;

    public EcoDriveExposed(IGearbox gearbox, IExternal externals) {
        super(gearbox, externals);
    }

    protected IRPMChange createRPMHandler() {
        rPMHandlerMock = mock(IRPMChange.class);
        return rPMHandlerMock;
    }
}
