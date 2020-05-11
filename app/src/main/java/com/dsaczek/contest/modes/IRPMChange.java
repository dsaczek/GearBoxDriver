package com.dsaczek.contest.modes;

import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;

public interface IRPMChange {
    GearChange recalculateGear(RPM end, RPM current);
}
