package com.dsaczek.contest;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.utils.ComfortDriveExposed;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

import org.junit.After;
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
public class ComfortDriveModeTest {
    private ComfortDriveExposed comfortDriveMode;

    private RPM regularEndRPM = new RPM(Characteristics.COMFORT_RPM_INCREASE_IN_ACCELERATION);
    private Threshold kickdownThreshold = new Threshold(Characteristics.COMFORT_KICKDOWN_THRESHOLD);

    @Mock
    private IExternal externals;
    @Mock
    private IGearbox gearbox;

    @Before
    public void setUp() {
        comfortDriveMode = new ComfortDriveExposed(gearbox, externals, AgressiveMode.Agressive_1);
    }

    @After
    public void tearDown() {
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void increaseGear_noKickdown_noCarTrailer() {
        RPM currentRPM = new RPM(Characteristics.COMFORT_RPM_INCREASE_IN_ACCELERATION + 1);
        Threshold currentGas = new Threshold(0);
        when(comfortDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(1));
        when(comfortDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);

        comfortDriveMode.recalculateGear();

        verify(gearbox, times(1)).increaseGear();
        verify(gearbox, never()).decreaseGear();
    }

    @Test
    public void decreaseGear_noKickdown_noCarTrailer() {
        RPM currentRPM = new RPM(Characteristics.COMFORT_RPM_DECREASE_IN_ACCELERATION - 1);
        Threshold currentGas = new Threshold(0);
        when(comfortDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(-1));
        when(comfortDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);

        comfortDriveMode.recalculateGear();

        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void doNotDecreaseOrIncreaseGear_noKickdown_noCarTrailer() {
        double average = (Characteristics.COMFORT_RPM_DECREASE_IN_ACCELERATION + Characteristics.COMFORT_RPM_INCREASE_IN_ACCELERATION) / 2;
        RPM currentRPM = new RPM(average);
        Threshold currentGas = new Threshold(0);
        when(comfortDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(comfortDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        comfortDriveMode.recalculateGear();

        currentRPM = new RPM(Characteristics.COMFORT_RPM_DECREASE_IN_ACCELERATION);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        when(comfortDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(comfortDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        comfortDriveMode.recalculateGear();

        currentRPM = new RPM(Characteristics.COMFORT_RPM_INCREASE_IN_ACCELERATION);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        when(comfortDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(comfortDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        comfortDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void decreaseGear_Kickdown_noCarTrialer() {
        RPM currentRPM = new RPM(Characteristics.COMFORT_RPM_DECREASE_IN_KICKDOWN - 1);
        Threshold currentGas = new Threshold(0.6);
        when(comfortDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-1));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        comfortDriveMode.recalculateGear();

        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void notDecreaseGear_Kickdown_dueRPMToHigh_noCarTrailer() {
        RPM currentRPM = new RPM(Characteristics.COMFORT_RPM_DECREASE_IN_KICKDOWN);
        Threshold currentGas = new Threshold(0.6);
        when(comfortDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(comfortDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        when(externals.isCarTrailer()).thenReturn(false);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        comfortDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }

    @Test
    public void decreaseGear_carTrialer() {
        RPM currentRPM = new RPM(Characteristics.COMFORT_RPM_DECREASE_IN_BRAKING-1);
        Threshold currentGas = new Threshold(0);
        when(externals.isCarTrailer()).thenReturn(true);
        when(externals.isCarDrivingDown()).thenReturn(true);
        when(externals.getCurrentGas()).thenReturn(currentGas);
        when(externals.getCurrentRPM()).thenReturn(currentRPM);
        comfortDriveMode.recalculateGear();

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
        when(comfortDriveMode.rPMHandlerMock.recalculateGear(regularEndRPM, currentRPM)).thenReturn(new GearChange(0));
        when(comfortDriveMode.gasHandlerMock.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(0));
        when(externals.getCurrentRPM()).thenReturn(currentRPM);

        comfortDriveMode.recalculateGear();

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, never()).increaseGear();
        verify(gearbox, never()).decreaseGearTwice();
    }

    @Test
    public void changeGearUp() {
        comfortDriveMode.changeGear(true);

        verify(gearbox, never()).decreaseGear();
        verify(gearbox, times(1)).increaseGear();
    }

    @Test
    public void changeGearDown() {
        comfortDriveMode.changeGear(false);

        verify(gearbox, times(1)).decreaseGear();
        verify(gearbox, never()).increaseGear();
    }
}
