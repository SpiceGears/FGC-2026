package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants;

public class MechanismSubsystem extends SubsystemBase {
    private final DcMotorEx motor1;
    private final DcMotorEx motor2;
    private final DcMotorEx motor3;
    private final DcMotorEx motor4;
    private final DcMotorEx[] motors;

    public MechanismSubsystem(HardwareMap hwMap) {
        motor1 = hwMap.get(DcMotorEx.class, "motor1");
        motor2 = hwMap.get(DcMotorEx.class, "motor2");
        motor3 = hwMap.get(DcMotorEx.class, "motor3");
        motor4 = hwMap.get(DcMotorEx.class, "motor4");

        motors = new DcMotorEx[]{motor1, motor2, motor3, motor4};

        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motor.setVelocityPIDFCoefficients(
                    Constants.ShooterConstants.kP,
                    Constants.ShooterConstants.kI,
                    Constants.ShooterConstants.kD,
                    Constants.ShooterConstants.kF);
        }

        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor3.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double power) {
        for (DcMotorEx motor : motors) {
            motor.setPower(power);
        }
    }

    public void runIntake(double power) {
        motor1.setPower(power);
        motor2.setPower(power);
        motor3.setPower(0);
        motor4.setPower(0);
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
            double diff = Math.abs(rpm - Constants.ShooterConstants.TARGET_RPM);

            if (diff > Constants.ShooterConstants.RPM_TOLERANCE) {
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
        return rps * Constants.ShooterConstants.TICKS_PER_REV;
    }

    private double ticksPerSecondToRPM(double ticksPerSecond) {
        double rps = ticksPerSecond / Constants.ShooterConstants.TICKS_PER_REV;
        return rps * 60;
    }

    public void stop() {
        setPower(0);
    }
}
