package com.dsaczek.contest;

import com.dsaczek.contest.decorators.HeavyKickdownDecorator;
import com.dsaczek.contest.modes.IGasChange;
import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HeavyKickdownDecoratorSportTest {
    IGasChange kickdownDecorator;

    private Threshold heavyKickThreshold = new Threshold(Characteristics.SPORT_HEAVY_KICKDOWN_THRESHOLD);
    private RPM heavyKickdownHighBound = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN);

    @Mock
    private IGasChange kickdown;

    @Before
    public void setUp() {
        kickdownDecorator = new HeavyKickdownDecorator(kickdown, heavyKickdownHighBound, heavyKickThreshold);
    }

    @Test
    public void decreaseGear() {
        RPM currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN - 1);
        Threshold currentGas = new Threshold(0.8);
        when(kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-1));
        GearChange change = kickdownDecorator.getGearDecreaseDueKickdown(currentGas, currentRPM);
        assertTrue(change.getChange() == -2);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN -1);
        currentGas = new Threshold(0.7);
        when(kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-1));
        change = kickdownDecorator.getGearDecreaseDueKickdown(currentGas, currentRPM);
        assertTrue(change.getChange() == -2);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN);
        currentGas = new Threshold(0.8);
        when(kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-1));
        change = kickdownDecorator.getGearDecreaseDueKickdown(currentGas, currentRPM);
        assertTrue(change.getChange() == -2);
    }

    @Test
    public void doNotDecreaseGear() {
        RPM currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN + 1);
        Threshold currentGas = new Threshold(0.6);
        when(kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-1));
        GearChange change = kickdownDecorator.getGearDecreaseDueKickdown(currentGas, currentRPM);
        assertTrue(change.getChange() == -1);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN + 1);
        currentGas = new Threshold(0.7);
        when(kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-1));
        change = kickdownDecorator.getGearDecreaseDueKickdown(currentGas, currentRPM);
        assertTrue(change.getChange() == -1);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN);
        currentGas = new Threshold(0.6);
        when(kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM)).thenReturn(new GearChange(-1));
        change = kickdownDecorator.getGearDecreaseDueKickdown(currentGas, currentRPM);
        assertTrue(change.getChange() == -1);
    }
}
