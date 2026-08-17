package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants;

public class ShooterSubsystem extends SubsystemBase {
    private final DcMotorEx rightShooter;
    private final DcMotorEx leftShooter;
    private final DcMotorEx feeder;

    public ShooterSubsystem(HardwareMap hwMap) {
        rightShooter = hwMap.get(DcMotorEx.class, "rightShooter");
        leftShooter = hwMap.get(DcMotorEx.class, "leftShooter");
        feeder = hwMap.get(DcMotorEx.class, "feeder");

        rightShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftShooter.setVelocityPIDFCoefficients(
                Constants.ShooterConstants.kP,
                Constants.ShooterConstants.kI,
                Constants.ShooterConstants.kD,
                Constants.ShooterConstants.kF
        );

        rightShooter.setVelocityPIDFCoefficients(
                Constants.ShooterConstants.kP,
                Constants.ShooterConstants.kI,
                Constants.ShooterConstants.kD,
                Constants.ShooterConstants.kF
        );

        rightShooter.setDirection(DcMotorSimple.Direction.REVERSE);

        feeder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
                getShooterRPM() - Constants.ShooterConstants.TARGET_RPM
        ) <= Constants.ShooterConstants.RPM_TOLERANCE;
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
        return rpm * Constants.ShooterConstants.TICKS_PER_REV / 60.0;
    }
    private double ticksPerSecondToRPM(double ticksPerSecond) {
        return ticksPerSecond * 60.0 / Constants.ShooterConstants.TICKS_PER_REV;
    }

}
