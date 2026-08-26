package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Constants {

    @Configurable
    public static class OperatorConstants {
        public static double TRIGGER_THRESHOLD = 0.1;
        private OperatorConstants() {}
    }

    @Configurable
    public static class DriveConstants {
        public static String LEFT_MOTOR = "left_drive";
        public static String RIGHT_MOTOR = "right_drive";
        public static double STICK_DEADZONE = 0.05;
        public static DcMotor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR =
                DcMotor.ZeroPowerBehavior.BRAKE;
        public static DcMotorSimple.Direction RIGHT_MOTOR_DIRECTION =
                DcMotorSimple.Direction.REVERSE;
        private DriveConstants() {}
    }

    @Configurable
    public static class MechanismConstants {
        public static String MOTOR_1 = "structure_1";
        public static String MOTOR_2 = "structure_2";
        public static String MOTOR_3 = "structure_3";
        public static String MOTOR_4 = "structure_4";
        public static DcMotor.RunMode RUN_MODE = DcMotor.RunMode.RUN_USING_ENCODER;
        public static DcMotor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR =
                DcMotor.ZeroPowerBehavior.FLOAT;
        public static DcMotorSimple.Direction MOTOR_3_DIRECTION =
                DcMotorSimple.Direction.REVERSE;
        public static DcMotorSimple.Direction MOTOR_4_DIRECTION =
                DcMotorSimple.Direction.REVERSE;
        private MechanismConstants() {}
    }

    @Configurable
    public static class ShooterConstants {
        public static String SHOOTER_MOTOR = "shooter";
        public static String LEFT_SHOOTER_MOTOR = "leftShooter";
        public static String RIGHT_SHOOTER_MOTOR = "rightShooter";
        public static double TICKS_PER_REV = 28.0;
        public static double GEAR_RATIO = 1.0;
        public static double TARGET_RPM = 4000.0;
        public static double RPM_TOLERANCE = 100.0;
        public static double KP = 10.0;
        public static double KI = 0.0;
        public static double KD = 0.0;
        public static double KF = 12.0;
        public static double CLUTCH_DELAY_SECONDS = 0.25;
        public static DcMotor.RunMode RUN_MODE = DcMotor.RunMode.RUN_USING_ENCODER;
        public static DcMotor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR =
                DcMotor.ZeroPowerBehavior.FLOAT;
        public static DcMotorSimple.Direction RIGHT_MOTOR_DIRECTION =
                DcMotorSimple.Direction.REVERSE;
        private ShooterConstants() {}
    }

    @Configurable
    public static class IntakeConstants {
        public static double POWER = -1.0;
        public static double REVERSE_POWER = 1.0;
        public static double CLUTCH_DELAY_SECONDS = 0.25;
        private IntakeConstants() {}
    }

    @Configurable
    public static class FeederConstants {
        public static String MOTOR = "feeder";
        public static double POWER = 1.0;
        public static double REVERSE_POWER = -1.0;
        public static DcMotor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR =
                DcMotor.ZeroPowerBehavior.BRAKE;
        private FeederConstants() {}
    }

    @Configurable
    public static class ClimbConstants {
        public static double POWER = 1.0;
        public static double REVERSE_POWER = 1.0;
        private ClimbConstants() {}
    }

    @Configurable
    public static class ArmConstants {
        public static String MOTOR = "arm";
        public static String LIMIT_SENSOR = "potencjometr";
        public static double POWER = 1.0;
        public static double REVERSE_POWER = 1.0;
        private ArmConstants() {}
    }

    @Configurable
    public static class ClutchConstants {
        public static String SERVO = "clutch_servo";
        public static double INTAKE_POSITION = 0.6;
        public static double SHOOTER_POSITION = 0.0;
        public static Servo.Direction DIRECTION = Servo.Direction.FORWARD;
        private ClutchConstants() {}
    }

    @Configurable
    public static class TestConstants {
        public static double MOTOR_POWER = 1.0;
        private TestConstants() {}
    }

    private Constants() {}
}
