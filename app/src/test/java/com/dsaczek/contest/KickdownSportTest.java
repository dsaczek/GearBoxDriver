package com.dsaczek.contest;

import com.dsaczek.contest.modes.IGasChange;
import com.dsaczek.contest.modes.Kickdown;
import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class KickdownSportTest {
    IGasChange kickdown;
    private Threshold kickdownThreshold = new Threshold(Characteristics.SPORT_KICKDOWN_THRESHOLD);
    private RPM kickdownHighBound = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN);

    @Before
    public void setUp() {
        kickdown = new Kickdown(kickdownHighBound, kickdownThreshold);
    }

    @Test
    public void decreaseGear() {
        RPM currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN - 1);
        Threshold currentGas = new Threshold(0.6);
        GearChange change = kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM);

        assertTrue(change.getChange() == -1);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN);
        currentGas = new Threshold(0.5);
        change = kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM);

        assertTrue(change.getChange() == -1);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN - 1);
        currentGas = new Threshold(0.5);
        change = kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM);

        assertTrue(change.getChange() == -1);
    }

    @Test
    public void doNotDecreaseGear() {
        RPM currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN + 1);
        Threshold currentGas = new Threshold(0.499);
        GearChange change = kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM);

        assertTrue(change.getChange() == 0);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN + 1);
        currentGas = new Threshold(0.6);
        change = kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM);

        assertTrue(change.getChange() == 0);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN - 1);
        currentGas = new Threshold(0.499);
        change = kickdown.getGearDecreaseDueKickdown(currentGas, currentRPM);

        assertTrue(change.getChange() == 0);
    }

}