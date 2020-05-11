# GearBoxDriver

In order to understand gearbox driver features please go to IGearboxDriver.java.  

A quick demonstration for a gearbox driver is placed in Starter.java file. This produces following output:

```
>Comment: Quick demonstration.
 Driver: GearBox:{state:Park, gear:0} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_1} 
 Externals: RPM:0.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: In Park mode gear position cannot be affected based on current RPM.
 Driver: GearBox:{state:Park, gear:0} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_1} 
 Externals: RPM:3000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Park->Drive.
 Driver: GearBox:{state:Drive, gear:1} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_1} 
 Externals: RPM:3000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Auto gear change 3 times 1->4
 Driver: GearBox:{state:Drive, gear:4} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_1} 
 Externals: RPM:3000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Comfort->Sport.
 Driver: GearBox:{state:Drive, gear:4} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_1} 
 Externals: RPM:3000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: 3000RPM is to low to decrease gear for Sport.
 Driver: GearBox:{state:Drive, gear:4} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_1} 
 Externals: RPM:3000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: 6000RPM is enough to decrease gear for Sport.
 Driver: GearBox:{state:Drive, gear:5} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_1} 
 Externals: RPM:6000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Agressive_1->Agressive3.
 Driver: GearBox:{state:Drive, gear:5} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:6000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: 6000RPM is to low to decrease gear for Sport in Agressive_3.
 Driver: GearBox:{state:Drive, gear:5} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:6000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: 7000RPM is enough to decrease gear for Sport in Agressive_3.
 Driver: GearBox:{state:Drive, gear:6} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:7000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Heavy kickdown in Sport gear 6->4.
 Driver: GearBox:{state:Drive, gear:4} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:4000.0, gas:0.8, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Next kickdown not possible due high RPM.
 Driver: GearBox:{state:Drive, gear:4} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:6000.0, gas:0.8, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Kickdown in Sport gear 4->3.
 Driver: GearBox:{state:Drive, gear:3} Drive:{mode:Sport, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:4000.0, gas:0.6, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Sport->Comfort.
 Driver: GearBox:{state:Drive, gear:3} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:4000.0, gas:0.6, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Only one possible gear jump for kickdown in Comfort gear 3->2.
 Driver: GearBox:{state:Drive, gear:2} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:4000.0, gas:0.8, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Drive->Reverse fail.
 Driver: GearBox:{state:Drive, gear:2} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:4000.0, gas:0.8, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: In manual mode gear does not change automatically.
 Driver: GearBox:{state:Drive, gear:2} Drive:{mode:Comfort, manual:true, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:7000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Change gear manually 2->1.
 Driver: GearBox:{state:Drive, gear:1} Drive:{mode:Comfort, manual:true, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:7000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Change gear manually 1->3.
 Driver: GearBox:{state:Drive, gear:3} Drive:{mode:Comfort, manual:true, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:7000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Test mDynamic mode. Gear has not changed.
 Driver: GearBox:{state:Drive, gear:3} Drive:{mode:Comfort, manual:false, mDynamics:true, agressive:Agressive_3} 
 Externals: RPM:7000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Turn off mDynamics so gear has changed 3->4.
 Driver: GearBox:{state:Drive, gear:4} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:7000.0, gas:0.0, carTrailer:false, isCarDrivingDown: false, isCarSlipping: true
>Comment: Add car trailer and drive down.
 Driver: GearBox:{state:Drive, gear:4} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:1000.0, gas:0.0, carTrailer:true, isCarDrivingDown: false, isCarSlipping: true
>Comment: Lights does not support car slope so I have to simulate it 4->1.
 Driver: GearBox:{state:Drive, gear:1} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:1000.0, gas:0.0, carTrailer:true, isCarDrivingDown: true, isCarSlipping: true
>Comment: Drive->Reverse.
 Driver: GearBox:{state:Reverse, gear:-1} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:1000.0, gas:0.0, carTrailer:true, isCarDrivingDown: true, isCarSlipping: true
>Comment: Reverse->Park.
 Driver: GearBox:{state:Park, gear:0} Drive:{mode:Comfort, manual:false, mDynamics:false, agressive:Agressive_3} 
 Externals: RPM:1000.0, gas:0.0, carTrailer:true, isCarDrivingDown: true, isCarSlipping: true
>Comment: Finish.
```
