package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

public final class armPIDF {
    private armPIDF() {}

    @Config
    public static final class Arm {

        public static double KP = 0.02;
        public static double KI = 0.0;
        public static double KD = 0.0;
        public static double KF = 0.0;

        public static double TOLERANCE_DEG = 0.2;

        public static double START_ANGLE = 0.0;
        public static double JOG_STEP_DEG = 1.0;

        public static double MIN_ANGLE = -45.0;
        public static double MAX_ANGLE = 90.0;
    }
}
