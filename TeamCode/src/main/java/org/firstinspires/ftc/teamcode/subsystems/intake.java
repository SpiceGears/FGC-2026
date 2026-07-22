package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog
public class intake {
    private final LinearOpMode opMode;
    private DcMotorEx intake;
    public double intakePower = 0;

    public intake(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void init() {
        intake = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void IntakeForward() {
        intake.setPower(1);
        intakePower = 1;
    }

    public void IntakeReverse() {
        intake.setPower(-1);
        intakePower = -1;
    }


    @AutoLogOutput
    public double intakeRPM() {
        return motorUtils.getRPM(intake);
    }

    public void stop() {
        intake.setPower(0);
        intakePower = 0;
    }
}