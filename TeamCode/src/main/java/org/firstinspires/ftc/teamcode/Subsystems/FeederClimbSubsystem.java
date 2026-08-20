package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class FeederClimbSubsystem extends SubsystemBase {
    private final DcMotorEx feeder;
    private final DcMotorEx arm;
    private final DigitalChannel armLimit;

    public FeederClimbSubsystem(HardwareMap hwMap) {
        feeder = hwMap.get(DcMotorEx.class, "feeder");
        arm = hwMap.get(DcMotorEx.class, "arm");
        armLimit = hwMap.get(DigitalChannel.class, "armLimit");
        armLimit.setMode(DigitalChannel.Mode.INPUT);
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

    public boolean isArmLimit() {
        return !armLimit.getState();
    }
 }
