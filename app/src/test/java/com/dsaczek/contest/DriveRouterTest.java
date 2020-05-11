package com.dsaczek.contest;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.modes.ComfortDriveMode;
import com.dsaczek.contest.vars.DriveMode;
import com.dsaczek.contest.modes.RPMCalculator;
import com.dsaczek.contest.modes.EcoDriveMode;
import com.dsaczek.contest.modes.SportDriveMode;
import com.dsaczek.contest.utils.DriveRouterExposed;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DriveRouterTest {
    private DriveRouterExposed drive;

    @Mock
    private IExternal externals;
    @Mock
    private IGearbox gearbox;

    @Before
    public void setUp() {
        drive = new DriveRouterExposed(gearbox, externals);
    }

    @Test
    public void recalculateGear_ComfortDriveMode_byDefault() {

        drive.recalculateGear();

        assertTrue(drive.currentModeMock instanceof ComfortDriveMode);
        verify(drive.currentModeMock, times(1)).recalculateGear();
    }

    @Test
    public void recalculateGear_SetEcoDriveMode() {

        drive.changeDriveMode(DriveMode.Eco);
        drive.recalculateGear();

        assertTrue(drive.currentModeMock instanceof EcoDriveMode);
        verify(drive.currentModeMock, times(1)).recalculateGear();
    }

    @Test
    public void recalculateGear_SetComfortDriveMode() {

        drive.changeDriveMode(DriveMode.Comfort);
        drive.recalculateGear();

        assertTrue(drive.currentModeMock instanceof ComfortDriveMode);
        verify(drive.currentModeMock, times(1)).recalculateGear();
    }

    @Test
    public void recalculateGear_SetSportDriveMode() {

        drive.changeDriveMode(DriveMode.Sport);
        drive.recalculateGear();

        assertTrue(drive.currentModeMock instanceof SportDriveMode);
        verify(drive.currentModeMock, times(1)).recalculateGear();
    }

    @Test
    public void changeGear() {
        drive.changeGear(true);
        verify(drive.currentModeMock, times(1)).changeGear(true);
        drive.changeGear(false);
        verify(drive.currentModeMock, times(1)).changeGear(false);
    }
}
