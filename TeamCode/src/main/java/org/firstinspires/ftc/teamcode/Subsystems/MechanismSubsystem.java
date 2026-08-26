package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants.MechanismConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;

public class MechanismSubsystem extends SubsystemBase {
    private final DcMotorEx motor1;
    private final DcMotorEx motor2;
    private final DcMotorEx motor3;
    private final DcMotorEx motor4;
    private final DcMotorEx[] motors;

    public MechanismSubsystem(HardwareMap hwMap) {
        motor1 = hwMap.get(DcMotorEx.class, MechanismConstants.MOTOR_1);
        motor2 = hwMap.get(DcMotorEx.class, MechanismConstants.MOTOR_2);
        motor3 = hwMap.get(DcMotorEx.class, MechanismConstants.MOTOR_3);
        motor4 = hwMap.get(DcMotorEx.class, MechanismConstants.MOTOR_4);

        motors = new DcMotorEx[]{motor1, motor2, motor3, motor4};

        for (DcMotorEx motor : motors) {
            motor.setMode(MechanismConstants.RUN_MODE);
            motor.setZeroPowerBehavior(MechanismConstants.ZERO_POWER_BEHAVIOR);
            motor.setVelocityPIDFCoefficients(
                    ShooterConstants.KP,
                    ShooterConstants.KI,
                    ShooterConstants.KD,
                    ShooterConstants.KF);
        }

        motor4.setDirection(MechanismConstants.MOTOR_4_DIRECTION);
        motor3.setDirection(MechanismConstants.MOTOR_3_DIRECTION);
    }

    public void setPower(double power) {
        for (DcMotorEx motor : motors) {
            motor.setPower(power);
        }
    }

    public void setShooterRPM(double rpm) {
        double ticksPerSecond = rpmToTicksPerSecond(rpm);
        for (DcMotorEx motor : motors) {
            motor.setVelocity(ticksPerSecond);
        }
    }

    public boolean atShooterSpeed() {
        for (DcMotorEx motor : motors) {
            double vel = motor.getVelocity();
            double rpm = Math.abs(ticksPerSecondToRPM(vel));
            double diff = Math.abs(rpm - ShooterConstants.TARGET_RPM);

            if (diff > ShooterConstants.RPM_TOLERANCE) {
                return false;
            }
        }

        return true;
    }

    public double getShooterRPM() {
        double rpm = 0;
        for (DcMotorEx motor: motors) {
            rpm += Math.abs(ticksPerSecondToRPM(motor.getVelocity()));
        }
        return rpm/motors.length;
    }

    private double rpmToTicksPerSecond(double rpm) {
        double rps = rpm / 60;
        return rps * ShooterConstants.TICKS_PER_REV * ShooterConstants.GEAR_RATIO;
    }

    private double ticksPerSecondToRPM(double ticksPerSecond) {
        double rps = ticksPerSecond
                / (ShooterConstants.TICKS_PER_REV * ShooterConstants.GEAR_RATIO);
        return rps * 60;
    }

    public void stop() {
        setPower(0);
    }
}
