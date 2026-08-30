package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

public final class flywheelPIDF {
    private flywheelPIDF() {}

    @Config
    public static final class Shooter {
        public static final double CPR = 0;
        public static final double RPM = 0;

        public static double KP = 0.035;
        public static double KI = 0;
        public static double KD = 0;
        public static double KF = 0.8;

        public static double TARGET_RPM = 2000.0;
        public static double RPM_TOLERANCE = 200.0;
    }
}