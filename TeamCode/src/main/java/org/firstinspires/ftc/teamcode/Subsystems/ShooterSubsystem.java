package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants.FeederConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private final DcMotorEx rightShooter;
    private final DcMotorEx leftShooter;
    private final DcMotorEx feeder;

    public ShooterSubsystem(HardwareMap hwMap) {
        rightShooter = hwMap.get(DcMotorEx.class, ShooterConstants.RIGHT_SHOOTER_MOTOR);
        leftShooter = hwMap.get(DcMotorEx.class, ShooterConstants.LEFT_SHOOTER_MOTOR);
        feeder = hwMap.get(DcMotorEx.class, FeederConstants.MOTOR);

        rightShooter.setMode(ShooterConstants.RUN_MODE);
        leftShooter.setMode(ShooterConstants.RUN_MODE);
        rightShooter.setZeroPowerBehavior(ShooterConstants.ZERO_POWER_BEHAVIOR);
        leftShooter.setZeroPowerBehavior(ShooterConstants.ZERO_POWER_BEHAVIOR);

        leftShooter.setVelocityPIDFCoefficients(
                ShooterConstants.KP,
                ShooterConstants.KI,
                ShooterConstants.KD,
                ShooterConstants.KF
        );

        rightShooter.setVelocityPIDFCoefficients(
                ShooterConstants.KP,
                ShooterConstants.KI,
                ShooterConstants.KD,
                ShooterConstants.KF
        );

        rightShooter.setDirection(ShooterConstants.RIGHT_MOTOR_DIRECTION);

        feeder.setZeroPowerBehavior(FeederConstants.ZERO_POWER_BEHAVIOR);
    }

    public void setShooterRPM(double rpm) {
        double ticksPerSecond = rpmToTicksPerSecond(rpm);
        rightShooter.setVelocity(ticksPerSecond);
        leftShooter.setVelocity(ticksPerSecond);
    }

    public double getShooterRPM() {
        double leftRPM = ticksPerSecondToRPM(
                Math.abs(leftShooter.getVelocity())
        );

        double rightRPM = ticksPerSecondToRPM(
                Math.abs(rightShooter.getVelocity())
        );

        return (leftRPM + rightRPM) / 2.0;
    }

    public boolean atSpeed() {
        return Math.abs(
                getShooterRPM() - ShooterConstants.TARGET_RPM
        ) <= ShooterConstants.RPM_TOLERANCE;
    }

    public void stopShooter() {
        rightShooter.setVelocity(0);
        leftShooter.setVelocity(0);
    }

    public void setFeederPower(double power) {
        feeder.setPower(power);
    }

    public void stopFeeder() {
        feeder.setPower(0);
    }

    public void stop() {
        stopShooter();
        stopFeeder();
    }

    private double rpmToTicksPerSecond(double rpm) {
        return rpm * ShooterConstants.TICKS_PER_REV * ShooterConstants.GEAR_RATIO / 60.0;
    }
    private double ticksPerSecondToRPM(double ticksPerSecond) {
        return ticksPerSecond * 60.0
                / (ShooterConstants.TICKS_PER_REV * ShooterConstants.GEAR_RATIO);
    }

}
