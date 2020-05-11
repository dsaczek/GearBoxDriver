package com.dsaczek.contest.utils;

import com.dsaczek.contest.ExternalForwarder;
import com.dsaczek.contest.adapters.IExternal;

import static org.mockito.Mockito.mock;

public class ExternalForwarderExposed extends ExternalForwarder {
    public IExternal externalAdapterMock;

    protected IExternal createExternalAdapter() {
        externalAdapterMock = mock(IExternal.class);
        return externalAdapterMock;
    }

}
