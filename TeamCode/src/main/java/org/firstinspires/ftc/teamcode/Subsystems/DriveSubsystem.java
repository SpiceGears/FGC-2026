package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

    private final DcMotorEx leftDrive;
    private final DcMotorEx rightDrive;

    public DriveSubsystem(final HardwareMap hwMap) {
        leftDrive = hwMap.get(DcMotorEx.class, DriveConstants.LEFT_MOTOR);
        rightDrive = hwMap.get(DcMotorEx.class, DriveConstants.RIGHT_MOTOR);

        leftDrive.setZeroPowerBehavior(DriveConstants.ZERO_POWER_BEHAVIOR);
        rightDrive.setZeroPowerBehavior(DriveConstants.ZERO_POWER_BEHAVIOR);

        rightDrive.setDirection(DriveConstants.RIGHT_MOTOR_DIRECTION);
    }

    public void tankDrive(double forward, double turn) {
        double leftPower = Range.clip(forward + turn, -1.0, 1.0);
        double rightPower = Range.clip(forward - turn, -1.0, 1.0);

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }

    public void stop() {
        tankDrive(0, 0);
    }
}
