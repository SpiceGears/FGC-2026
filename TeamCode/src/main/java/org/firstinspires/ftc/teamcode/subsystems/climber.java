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
public class climber {
    private final LinearOpMode opMode;
    private DcMotorEx climber;
    public double climberPower = 0;

    public climber(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void init() {
        climber = opMode.hardwareMap.get(DcMotorEx.class, "climber");
        climber.setDirection(DcMotorSimple.Direction.FORWARD);
        climber.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        climber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void climberForward() {
        climber.setPower(1);
        climberPower = 1;
    }

    public void climberReverse() {

        climber.setPower(-1);
        climberPower = -1;
    }

    @AutoLogOutput
    public double getClimberRPM() {
        return motorUtils.getRPM(climber);
    }


    public void stop() {
        climber.setPower(0);
        climberPower = 0;
    }
}