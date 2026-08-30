package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

public final class intakePIDF {
    private intakePIDF() {}

    @Config
    public static final class Intake {

        // TODO
        public static double KP = 0;
        public static double KI = 0;
        public static double KD = 0;
        public static double KF = 0;

        public static double TARGET_RPM = 2000;
        public static double RPM_TOLERANCE = 400;
    }
}
