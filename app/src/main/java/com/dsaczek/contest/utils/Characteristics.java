package com.dsaczek.contest.utils;

public class Characteristics {
    static private Object[] characteristics = new Object[]{
            2000d, 1000d,
            1000d, 0.5d, 2500d, 4500d,
            1500d, 0.5d, 5000d, 0.7d, 5000d, 5000d,
            1500d,
            2000d,
            3000d,
            6500d};

    public static final Double ECO_RPM_INCREASE_IN_ACCELERATION = get(0);
    public static final Double ECO_RPM_DECREASE_IN_ACCELERATION = get(1);

    public static final Double COMFORT_RPM_DECREASE_IN_ACCELERATION = get(2);
    public static final Double COMFORT_KICKDOWN_THRESHOLD = get(3);
    public static final Double COMFORT_RPM_INCREASE_IN_ACCELERATION = get(4);
    public static final Double COMFORT_RPM_DECREASE_IN_KICKDOWN = get(5);

    public static final Double SPORT_RPM_DECREASE_IN_ACCELERATION = get(6);
    public static final Double SPORT_KICKDOWN_THRESHOLD = get(7);
    public static final Double SPORT_RPM_INCREASE_IN_ACCELERATION = get(8);
    public static final Double SPORT_HEAVY_KICKDOWN_THRESHOLD = get(9);
    public static final Double SPORT_RPM_DECREASE_IN_KICKDOWN = get(10);
    public static final Double SPORT_RPM_DECREASE_IN_HEAVY_KICKDOWN = get(11);

    public static final Double ECO_RPM_DECREASE_IN_BRAKING = get(12);
    public static final Double COMFORT_RPM_DECREASE_IN_BRAKING = get(13);
    public static final Double SPORT_RPM_DECREASE_IN_BRAKING = get(14);

    static private Double get(int idx)
    {
        if ( idx >= characteristics.length) {
            throw new IndexOutOfBoundsException();
        }
        return (Double) characteristics[idx];
    }

}
