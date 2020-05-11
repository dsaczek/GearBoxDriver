package com.dsaczek.contest.adapters;

import com.dsaczek.contest.libs.Gearbox;
import com.dsaczek.contest.vars.DriveMode;
import com.dsaczek.contest.vars.GearboxState;

import static com.dsaczek.contest.vars.GearboxState.Drive;
import static com.dsaczek.contest.vars.GearboxState.Neutral;
import static com.dsaczek.contest.vars.GearboxState.Park;
import static com.dsaczek.contest.vars.GearboxState.Reverse;

public class GearboxAdapter implements IGearbox {
    Gearbox gearbox;
    final private int Drive_State = 1;
    final private int Park_State = 2;
    final private int Reverse_State = 3;
    final private int Neutral_State = 4;

    public GearboxAdapter(Gearbox gearbox) {
        this.gearbox = gearbox;
    }

    @Override
    public boolean isInDriveState() {
        if ((Integer) gearbox.getState() == Drive_State) {
            return true;
        }
        return false;
    }

    @Override
    public void increaseGear() {
        Integer currentGear = (Integer) gearbox.getCurrentGear();
        if (gearbox.getMaxDrive() > currentGear) {
            gearbox.setCurrentGear(currentGear + 1);

        }
    }

    @Override
    public void decreaseGear() {
        Integer currentGear = (Integer) gearbox.getCurrentGear();
        if (1 < currentGear) {
            gearbox.setCurrentGear(currentGear - 1);
        }
    }

    @Override
    public void decreaseGearTwice() {
        Integer currentGear = (Integer) gearbox.getCurrentGear();
        if (2 < currentGear) {
            gearbox.setCurrentGear(currentGear - 2);
        } else if (1 < currentGear) {
            gearbox.setCurrentGear(currentGear - 1);
        }
    }

    /**
     * It has to repair bug in setGearBoxCurrentParams
     * It is important to provide proper gear in gearBoxCurrentParams[1]
     * @param state
     */
    @Override
    public void changeGearboxState(GearboxState state) {
        GearboxState currentState = getGearboxState();
        if (currentState != state)
        {
            Integer currentGear = (Integer) gearbox.getCurrentGear();
            if (currentGear == 0 && currentState == Park
                    || currentGear == 1 && currentState == Drive
                    || currentGear == 0 && currentState == Neutral
                    || currentGear == -1 && currentState == Reverse) {
                Object[] gearBoxParams = new Object[2];
                switch (state) {
                    case Drive: {
                        gearBoxParams[0] = Drive_State;
                        gearBoxParams[1] = 1;
                        break;
                    }
                    case Park: {
                        gearBoxParams[0] = Park_State;
                        gearBoxParams[1] = 0;
                        break;
                    }
                    case Reverse: {
                        gearBoxParams[0] = Reverse_State;
                        gearBoxParams[1] = -1;
                        break;
                    }
                    default: {
                        gearBoxParams[0] = Neutral_State;
                        gearBoxParams[1] = 0;

                        break;
                    }
                }
                gearbox.setGearBoxCurrentParams(gearBoxParams);
            }
        }
    }

    @Override
    public void configureGearbox(int gear) {
        if (gear < 1) {
            throw new IllegalArgumentException();
        } else {
            gearbox.setGearBoxCurrentParams(new Object[]{Park_State, 0});
            gearbox.setMaxDrive(gear);
        }
    }

    @Override
    public String getInformation() {
        return new String("state:" +getGearboxState().toString()+ ", gear:" + (Integer) gearbox.getCurrentGear());
    }

    private GearboxState getGearboxState() {
        Integer state = (Integer) gearbox.getState();
        switch (state) {
            case Drive_State: {
                return Drive;
            }
            case Park_State: {
                return Park;
            }
            case Reverse_State: {
                return Reverse;
            }
            case Neutral_State: {
                return Neutral;
            }
            default:{
                throw new IllegalStateException();
            }
        }
    }
}
