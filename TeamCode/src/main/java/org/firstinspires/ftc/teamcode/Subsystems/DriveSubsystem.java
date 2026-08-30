package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants.DriveConstants;
import org.firstinspires.ftc.teamcode.Robot;

public class DriveSubsystem extends SubsystemBase {

    private final Robot robot = Robot.getInstance();

    public DriveSubsystem() {

    }

    public void tankDrive(double forward, double turn) {
        double leftPower = Range.clip(forward + turn, -1.0, 1.0);
        double rightPower = Range.clip(forward - turn, -1.0, 1.0);

        robot.leftDrive.set(leftPower);
        robot.rightDrive.set(rightPower);
    }

    public void stop() {
        robot.leftDrive.stopMotor();
        robot.rightDrive.stopMotor();
    }
}
