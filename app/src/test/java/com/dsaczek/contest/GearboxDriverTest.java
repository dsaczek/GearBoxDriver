package com.dsaczek.contest;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.vars.DriveMode;
import com.dsaczek.contest.utils.GearboxDriverExposed;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.vars.GearboxState;

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
public class GearboxDriverTest {

    private GearboxDriverExposed gearboxDriver;

    @Mock
    private IExternal externals;

    private IGearbox gearbox;

    @Before
    public void setUp() {
        gearboxDriver = new GearboxDriverExposed(externals);
        gearbox = gearboxDriver.gearboxAdapterMock;
        verify(gearbox, times(1)).changeGearboxState(GearboxState.Park);
        verify(gearbox, times(1)).configureGearbox(8);

    }

    @Test
    public void recalculateGear_express_driveMode() {
        when(gearbox.isInDriveState()).thenReturn(true);
        gearboxDriver.recalculateGear();
        verify(gearboxDriver.driveRouterMock, times(1)).recalculateGear();
    }

    @Test
    public void recalculateGear_supress_driveMode() {
        when(gearbox.isInDriveState()).thenReturn(false);
        gearboxDriver.recalculateGear();
        verify(gearboxDriver.driveRouterMock, never()).recalculateGear();
    }

    @Test
    public void changeDriveMode() {
        gearboxDriver.changeDriveMode(DriveMode.Eco);
        verify(gearboxDriver.driveRouterMock, times(1)).changeDriveMode(DriveMode.Eco);
        gearboxDriver.changeDriveMode(DriveMode.Sport);
        verify(gearboxDriver.driveRouterMock, times(1)).changeDriveMode(DriveMode.Sport);
        gearboxDriver.changeDriveMode(DriveMode.Comfort);
        verify(gearboxDriver.driveRouterMock, times(1)).changeDriveMode(DriveMode.Comfort);
    }

    @Test
    public void changeManual() {
        gearboxDriver.changeManual(true);
        verify(gearboxDriver.driveRouterMock, times(1)).changeManual(true);
        gearboxDriver.changeManual(false);
        verify(gearboxDriver.driveRouterMock, times(1)).changeManual(false);
    }

    @Test
    public void changeAgressiveMode() {
        gearboxDriver.changeAgressive(AgressiveMode.Agressive_1);
        verify(gearboxDriver.driveRouterMock, times(1)).changeAgressive(AgressiveMode.Agressive_1);
        gearboxDriver.changeAgressive(AgressiveMode.Agressive_2);
        verify(gearboxDriver.driveRouterMock, times(1)).changeAgressive(AgressiveMode.Agressive_2);
        gearboxDriver.changeAgressive(AgressiveMode.Agressive_3);
        verify(gearboxDriver.driveRouterMock, times(1)).changeAgressive(AgressiveMode.Agressive_3);
    }

    @Test
    public void changeGear_supress() {
        when(gearbox.isInDriveState()).thenReturn(false);

        gearboxDriver.changeGear(true);
        verify(gearboxDriver.driveRouterMock, never()).changeGear(true);
        gearboxDriver.changeGear(false);
        verify(gearboxDriver.driveRouterMock, never()).changeGear(false);
    }

    @Test
    public void changeGear_express() {
        when(gearbox.isInDriveState()).thenReturn(true);
        gearboxDriver.changeGear(true);
        verify(gearboxDriver.driveRouterMock, times(1)).changeGear(true);
        gearboxDriver.changeGear(false);
        verify(gearboxDriver.driveRouterMock, times(1)).changeGear(false);
    }

    @Test
    public void changeMDynamics_express() {
        gearboxDriver.changeMDynamics(true);
        verify(gearboxDriver.driveRouterMock, times(1)).changeMDynamics(true);
        gearboxDriver.changeMDynamics(false);
        verify(gearboxDriver.driveRouterMock, times(1)).changeMDynamics(false);
    }

    @Test
    public void changeGearboxState_express() {
        verify(gearbox, times(1)).changeGearboxState(GearboxState.Park);

        gearboxDriver.changeGearboxState(GearboxState.Drive);
        verify(gearbox, times(1)).changeGearboxState(GearboxState.Drive);
        gearboxDriver.changeGearboxState(GearboxState.Park);
        verify(gearbox, times(2)).changeGearboxState(GearboxState.Park);
        gearboxDriver.changeGearboxState(GearboxState.Reverse);
        verify(gearbox, times(1)).changeGearboxState(GearboxState.Reverse);
        gearboxDriver.changeGearboxState(GearboxState.Neutral);
        verify(gearbox, times(1)).changeGearboxState(GearboxState.Neutral);
    }

}
