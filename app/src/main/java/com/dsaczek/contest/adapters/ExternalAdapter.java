package com.dsaczek.contest.adapters;

import com.dsaczek.contest.libs.Lights;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;
import com.dsaczek.contest.libs.ExternalSystems;

public class ExternalAdapter implements IExternal {

    private Threshold currentGas = new Threshold(0);
    private boolean carTrailer = false;

    @Override
    public void setSimulateDriveDown(boolean simulateDriveDown) {
        this.simulateDriveDown = simulateDriveDown;
    }

    private boolean simulateDriveDown = false;
    private ExternalSystems externalSystems;

    @Override
    public void setCurrentGas(Threshold currentGas) {
        this.currentGas = currentGas;
    }

    @Override
    public void changeCurrentRPM(RPM currentRPM) {
        externalSystems.setCurrentRpm(currentRPM.getData());
    }

    @Override
    public Threshold getCurrentGas() {
        return currentGas;
    }

    @Override
    public boolean isCarSlipping() {
        return externalSystems.getAngularSpeed() > 50;
    }

    @Override
    public boolean isCarDrivingDown() {
        if (simulateDriveDown) return true;

        Lights lights = externalSystems.getLights();
        if (lights == null) {
            return false;
        }
        Integer position = lights.getLightsPosition();
        if (position != null) {
            if (position > 0 && position <= 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCarTrailer() {
        return carTrailer;
    }

    @Override
    public void hitchCarTrailer(boolean enable) {
        carTrailer = enable;
    }

    @Override
    public String getInformation() {
        return new String("RPM:" + Double.toString(externalSystems.getCurrentRpm())+ ", gas:" + currentGas.toString() +", carTrailer:"+ carTrailer+", isCarDrivingDown: "+isCarDrivingDown() + ", isCarSlipping: " + isCarSlipping());
    }


    public ExternalAdapter(ExternalSystems externals) {
        this.externalSystems = externals;
    }

    @Override
    public RPM getCurrentRPM() {
        return new RPM(externalSystems.getCurrentRpm());
    }
}
