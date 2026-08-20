package org.firstinspires.ftc.teamcode;

public final class Constants {

    public static final class ShooterConstants {

        // Hardware
        public static final String SHOOTER_MOTOR = "shooter";
        public static final String FEEDER_MOTOR = "feeder";

        // Encoder
        public static final double TICKS_PER_REV = 28.0;
        public static final double GEAR_RATIO = 1.0;

        // Shooter
        public static final double TARGET_RPM = 4000.0;
        public static final double RPM_TOLERANCE = 100.0;

        // Velocity PIDF
        public static final double kP = 10.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double kF = 12.0;

        // Feeder
        public static final double FEEDER_POWER = 1.0;

        // Clutch
        public static final double SERVO_DISENGAGED = 0;
        public static final double SERVO_ENGAGED = 0;
        public static final double SPINUP_TIMEOUT = 1.2; // seconds

        private ShooterConstants() {}
    }

    private Constants() {}
}