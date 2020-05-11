package com.dsaczek.contest;

import com.dsaczek.contest.adapters.IExternal;
import com.dsaczek.contest.vars.AgressiveMode;
import com.dsaczek.contest.vars.DriveMode;
import com.dsaczek.contest.vars.GearboxState;
import com.dsaczek.contest.vars.RPM;
import com.dsaczek.contest.vars.Threshold;

public class Starter {
    public static void main(String[] args) {

        IExternal external = new ExternalForwarder();
        IGearboxDriver driver = new GearboxDriver(external);

        comment("Quick demonstration.");
        log(driver, external);

        comment("In Park mode gear position cannot be affected based on current RPM.");
        external.changeCurrentRPM(new RPM(3000d));
        driver.recalculateGear();
        log(driver, external);

        comment("Park->Drive.");
        driver.changeGearboxState(GearboxState.Drive);
        log(driver, external);

        comment("Auto gear change 3 times 1->4");
        external.changeCurrentRPM(new RPM(3000d));
        driver.recalculateGear();
        external.changeCurrentRPM(new RPM(3000d));
        driver.recalculateGear();
        external.changeCurrentRPM(new RPM(3000d));
        driver.recalculateGear();
        log(driver, external);

        comment("Comfort->Sport.");
        driver.changeDriveMode(DriveMode.Sport);
        log(driver, external);

        comment("3000RPM is to low to decrease gear for Sport.");
        driver.recalculateGear();
        log(driver, external);

        comment("6000RPM is enough to decrease gear for Sport.");
        external.changeCurrentRPM(new RPM(6000d));
        driver.recalculateGear();
        log(driver, external);

        comment("Agressive_1->Agressive3.");
        driver.changeAgressive(AgressiveMode.Agressive_3);
        log(driver, external);

        comment("6000RPM is to low to decrease gear for Sport in Agressive_3.");
        driver.recalculateGear();
        log(driver, external);

        comment("7000RPM is enough to decrease gear for Sport in Agressive_3.");
        external.changeCurrentRPM(new RPM(7000d));
        driver.recalculateGear();
        log(driver, external);

        comment("Heavy kickdown in Sport gear 6->4.");
        external.changeCurrentRPM(new RPM(4000d));
        external.setCurrentGas(new Threshold(0.8));
        driver.recalculateGear();
        log(driver, external);

        comment("Next kickdown not possible due high RPM.");
        external.changeCurrentRPM(new RPM(6000d));
        external.setCurrentGas(new Threshold(0.8));
        driver.recalculateGear();
        log(driver, external);

        comment("Kickdown in Sport gear 4->3.");
        external.changeCurrentRPM(new RPM(4000d));
        external.setCurrentGas(new Threshold(0.6));
        driver.recalculateGear();
        log(driver, external);

        comment("Sport->Comfort.");
        driver.changeDriveMode(DriveMode.Comfort);
        log(driver, external);

        comment("Only one possible gear jump for kickdown in Comfort gear 3->2.");
        external.setCurrentGas(new Threshold(0.8));
        driver.recalculateGear();
        log(driver, external);

        comment("Drive->Reverse fail.");
        driver.changeGearboxState(GearboxState.Reverse);
        log(driver, external);

        comment("In manual mode gear does not change automatically.");
        external.setCurrentGas(new Threshold(0.0));
        driver.changeManual(true);
        external.changeCurrentRPM(new RPM(7000d));
        driver.recalculateGear();
        log(driver, external);

        comment("Change gear manually 2->1.");
        driver.changeGear(false);
        driver.changeGear(false);
        driver.changeGear(false);
        log(driver, external);

        comment("Change gear manually 1->3.");
        driver.changeGear(true);
        driver.changeGear(true);
        log(driver, external);

        comment("Test mDynamic mode. Gear has not changed.");
        driver.changeManual(false);
        driver.changeMDynamics(true);
        driver.recalculateGear();
        log(driver, external);


        comment("Turn off mDynamics so gear has changed 3->4.");
        driver.changeMDynamics(false);
        driver.recalculateGear();
        log(driver, external);

        comment("Add car trailer and drive down.");
        external.changeCurrentRPM(new RPM(1000d));
        external.hitchCarTrailer(true);
        driver.recalculateGear();
        log(driver, external);

        comment("Lights does not support car slope so I have to simulate it 4->1.");
        external.setSimulateDriveDown(true);
        driver.recalculateGear();
        driver.recalculateGear();
        driver.recalculateGear();
        driver.recalculateGear();
        driver.recalculateGear();
        driver.recalculateGear();
        log(driver, external);

        comment("Drive->Reverse.");
        driver.changeGearboxState(GearboxState.Reverse);
        log(driver, external);

        comment("Reverse->Park.");
        driver.changeGearboxState(GearboxState.Park);
        log(driver, external);
        comment("Finish.");

    }

    static public void comment(String s){
        System.out.println(">Comment: " + s);
    }

    static public void log(IGearboxDriver driver, IExternal externals){
        System.out.println(" Driver: " + driver.getInformation());
        System.out.println(" Externals: " + externals.getInformation());
    }
}
