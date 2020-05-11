package com.dsaczek.contest.utils;

import com.dsaczek.contest.modes.IGasChange;
import com.dsaczek.contest.modes.IRPMChange;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.modes.SportDriveMode;
import com.dsaczek.contest.vars.AgressiveMode;

import static org.mockito.Mockito.mock;

public class SportDriveExposed  extends SportDriveMode {
    public IRPMChange rPMHandlerMock;
    public IGasChange gasHandlerMock;

    public SportDriveExposed(IGearbox gearbox, IExternal externals, AgressiveMode mode) {
        super(gearbox, externals, mode);
    }

    protected IRPMChange createRPMHandler() {
        rPMHandlerMock = mock(IRPMChange.class);
        return rPMHandlerMock;
    }

    protected IGasChange createGasHandler() {
        gasHandlerMock = mock(IGasChange.class);
        return gasHandlerMock;
    }
}
