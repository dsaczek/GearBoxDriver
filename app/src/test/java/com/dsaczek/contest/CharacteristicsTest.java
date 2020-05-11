package com.dsaczek.contest;

import com.dsaczek.contest.utils.Characteristics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CharacteristicsTest {
    private final double epsilon = 0.001;
    @Test
    public void characteristicsAreValid() {

        assertEquals(2000d, Characteristics.ECO_RPM_INCREASE_IN_ACCELERATION, epsilon);
        assertEquals(1000d, Characteristics.ECO_RPM_DECREASE_IN_ACCELERATION, epsilon);

        assertTrue(Characteristics.ECO_RPM_INCREASE_IN_ACCELERATION > Characteristics.ECO_RPM_DECREASE_IN_ACCELERATION);

        assertEquals( 1000d, Characteristics.COMFORT_RPM_DECREASE_IN_ACCELERATION, epsilon);
        assertEquals( 0.5d, Characteristics.COMFORT_KICKDOWN_THRESHOLD, epsilon);
        assertEquals( 2500d, Characteristics.COMFORT_RPM_INCREASE_IN_ACCELERATION, epsilon);
        assertEquals( 4500d, Characteristics.COMFORT_RPM_DECREASE_IN_KICKDOWN, epsilon);

        assertTrue(Characteristics.COMFORT_RPM_INCREASE_IN_ACCELERATION > Characteristics.COMFORT_RPM_DECREASE_IN_ACCELERATION);

        assertEquals( 1500d, Characteristics.SPORT_RPM_DECREASE_IN_ACCELERATION, epsilon);
        assertEquals( 0.5d, Characteristics.SPORT_KICKDOWN_THRESHOLD, epsilon);
        assertEquals( 5000d, Characteristics.SPORT_RPM_INCREASE_IN_ACCELERATION, epsilon);
        assertEquals( 0.7d, Characteristics.SPORT_HEAVY_KICKDOWN_THRESHOLD, epsilon);
        assertEquals( 5000d, Characteristics.SPORT_RPM_DECREASE_IN_KICKDOWN, epsilon);
        assertEquals( 5000d, Characteristics.SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN, epsilon);

        assertTrue(Characteristics.SPORT_HEAVY_KICKDOWN_THRESHOLD > Characteristics.SPORT_KICKDOWN_THRESHOLD);
        assertTrue(Characteristics.SPORT_RPM_INCREASE_IN_ACCELERATION > Characteristics.SPORT_RPM_DECREASE_IN_ACCELERATION);

        assertEquals( 1500d, Characteristics.ECO_RPM_DECREASE_IN_BRAKING, epsilon);
        assertEquals( 2000d, Characteristics.COMFORT_RPM_DECREASE_IN_BRAKING, epsilon);
        assertEquals( 3000d, Characteristics.SPORT_RPM_DECREASE_IN_BRAKING, epsilon);

    }
}
