package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants.ArmConstants;
import org.firstinspires.ftc.teamcode.Constants.FeederConstants;

public class FeederClimbSubsystem extends SubsystemBase {
    private final DcMotorEx feeder;
    private final DcMotorEx arm;
    private final AnalogInput armLimit;

    public FeederClimbSubsystem(HardwareMap hwMap) {
        feeder = hwMap.get(DcMotorEx.class, FeederConstants.MOTOR);
        arm = hwMap.get(DcMotorEx.class, ArmConstants.MOTOR);
        armLimit = hwMap.get(AnalogInput.class, ArmConstants.LIMIT_SENSOR);


        feeder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void runFeeder(double power) {
        feeder.setPower(power);
    }

    public void deployArm(double power) {
        arm.setPower(power);
    }

    public void stopArm() {
        arm.setPower(0);
    }

    public void stop() {
        feeder.setPower(0);
    }

    public double getArmVoltage() {
        return armLimit.getVoltage();
    }

 }
