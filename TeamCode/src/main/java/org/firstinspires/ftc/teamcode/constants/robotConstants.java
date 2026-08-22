package org.firstinspires.ftc.teamcode.constants;

public final class robotConstants {
    private robotConstants() {}

    public static final class SuperStructure {

        public static final String M1 = "motor1";
        public static final String M2 = "motor2";
        public static final String M3 = "motor3";
        public static final String M4 = "motor4";

        public static final double SHOOT_POWER = 1.0;
        public static final double INTAKE_POWER = 1.0;
    }

    public static final class Clutch {

        public static final String SERVO = "clutchServo";

        public static final double SHOOT = 0.0;
        public static final double INTAKE = 1.0;
    }

    public static final class Feeder {

        public static final String MOTOR = "motor5";

        public static final double FEED_POWER = 1.0;
        public static final double UP_POWER = 1.0;
        public static final double DOWN_POWER = -1.0;
    }

    public static final class Arm {

        public static final String MOTOR = "armMotor";

        public static final double UP_POWER = 0.6;
        public static final double DOWN_POWER = -0.4;
    }

    public static final class Drive {

        public static final String LEFT = "leftDrive";
        public static final String RIGHT = "rightDrive";
    }
}
