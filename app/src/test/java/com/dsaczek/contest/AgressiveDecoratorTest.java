package com.dsaczek.contest;

import com.dsaczek.contest.decorators.AgressiveDecorator;
import com.dsaczek.contest.modes.IRPMChange;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.vars.RPM;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AgressiveDecoratorTest {
    private AgressiveDecorator decorator;
    @Mock
    private IRPMChange object;

    @Mock
    private RPM current;

    @Test
    public void decorator_supress_recalculateGear_Agressive_1() {
        decorator = new AgressiveDecorator(object, AgressiveMode.Agressive_1);

        decorator.recalculateGear(new RPM(1000), current);

        verify(object, times(1)).recalculateGear(new RPM(1000), current);
    }

    @Test
    public void decorator_express_recalculateGear_Agressive_2() {
        decorator = new AgressiveDecorator(object, AgressiveMode.Agressive_2);

        decorator.recalculateGear(new RPM(1000), current);

        verify(object, times(1)).recalculateGear(new RPM(1000 * 130/100), current);
    }

    @Test
    public void decorator_express_recalculateGear_Agressive_3() {
        decorator = new AgressiveDecorator(object, AgressiveMode.Agressive_3);

        decorator.recalculateGear(new RPM(1000), current);

        verify(object, times(1)).recalculateGear(new RPM(1000 * 130/100), current);
    }
}