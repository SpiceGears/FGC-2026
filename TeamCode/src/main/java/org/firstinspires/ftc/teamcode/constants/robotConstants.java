package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

public final class robotConstants {
    private robotConstants() {}

    @Config
    public static final class SuperStructure {

        public static final String M1 = "motor1";
        public static final String M2 = "motor2";
        public static final String M3 = "motor3";
        public static final String M4 = "motor4";

        public static double SHOOT_POWER = 1.0;
        public static double INTAKE_POWER = -1;
        public static double REVERSE_POWER = 1.0;
    }

    @Config
    public static final class Clutch {

        public static final String SERVO = "clutchServo";
        public static final String LEFT_SENSOR = "leftSensor";
        public static final String RIGHT_SENSOR = "rightSensor";

        public static double INTAKE_POWER = -0.5; // left
        public static double SHOOT_POWER = 0.5;   // right
        public static double BACKOFF_POWER = 0.2;

        public static double TIMEOUT_SEC = 3.5;
        public static double BACKOFF_TIME_SEC = 0.4;

        public static double SHOOT_POS = 0.0;
        public static double INTAKE_POS = 0.7;
    }

    @Config
    public static final class Feeder {

        public static final String MOTOR = "motor5";

        public static double FEED_POWER = 1.0;
        public static double UP_POWER = 1.0;
        public static double DOWN_POWER = -1.0;

        public static double FEEDER_THRESHOLD_RPM = 3000.0;
    }

    @Config
    public static final class Arm {

        public static final String MOTOR = "armMotor";
        public static final String POTENTIOMETER = "pot";

        public static double UP_POWER = 1;
        public static double DOWN_POWER = -1;
    }

    @Config
    public static final class Drive {

        public static final String LEFT = "leftDrive";
        public static final String RIGHT = "rightDrive";

        public static double THROTTLE_DEADBAND = 0.02;
        public static double WHEEL_DEADBAND = 0.02;

        public static double AUTO_QUICK_TURN_THROTTLE = 0.15;

        public static double CURVATURE_TURN_GAIN = 1.0;
        public static double CURVATURE_QUICK_TURN_GAIN = 0.7;

        public static double QUICK_TURN_THROTTLE_SCALE = 0.0;
    }
}
