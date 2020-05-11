package com.dsaczek.contest.modes;

public interface IDriveMode {
    void recalculateGear();
    void changeGear(boolean up);
}