package com.dsaczek.contest.modes;

import com.dsaczek.contest.vars.GearChange;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

public interface IGasChange {
    GearChange getGearDecreaseDueKickdown(Threshold threshold, RPM current);
}
