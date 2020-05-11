package com.dsaczek.contest;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.vars.DriveMode;
import com.dsaczek.contest.utils.DriveRouterExposedForwarder;
import com.dsaczek.contest.vars.Threshold;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DriveRouterForwarderTest {
    private DriveRouterExposedForwarder driveRouter;

    @Mock
    private IExternal externals;
    @Mock
    private IGearbox gearbox;

    @Before
    public void setUp() {
        driveRouter = new DriveRouterExposedForwarder(gearbox, externals);
    }

    @Test
    public void recalculateGear_ForwardToDriveModeHandler() {

        driveRouter.recalculateGear();
        verify(driveRouter.driveModeHandlerMock, times(1)).recalculateGear();
    }

    @Test
    public void changeGear_ForwardToDriveModeHandler() {
        driveRouter.changeGear(true);
        verify(driveRouter.driveModeHandlerMock, times(1)).changeGear(true);
        driveRouter.changeGear(false);
        verify(driveRouter.driveModeHandlerMock, times(1)).changeGear(false);
    }

    @Test
    public void changeAgressive_ForwardToDriveModeHandler() {
        driveRouter.changeAgressive(AgressiveMode.Agressive_1);
        verify(driveRouter.driveModeHandlerMock, never()).changeGear(any(boolean.class));
        verify(driveRouter.driveModeHandlerMock, never()).recalculateGear();
    }

    @Test
    public void changeManual_ForwardToDriveModeHandler() {
        driveRouter.changeManual(true);
        verify(driveRouter.driveModeHandlerMock, never()).changeGear(any(boolean.class));
        verify(driveRouter.driveModeHandlerMock, never()).recalculateGear();
    }

    @Test
    public void changeMDynamic_ForwardToDriveModeHandler() {
        driveRouter.changeMDynamics(true);
        verify(driveRouter.driveModeHandlerMock, never()).changeGear(any(boolean.class));
        verify(driveRouter.driveModeHandlerMock, never()).recalculateGear();
    }

    @Test
    public void changeDriveMode_ForwardToDriveModeHandler() {
        driveRouter.changeDriveMode(DriveMode.Eco);
        verify(driveRouter.driveModeHandlerMock, never()).changeGear(any(boolean.class));
        verify(driveRouter.driveModeHandlerMock, never()).recalculateGear();
    }
}
