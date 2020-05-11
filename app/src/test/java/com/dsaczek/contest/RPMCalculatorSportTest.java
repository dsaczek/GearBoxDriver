package com.dsaczek.contest;

import com.dsaczek.contest.modes.RPMCalculator;
import com.dsaczek.contest.utils.Characteristics;
import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RPMCalculatorSportTest {
    RPMCalculator rpmCalculator;
    private RPM regularLowBoundRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_ACCELERATION);
    private RPM regularHighBoundRPM = new RPM(Characteristics.SPORT_RPM_INCREASE_IN_ACCELERATION);

    @Before
    public void setUp() {
        rpmCalculator = new RPMCalculator(regularLowBoundRPM);
    }

    @Test
    public void increaseGear() {
        RPM currentRPM = new RPM(Characteristics.SPORT_RPM_INCREASE_IN_ACCELERATION + 1);
        GearChange change = rpmCalculator.recalculateGear(regularHighBoundRPM, currentRPM);

        assertTrue(change.getChange() == 1);
    }

    @Test
    public void decreaseGear() {
        RPM currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_ACCELERATION - 1);
        GearChange change = rpmCalculator.recalculateGear(regularHighBoundRPM, currentRPM);

        assertTrue(change.getChange() == -1);
    }

    @Test
    public void doNotDecreaseNeitherIncreaseGear() {
        double average = (Characteristics.SPORT_RPM_DECREASE_IN_ACCELERATION + Characteristics.SPORT_RPM_INCREASE_IN_ACCELERATION) / 2;
        RPM currentRPM = new RPM(average);
        GearChange change = rpmCalculator.recalculateGear(regularHighBoundRPM, currentRPM);

        assertTrue(change.getChange() == 0);

        currentRPM = new RPM(Characteristics.SPORT_RPM_DECREASE_IN_ACCELERATION);
        change = rpmCalculator.recalculateGear(regularHighBoundRPM, currentRPM);
        assertTrue(change.getChange() == 0);

        currentRPM = new RPM(Characteristics.SPORT_RPM_INCREASE_IN_ACCELERATION);
        change = rpmCalculator.recalculateGear(regularHighBoundRPM, currentRPM);
        assertTrue(change.getChange() == 0);
    }
}
