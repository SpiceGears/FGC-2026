package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;


@AutoLog
public class drive {
    private final LinearOpMode opMode;
    private DcMotorEx leftDrive;
    private DcMotorEx rightDrive;



    private double speedModifier;

    public double leftPower = 0;
    public double rightPower = 0;

    public drive(LinearOpMode opMode) {
        this.opMode = opMode;

    }

    public void init() {
        leftDrive = opMode.hardwareMap.get(DcMotorEx.class, "left_drive");
        rightDrive = opMode.hardwareMap.get(DcMotorEx.class, "right_drive");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        speedModifier = 1.0;

    }

    public void drive(double drive, double turn) {
        leftPower = Range.clip(drive + turn, -1.0, 1.0) * speedModifier;
        rightPower = Range.clip(drive - turn, -1.0, 1.0) * speedModifier;

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }

    @AutoLogOutput
    public double leftDriveRPM() {
        return motorUtils.getRPM(leftDrive);
    }

    @AutoLogOutput
    public double rightDriveRPM() {
        return motorUtils.getRPM(rightDrive);
    }

    public void stop() {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        leftPower = 0;
        rightPower = 0;
    }

}
