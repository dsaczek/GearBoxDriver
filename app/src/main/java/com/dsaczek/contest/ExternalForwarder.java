package com.dsaczek.contest;

import com.dsaczek.contest.adapters.ExternalAdapter;
import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.libs.ExternalSystems;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

public class ExternalForwarder implements IExternal {
    private IExternal externalAdapter;

    public ExternalForwarder() {
        this.externalAdapter = createExternalAdapter();
    }

    @Override
    public RPM getCurrentRPM() {
        return externalAdapter.getCurrentRPM();
    }

    @Override
    public Threshold getCurrentGas() {
        return externalAdapter.getCurrentGas();
    }

    @Override
    public void setCurrentGas(Threshold currentGas) {
        externalAdapter.setCurrentGas(currentGas);
    }

    @Override
    public void changeCurrentRPM(RPM currentRPM) {
        externalAdapter.changeCurrentRPM(currentRPM);
    }

    @Override
    public boolean isCarSlipping() {
        return externalAdapter.isCarSlipping();
    }

    @Override
    public boolean isCarDrivingDown() {
        return externalAdapter.isCarDrivingDown();
    }

    @Override
    public boolean isCarTrailer() {
        return externalAdapter.isCarTrailer();
    }

    @Override
    public void hitchCarTrailer(boolean enable) {
        externalAdapter.hitchCarTrailer(enable);
    }

    @Override
    public void setSimulateDriveDown(boolean simulateDriveDown) {
        externalAdapter.setSimulateDriveDown(simulateDriveDown);
    }

    @Override
    public String getInformation() {
        return externalAdapter.getInformation();
    }

    protected IExternal createExternalAdapter() {
        return new ExternalAdapter(new ExternalSystems());
    }
}
