package com.dsaczek.contest.adapters;

import com.dsaczek.contest.vars.GearboxState;

public interface IGearbox {
    boolean isInDriveState();
    void increaseGear();
    void decreaseGear();
    void decreaseGearTwice();
    void changeGearboxState(GearboxState state);
    void configureGearbox(int gear);
    String getInformation();
}
