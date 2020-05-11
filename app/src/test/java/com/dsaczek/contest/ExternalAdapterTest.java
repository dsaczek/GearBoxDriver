package com.dsaczek.contest;

import com.dsaczek.contest.adapters.ExternalAdapter;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.libs.ExternalSystems;
import com.dsaczek.contest.libs.Lights;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalAdapterTest {
    private IExternal externalAdapter;

    @Mock
    private ExternalSystems externalSystems;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        externalAdapter = new ExternalAdapter(externalSystems);

    }

    @Test
    public void getCurrentRPM() {
        externalAdapter.getCurrentRPM();

        verify(externalSystems, times(1)).getCurrentRpm();
    }

    @Test
    public void changeCurrentRPM() {
        externalAdapter.changeCurrentRPM(new RPM(1000d));

        verify(externalSystems, times(1)).setCurrentRpm(1000d);
    }

    @Test
    public void getCurrentGas() {
        Threshold th = externalAdapter.getCurrentGas();
        assertTrue(th.isEqual(new Threshold(0)));

        externalAdapter.setCurrentGas(new Threshold(0.8));
        th = externalAdapter.getCurrentGas();
        assertTrue(th.isEqual(new Threshold(0.8)));
    }

    @Test
    public void hitchCarTrailer() {
        assertFalse(externalAdapter.isCarTrailer());
        externalAdapter.hitchCarTrailer(true);
        assertTrue(externalAdapter.isCarTrailer());
    }

    @Test
    public void isCarSlipping() {
        when(externalSystems.getAngularSpeed()).thenReturn(50.0);
        assertFalse(externalAdapter.isCarSlipping());

        when(externalSystems.getAngularSpeed()).thenReturn(50.1);
        assertTrue(externalAdapter.isCarSlipping());
    }

    @Test
    public void isCarDrivingDown_OK() {
        Lights lights = mock(Lights.class);
        when(lights.getLightsPosition()).thenReturn(1);
        when(externalSystems.getLights()).thenReturn(lights);
        assertTrue(externalAdapter.isCarDrivingDown());
    }

    @Test
    public void isCarDrivingDown_NOK() {
        Lights lights = mock(Lights.class);
        when(lights.getLightsPosition()).thenReturn(4);
        when(externalSystems.getLights()).thenReturn(lights);
        assertFalse(externalAdapter.isCarDrivingDown());
    }

    @Test
    public void isCarDrivingDown_returnNull_getLight() {
        when(externalSystems.getLights()).thenReturn(null);
        assertFalse(externalAdapter.isCarDrivingDown());
    }

    @Test
    public void isCarDrivingDown_returnNull_getLightPositions() {
        Lights lights = mock(Lights.class);
        when(lights.getLightsPosition()).thenReturn(null);
        when(externalSystems.getLights()).thenReturn(lights);
        assertFalse(externalAdapter.isCarDrivingDown());
    }

    @Test
    public void isCarDrivingDown_simulate() {
        externalAdapter.setSimulateDriveDown(true);
        assertTrue(externalAdapter.isCarDrivingDown());
    }
}

