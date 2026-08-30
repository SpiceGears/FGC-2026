package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.drivebase.DifferentialDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.constants.visionPIDF;

public class driveSubsystem extends SubsystemBase {
    private final MotorEx left, right;
    private final DifferentialDrive drive;
    private final PIDFController headingPidf;
    private final PIDFController distancePidf;

    public driveSubsystem(HardwareMap hwMap) {
        left = new MotorEx(hwMap, robotConstants.Drive.LEFT);
        right = new MotorEx(hwMap, robotConstants.Drive.RIGHT);

        left.setRunMode(Motor.RunMode.RawPower);
        right.setRunMode(Motor.RunMode.RawPower);
        left.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        drive = new DifferentialDrive(left, right);

        headingPidf = new PIDFController(visionPIDF.Heading.KP, visionPIDF.Heading.KI,
                visionPIDF.Heading.KD, visionPIDF.Heading.KF);
        headingPidf.setSetPoint(0.0);
        headingPidf.setTolerance(visionPIDF.Heading.TOLERANCE_DEG);

        distancePidf = new PIDFController(visionPIDF.Distance.KP, visionPIDF.Distance.KI,
                visionPIDF.Distance.KD, visionPIDF.Distance.KF);
        distancePidf.setTolerance(visionPIDF.Distance.TOLERANCE_IN);
    }

    public void arcadeDrive(double forward, double turn) {
        drive.arcadeDrive(forward, turn);
    }

    public void alignToTag(double yawErrorDeg, double rangeIn) {
        headingPidf.setPIDF(visionPIDF.Heading.KP, visionPIDF.Heading.KI, visionPIDF.Heading.KD, visionPIDF.Heading.KF);
        headingPidf.setTolerance(visionPIDF.Heading.TOLERANCE_DEG);
        distancePidf.setPIDF(visionPIDF.Distance.KP, visionPIDF.Distance.KI, visionPIDF.Distance.KD, visionPIDF.Distance.KF);
        distancePidf.setTolerance(visionPIDF.Distance.TOLERANCE_IN);
        distancePidf.setSetPoint(visionPIDF.Distance.TARGET_DISTANCE_IN);

        double turn = -headingPidf.calculate(yawErrorDeg) * (visionPIDF.Heading.INVERT_TURN ? -1 : 1);
        double forward = -distancePidf.calculate(rangeIn) * (visionPIDF.Distance.INVERT_FORWARD ? -1 : 1);
        arcadeDrive(forward, turn);
    }

    public boolean isAligned() {
        return headingPidf.atSetPoint() && distancePidf.atSetPoint();
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

    private static double handleDeadband(double value, double deadband) {
        return (Math.abs(value) > Math.abs(deadband)) ? value : 0.0;
    }
}
