package com.dsaczek.contest.adapters;

import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

public interface IExternal {
   RPM getCurrentRPM();
   Threshold getCurrentGas();
   void setCurrentGas(Threshold currentGas);
   void changeCurrentRPM(RPM currentRPM);
   boolean isCarSlipping();
   boolean isCarDrivingDown();
   boolean isCarTrailer();
   void hitchCarTrailer(boolean enable);
   void setSimulateDriveDown(boolean simulateDriveDown);
   String getInformation();
}
