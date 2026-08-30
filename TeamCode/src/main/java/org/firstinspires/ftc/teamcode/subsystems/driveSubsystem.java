package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.drivebase.DifferentialDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog(postToFtcDashboard = false)
public class driveSubsystem extends SubsystemBase {
    private final MotorEx left, right;
    private final DifferentialDrive drive;

    public driveSubsystem(HardwareMap hwMap) {
        left = new MotorEx(hwMap, robotConstants.Drive.LEFT);
        right = new MotorEx(hwMap, robotConstants.Drive.RIGHT);

        left.setRunMode(Motor.RunMode.RawPower);
        right.setRunMode(Motor.RunMode.RawPower);
        left.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        drive = new DifferentialDrive(left, right);

    }

    public void arcadeDrive(double forward, double turn) {
        drive.arcadeDrive(forward, turn);
    }



    public void cheesyDrive(double throttle, double wheel, boolean isQuickTurn) {
        throttle = handleDeadband(throttle, robotConstants.Drive.THROTTLE_DEADBAND);
        wheel = handleDeadband(wheel, robotConstants.Drive.WHEEL_DEADBAND);

        boolean autoQuickTurn = Math.abs(throttle) < robotConstants.Drive.AUTO_QUICK_TURN_THROTTLE;
        boolean quickTurn = isQuickTurn || autoQuickTurn;

        if (isQuickTurn) {
            throttle *= robotConstants.Drive.QUICK_TURN_THROTTLE_SCALE;
        }

        double angularPower;
        if (quickTurn) {
            angularPower = wheel * robotConstants.Drive.CURVATURE_QUICK_TURN_GAIN;
        } else {
            angularPower = Math.abs(throttle) * wheel * robotConstants.Drive.CURVATURE_TURN_GAIN;
        }

        double leftPower = throttle + angularPower;
        double rightPower = throttle - angularPower;

        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }

        drive.tankDrive(leftPower, rightPower);
    }

    public void stop() {
        drive.stop();
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getLeftRpm() { return motorUtils.getRPM(left); }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getRightRpm() { return motorUtils.getRPM(right); }

    private static double handleDeadband(double value, double deadband) {
        return (Math.abs(value) > Math.abs(deadband)) ? value : 0.0;
    }
}
