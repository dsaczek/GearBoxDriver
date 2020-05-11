package com.dsaczek.contest;


import com.dsaczek.contest.utils.ExternalForwarderExposed;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExternalForwarderTest {
    private ExternalForwarderExposed externalForwarder;
    
    @Before
    public void setUp() {
        externalForwarder = new ExternalForwarderExposed();
    }


    @Test
    public void forward_getCurrentRPM() {
        externalForwarder.getCurrentRPM();
        verify(externalForwarder.externalAdapterMock, times(1)).getCurrentRPM();
    }

    @Test
    public void forward_getCurrentGas() {
        externalForwarder.getCurrentGas();
        verify(externalForwarder.externalAdapterMock, times(1)).getCurrentGas();
    }

    @Test
    public void forward_setCurrentGas() {
        externalForwarder.setCurrentGas(new Threshold(0));
        verify(externalForwarder.externalAdapterMock, times(1)).setCurrentGas(any(Threshold.class));
    }

    @Test
    public void forward_changeCurrentRPM() {
        externalForwarder.changeCurrentRPM(new RPM(1000d));
        verify(externalForwarder.externalAdapterMock, times(1)).changeCurrentRPM(any(RPM.class));
    }

    @Test
    public void forward_isCarSlipping() {
        externalForwarder.isCarSlipping();
        verify(externalForwarder.externalAdapterMock, times(1)).isCarSlipping();
    }

    @Test
    public void forward_isCarDrivingDown() {
        externalForwarder.isCarDrivingDown();
        verify(externalForwarder.externalAdapterMock, times(1)).isCarDrivingDown();
    }

    @Test
    public void forward_isCarTrailer() {
        externalForwarder.isCarTrailer();
        verify(externalForwarder.externalAdapterMock, times(1)).isCarTrailer();
    }

    @Test
    public void forward_hitchCarTrailer() {
        externalForwarder.hitchCarTrailer(true);
        verify(externalForwarder.externalAdapterMock, times(1)).hitchCarTrailer(any(boolean.class));
    }

    @Test
    public void forward_setSimulateDriveDown() {
        externalForwarder.setSimulateDriveDown(true);
        verify(externalForwarder.externalAdapterMock, times(1)).setSimulateDriveDown(any(boolean.class));
    }


    @Test
    public void forward_getInformation() {
        externalForwarder.getInformation();
        verify(externalForwarder.externalAdapterMock, times(1)).getInformation();
    }


}
