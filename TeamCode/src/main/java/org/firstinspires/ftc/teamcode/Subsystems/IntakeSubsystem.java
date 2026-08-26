package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants.FeederConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final DcMotorEx shooter;
    private final DcMotorEx feeder;

    public IntakeSubsystem(HardwareMap hwMap) {
        shooter = hwMap.get(DcMotorEx.class, ShooterConstants.SHOOTER_MOTOR);
        feeder = hwMap.get(DcMotorEx.class, FeederConstants.MOTOR);

        shooter.setMode(ShooterConstants.RUN_MODE);
        shooter.setZeroPowerBehavior(ShooterConstants.ZERO_POWER_BEHAVIOR);

        feeder.setZeroPowerBehavior(FeederConstants.ZERO_POWER_BEHAVIOR);
    }

    public void setShooterRPM(double rpm) {
        double ticksPerSecond = rpmToTicksPerSecond(rpm);
        shooter.setVelocity(ticksPerSecond);
    }

    public double getShooterRPM() {
        return ticksPerSecondToRPM(shooter.getVelocity());
    }

    public boolean atSpeed(double targetRPM, double toleranceRPM) {
        return Math.abs(getShooterRPM() - targetRPM) <= toleranceRPM;
    }

    public void stopShooter() {
        shooter.setVelocity(0);
    }

    public void setFeederPower(double power) {
        feeder.setPower(power);
    }

    public void stopFeeder() {
        feeder.setPower(0);
    }

    private double rpmToTicksPerSecond(double rpm) {
        return rpm * ShooterConstants.TICKS_PER_REV * ShooterConstants.GEAR_RATIO / 60.0;
    }
    private double ticksPerSecondToRPM(double ticksPerSecond) {
        return ticksPerSecond * 60.0
                / (ShooterConstants.TICKS_PER_REV * ShooterConstants.GEAR_RATIO);
    }
}
