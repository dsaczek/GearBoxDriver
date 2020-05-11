package com.dsaczek.contest;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.utils.EcoDriveExposed;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EcoDriveModeTest {
    private EcoDriveExposed ecoDriveMode;
    private RPM regularEndRPM = new RPM(Characteristics.ECO_RPM_INCREASE_IN_ACCELERATION);

    @Mock
    private IExternal externals;
    @Mock
    private IGearbox gearbox;

    @Before
    public void setUp() {
        ecoDriveMode = new EcoDriveExposed(gearbox, externals);
    }

    @After
    public void tearDown() {
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void increaseGear_noCarTrialer() {
        RPM currentRPM = new RPM(Characteristics.ECO_RPM_INCREASE_IN_ACCELERATION + 1);
        when(ecoDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(1));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        ecoDriveMode.recalculateGear();

        verify(gearbox, times(1)).increaseGear();
        verify(gearbox, never()).decreaseGear();
    }

    @Test
    public void decreaseGear_noCarTrialer() {
        RPM currentRPM = new RPM(Characteristics.ECO_RPM_DECREASE_IN_ACCELERATION - 1);
        when(ecoDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(-1));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        ecoDriveMode.recalculateGear();

        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void doNotDecreaseOrIncreaseGear_noCarTrialer() {
        double average = (Characteristics.ECO_RPM_DECREASE_IN_ACCELERATION + Characteristics.ECO_RPM_INCREASE_IN_ACCELERATION) / 2;
        RPM currentRPM = new RPM(average);
        when(ecoDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(externals.isCarTrailer()).thenReturn(false);

        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        ecoDriveMode.recalculateGear();

        currentRPM = new RPM(Characteristics.ECO_RPM_DECREASE_IN_ACCELERATION);
        when(ecoDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        ecoDriveMode.recalculateGear();

        currentRPM = new RPM(Characteristics.ECO_RPM_INCREASE_IN_ACCELERATION);
        when(ecoDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        ecoDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void decreaseGear_carTrailer() {
        RPM currentRPM = new RPM(Characteristics.ECO_RPM_DECREASE_IN_BRAKING-1);
        when(externals.isCarTrailer()).thenReturn(true);
        when(externals.isCarDrivingDown()).thenReturn(true);
        when(externals.getCurrentGas()).thenReturn(new Threshold(0));
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        ecoDriveMode.recalculateGear();

        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void carTrailer_doNotDecreaseGear_due_carNotDrivingDown() {
        RPM currentRPM = new RPM(Characteristics.ECO_RPM_DECREASE_IN_BRAKING-1);
        when(externals.isCarTrailer()).thenReturn(true);
        when(externals.getCurrentGas()).thenReturn(new Threshold(0));
        when(ecoDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        ecoDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void carTrailer_doNotDecreaseGear_due_currentGasIsNotZero() {
        when(externals.isCarTrailer()).thenReturn(true);
        when(externals.getCurrentGas()).thenReturn(new Threshold(0.1));
        when(ecoDriveMode.rPMHandlerMock.recalculateGear(any(RPM.class), any(RPM.class))).thenReturn(new GearChange(0));
        when(externals.getCurrentRPM()).thenReturn(new RPM(Characteristics.ECO_RPM_DECREASE_IN_BRAKING-1));
        ecoDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void carTrailer_doNotDecreaseGear_due_RPMIsToHigh() {
        when(externals.isCarTrailer()).thenReturn(true);
        when(externals.getCurrentGas()).thenReturn(new Threshold(0));
        when(ecoDriveMode.rPMHandlerMock.recalculateGear(any(RPM.class), any(RPM.class))).thenReturn(new GearChange(0));
        when(externals.getCurrentRPM()).thenReturn(new RPM(Characteristics.ECO_RPM_DECREASE_IN_BRAKING+1));
        ecoDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void changeGearUp() {
        ecoDriveMode.changeGear(true);
        verify(gearbox, never()).decreaseGear();
        verify(gearbox, times(1)).increaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void changeGearDown() {
        ecoDriveMode.changeGear(false);
        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }
}
