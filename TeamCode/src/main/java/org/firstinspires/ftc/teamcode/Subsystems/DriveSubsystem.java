package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    private final DcMotorEx leftFront, leftBack, rightFront, rightBack;

    public DriveSubsystem(final HardwareMap hwMap) {
        leftFront = hwMap.get(DcMotorEx.class, "leftFront");
        leftBack = hwMap.get(DcMotorEx.class, "leftBack");
        rightFront = hwMap.get(DcMotorEx.class, "rightFront");
        rightBack = hwMap.get(DcMotorEx.class, "rightBack");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void tankDrive(double left, double right) {
        leftFront.setPower(left);
        leftBack.setPower(left);

        rightFront.setPower(right);
        rightBack.setPower(right);
    }

    public void stop() {
        tankDrive(0, 0);
    }
}