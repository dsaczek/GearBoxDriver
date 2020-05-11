package com.dsaczek.contest;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.decorators.DriveBlockerDecorator;
import com.dsaczek.contest.modes.IDriveMode;

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
public class DriveBlockerDecoratorTest {
    private DriveBlockerDecorator decorator;
    @Mock
    private IDriveMode object;
    @Mock
    IExternal externals;

    @Test
    public void decorator_express_recalculateGear_manualOff_mDynamicsOff() {
        decorator = new DriveBlockerDecorator(externals, object, false, false);

        decorator.recalculateGear();
        verify(object, times(1)).recalculateGear();
    }

    @Test
    public void decorator_express_recalculateGear_manualOff_mDynamicsOn_ButCarNotSlipping() {
        decorator = new DriveBlockerDecorator(externals, object, false, true);

        decorator.recalculateGear();
        verify(object, times(1)).recalculateGear();
    }

    @Test
    public void decorator_express_recalculateGear_manualOff_mDynamicsOff_CarIsSlipping() {
        decorator = new DriveBlockerDecorator(externals, object, false, false);

        decorator.recalculateGear();
        verify(object, times(1)).recalculateGear();
    }

    @Test
    public void decorator_supress_recalculateGear_manualOn_mDynamicsOff() {
        decorator = new DriveBlockerDecorator(externals, object, true, false);

        decorator.recalculateGear();
        verify(object, never()).recalculateGear();
    }

    @Test
    public void decorator_supress_recalculateGear_manualOff_mDynamicsOn_CarIsSlipping() {
        when(externals.isCarSlipping()).thenReturn(true);
        decorator = new DriveBlockerDecorator(externals, object, false, true);

        decorator.recalculateGear();
        verify(object, never()).recalculateGear();
    }

    @Test
    public void decorator_express_changeGear() {
        decorator = new DriveBlockerDecorator(externals, object, false, false);
        decorator.changeGear(true);
        verify(object, times(1)).changeGear(true);
        decorator.changeGear(false);
        verify(object, times(1)).changeGear(false);
    }
}
