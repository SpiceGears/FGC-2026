package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

public final class flywheelPIDF {
    private flywheelPIDF() {}

    @Config
    public static final class Shooter {
        public static double KP = 0.035;
        public static double KI = 0;
        public static double KD = 0;
        public static double KF = 0.8;

        public static double TARGET_RPM = 4300;
        public static double RPM_TOLERANCE = 800;
        public static double FEEDER_RECOVERY_RPM = 3700;
        public static double FEED_LOCKOUT_SEC = 0.5;
        public static double SPINUP_RAMP_SEC = 0.75;

        public static double JAM_MIN_RPM = 1500;
        public static double JAM_TIMEOUT_SEC = 3.0;
        public static double JAM_REVERSE_POWER = 1.0;
    }
}