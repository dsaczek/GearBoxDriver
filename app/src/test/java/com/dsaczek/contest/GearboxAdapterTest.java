package com.dsaczek.contest;

import com.dsaczek.contest.adapters.GearboxAdapter;
import com.dsaczek.contest.adapters.IGearbox;
import com.dsaczek.contest.libs.Gearbox;
import com.dsaczek.contest.vars.GearboxState;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GearboxAdapterTest {
    private IGearbox gearboxAdapter;

    final private int Drive_State = 1;
    final private int Park_State = 2;
    final private int Reverse_State = 3;
    final private int Neutral_State = 4;

    @Mock
    private Gearbox realGearbox;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        gearboxAdapter = new GearboxAdapter(realGearbox);

    }

    @Test
    public void increaseGear_OK() {
        when(realGearbox.getCurrentGear()).thenReturn(1);
        when(realGearbox.getMaxDrive()).thenReturn(2);

        gearboxAdapter.increaseGear();

        verify(realGearbox, times(1)).setCurrentGear(2);
    }

    @Test
    public void increaseGear_NOK_due_max() {
        when(realGearbox.getCurrentGear()).thenReturn(2);
        when(realGearbox.getMaxDrive()).thenReturn(2);

        gearboxAdapter.increaseGear();

        verify(realGearbox, never()).setCurrentGear(any(int.class));
    }

    @Test
    public void decreaseGear_OK() {
        when(realGearbox.getCurrentGear()).thenReturn(2);

        gearboxAdapter.decreaseGear();

        verify(realGearbox, times(1)).setCurrentGear(1);
    }

    @Test
    public void decreaseGear_NOK_due_min() {
        when(realGearbox.getCurrentGear()).thenReturn(1);

        gearboxAdapter.decreaseGear();

        verify(realGearbox, never()).setCurrentGear(any(int.class));
    }

    @Test
    public void decreaseGearTwice_OK() {
        when(realGearbox.getCurrentGear()).thenReturn(3);

        gearboxAdapter.decreaseGearTwice();

        verify(realGearbox, times(1)).setCurrentGear(1);
    }

    @Test
    public void decreaseGearTwice_OK_onlyOneGearStep_DueToLowGear() {
        when(realGearbox.getCurrentGear()).thenReturn(2);

        gearboxAdapter.decreaseGearTwice();

        verify(realGearbox, times(1)).setCurrentGear(1);
    }

    @Test
    public void decreaseGearTwice_NOK_dueMin() {
        when(realGearbox.getCurrentGear()).thenReturn(1);

        gearboxAdapter.decreaseGearTwice();

        verify(realGearbox, never()).setCurrentGear(1);
    }

    @Test
    public void changeGearboxState_OK_StateReverse_to_Any() {
        when(realGearbox.getCurrentGear()).thenReturn(-1);
        when(realGearbox.getState()).thenReturn(Reverse_State);

        gearboxAdapter.changeGearboxState(GearboxState.Neutral);
        Object[] gearBoxParams = new Object[]{Neutral_State, 0};
        verify(realGearbox, times(1)).setGearBoxCurrentParams(gearBoxParams);

        gearboxAdapter.changeGearboxState(GearboxState.Park);
        gearBoxParams = new Object[]{Park_State, 0};
        verify(realGearbox, times(1)).setGearBoxCurrentParams(gearBoxParams);

        gearboxAdapter.changeGearboxState(GearboxState.Drive);
        gearBoxParams = new Object[]{Drive_State, 1};
        verify(realGearbox, times(1)).setGearBoxCurrentParams(gearBoxParams);
    }

    @Test
    public void changeGearboxState_OK_StateDrive_to_Reverse() {
        when(realGearbox.getCurrentGear()).thenReturn(1);
        when(realGearbox.getState()).thenReturn(Drive_State);

        gearboxAdapter.changeGearboxState(GearboxState.Reverse);
        Object[] gearBoxParams = new Object[]{Reverse_State, -1};
        verify(realGearbox, times(1)).setGearBoxCurrentParams(gearBoxParams);
    }

    @Test
    public void changeGearboxState_NOK_StateReverse_to_Any() {
        when(realGearbox.getCurrentGear()).thenReturn(0);
        when(realGearbox.getState()).thenReturn(Reverse_State);

        gearboxAdapter.changeGearboxState(GearboxState.Neutral);
        gearboxAdapter.changeGearboxState(GearboxState.Park);
        gearboxAdapter.changeGearboxState(GearboxState.Drive);
        verify(realGearbox, never()).setGearBoxCurrentParams(any(Object[].class));
    }

    @Test
    public void changeGearboxState_NOK_StateReverse_to_Reverse() {
        when(realGearbox.getState()).thenReturn(Reverse_State);

        gearboxAdapter.changeGearboxState(GearboxState.Reverse);
        verify(realGearbox, never()).setGearBoxCurrentParams(any(Object[].class));
    }

    @Test
    public void changeGearboxState_OK_StateNeutral_to_Park() {
        when(realGearbox.getCurrentGear()).thenReturn(0);
        when(realGearbox.getState()).thenReturn(Neutral_State);

        gearboxAdapter.changeGearboxState(GearboxState.Park);
        Object[] gearBoxParams = new Object[]{Park_State, 0};

        verify(realGearbox, times(1)).setGearBoxCurrentParams(gearBoxParams);
    }

    @Test
    public void changeGearboxState_NOK_StateNeutral_to_Park() {
        when(realGearbox.getCurrentGear()).thenReturn(1);
        when(realGearbox.getState()).thenReturn(Neutral_State);

        gearboxAdapter.changeGearboxState(GearboxState.Park);
        verify(realGearbox, never()).setGearBoxCurrentParams(any(Object[].class));
    }

    @Test
    public void changeGearboxState_InvalidStateInGearbox() throws Exception {
        expectedException.expect(IllegalStateException.class);
        when(realGearbox.getState()).thenReturn(999);

        gearboxAdapter.changeGearboxState(GearboxState.Park);
        verify(realGearbox, never()).setGearBoxCurrentParams(any(Object[].class));
    }

    @Test
    public void changeGearboxState_OK_StateDrive_to_Park() {
        when(realGearbox.getCurrentGear()).thenReturn(1);
        when(realGearbox.getState()).thenReturn(Drive_State);

        gearboxAdapter.changeGearboxState(GearboxState.Park);
        Object[] gearBoxParams = new Object[]{Park_State, 0};

        verify(realGearbox, times(1)).setGearBoxCurrentParams(gearBoxParams);
    }

    @Test
    public void changeGearboxState_NOK_StateDrive_to_Park() {
        when(realGearbox.getCurrentGear()).thenReturn(0);
        when(realGearbox.getState()).thenReturn(Drive_State);

        gearboxAdapter.changeGearboxState(GearboxState.Park);
        verify(realGearbox, never()).setGearBoxCurrentParams(any(Object[].class));
    }

    @Test
    public void configureGearbox_OK() {
        gearboxAdapter.configureGearbox(1);
        verify(realGearbox, times(1)).setMaxDrive(1);
        Object[] gearBoxParams = new Object[]{Park_State, 0};
        verify(realGearbox, times(1)).setGearBoxCurrentParams(gearBoxParams);
    }

    @Test
    public void configureGearbox_NOK() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        gearboxAdapter.configureGearbox(0);
        verify(realGearbox, never()).setMaxDrive(1);
    }
}
