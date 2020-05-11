package com.dsaczek.contest;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.utils.SportDriveExposed;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SportDriveModeTest {
    private SportDriveExposed sportDriveMode;

    private RPM regularEndRPM = new RPM(Characteristics.SPORT_RPM_INCREASE_IN_ACCELERATION);

    @Mock
    private IExternal externals;
    @Mock
    private IGearbox gearbox;

    @Before
    public void setUp() {
        sportDriveMode = new SportDriveExposed(gearbox, externals, AgressiveMode.Agressive_1);
    }

    @Test
    public void handleGear_OK_noKickdown_noCarTrailer() {
        RPM currentRPM = new RPM(0);
        Threshold currentGas = new Threshold(0);
        when(sportDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(1));
        when(sportDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        sportDriveMode.recalculateGear();

        verify(gearbox, times(1)).increaseGear();
        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).decreaseGearTwice();

        when(sportDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(-1));
        sportDriveMode.recalculateGear();
        verify(gearbox, times(1)).increaseGear();
        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).decreaseGearTwice();

        when(sportDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        sportDriveMode.recalculateGear();
        verify(gearbox, times(1)).increaseGear();
        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void handleGear_InvalidGearChange_noKickdown_noCarTrailer() {
        RPM currentRPM = new RPM(0);
        Threshold currentGas = new Threshold(0);
        when(sportDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(-2));
        when(sportDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        sportDriveMode.recalculateGear();

        verify(gearbox, never()).increaseGear();
        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void decreaseGear_Kickdown_noCarTrailer() {
        RPM currentRPM = new RPM(0);
        Threshold currentGas = new Threshold(0.6);
        when(sportDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-1));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        sportDriveMode.recalculateGear();

        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void decreaseGearTwice_HeavyKickdown_noCarTrailer() {
        RPM currentRPM = new RPM(0);
        Threshold currentGas = new Threshold(0.6);
        when(sportDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-2));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        sportDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
        verify(gearbox, times(1)).decreaseGearTwice();
    }

    @Test
    public void decreaseGear_carTrialer() {
        RPM currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_BRAKING-1);
        Threshold currentGas = new Threshold(0);
        when(externals.isCarTrailer()).thenReturn(true);
        when(externals.isCarDrivingDown()).thenReturn(true);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        sportDriveMode.recalculateGear();

        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void carTrailer_doNotDecreaseGear_due_carNotDrivingDown() {
        RPM currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_BRAKING-1);
        Threshold currentGas = new Threshold(0);
        when(externals.isCarDrivingDown()).thenReturn(false);
        when(externals.isCarTrailer()).thenReturn(true);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(sportDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(sportDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        when(externals.getCurrentRPM()).thenReturn(currentRPM);

        sportDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void changeGearUp() {
        sportDriveMode.changeGear(true);

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, times(1)).increaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void changeGearDown() {
        sportDriveMode.changeGear(false);

        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

}
