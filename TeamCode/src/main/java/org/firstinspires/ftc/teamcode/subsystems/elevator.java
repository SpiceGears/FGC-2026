package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog
public class elevator {
    private final LinearOpMode opMode;
    private DcMotorEx elevator;
    public double elevatorPower = 0;

    public elevator(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void init() {
        elevator = opMode.hardwareMap.get(DcMotorEx.class, "elevator");
        elevator.setDirection(DcMotorSimple.Direction.FORWARD);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void elevatorForward() {
        elevator.setPower(1);
        elevatorPower = 1;
    }

    public void elevatorReverse() {
        elevator.setPower(-1);
        elevatorPower = -1;
    }

    @AutoLogOutput
    public double getElevatorRPM() {
        return motorUtils.getRPM(elevator);
    }



    public void stop() {
        elevator.setPower(0);
        elevatorPower = 0;
    }
}