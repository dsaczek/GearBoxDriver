package com.dsaczek.contest;

import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.vars.DriveMode;
import com.dsaczek.contest.vars.GearboxState;
import com.dsaczek.contest.vars.Threshold;

public interface IGearboxDriver {
    /**
     * This function is responsible for recalculating gear position
     * The behave depends on current RPM, current Gas Position, Car Trailer presence,
     * car slip, tilting the car while driving and modes which are controlled by below functions
     * It should be called on every mentioned changes
     * Function has no power in states different than Drive
     */
    void recalculateGear();

    /**
     * This function is responsible for changing manually gear
     * It is not guaranteed that gear will be maintained in none manual mode
     * Next recalculateGear could change it based on its algorithms
     * Function has no power in states different than Drive
     * @param up
     */
    void changeGear(boolean up);

    /**
     * Agressive mode is responsible for increasing RPM bound for which gear is reduced in none manual mode
     * It works only for Sport and Comfort mode
     * Agressive_2 increase RPM bound 30 percent in comparison to Agressive_1
     * Agressive_3 increase RPM bound 30 percent in comparison to Agressive_1
     * @param mode
     */
    void changeAgressive(AgressiveMode mode);

    /**
     * If this mode is enabled function recalculateGear has no power
     * Changing Gear can be done by function changeGear
     * @param enable
     */
    void changeManual(boolean enable);

    /**
     * If this mode is enabled and car is slipping recalculateGear has no power
     * @param enable
     */
    void changeMDynamics(boolean enable);

    /**
     * Change gearbox state
     * It is possible to change gearbox state in following scenarios:
     * state is Reverse and gear is set to -1
     * state is either Park or Neutral and gear is set to 0
     * state is Drive and gear is set to 1
     * @param state
     */
    void changeGearboxState(GearboxState state);

    /**
     * Change drive mode
     * @param driveMode
     */
    void changeDriveMode(DriveMode driveMode);

    /**
     * Get information about driver and gearbox
     * @return
     */
    String getInformation();

}
