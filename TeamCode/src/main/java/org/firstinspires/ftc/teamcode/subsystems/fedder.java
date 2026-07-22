package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog
public class fedder {
    private final LinearOpMode opMode;
    private DcMotorEx fedder;
    public double fedderPower = 0;

    public fedder(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void init() {
        fedder = opMode.hardwareMap.get(DcMotorEx.class, "fedder");
        fedder.setDirection(DcMotorSimple.Direction.FORWARD);
        fedder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fedder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void FedderForward() {
        fedder.setPower(1);
        fedderPower = 1;
    }

    public void FedderReverse() {

        fedder.setPower(-1);
        fedderPower = -1;
    }


    @AutoLogOutput
    public double fedderRPM() {
        return motorUtils.getRPM(fedder);
    }

    public void stop() {

        fedder.setPower(0);
        fedderPower = 0;
    }
}