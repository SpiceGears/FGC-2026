package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

    private final DcMotorEx leftDrive;
    private final DcMotorEx rightDrive;

    public DriveSubsystem(final HardwareMap hwMap) {
        leftDrive = hwMap.get(DcMotorEx.class, "leftDrive");
        rightDrive = hwMap.get(DcMotorEx.class, "rightDrive");

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void tankDrive(double left, double right) {
        leftDrive.setPower(left);
        rightDrive.setPower(right);
    }

    public void stop() {
        tankDrive(0, 0);
    }
}