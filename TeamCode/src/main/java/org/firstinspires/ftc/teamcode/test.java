package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants.DriveConstants;
import org.firstinspires.ftc.teamcode.Constants.TestConstants;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "MOTOR TEST")
public class test extends OpMode {

    private DcMotorEx motor1;
    private DcMotorEx motor2;

    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotorEx.class, DriveConstants.LEFT_MOTOR);
        motor2 = hardwareMap.get(DcMotorEx.class, DriveConstants.RIGHT_MOTOR);
    }

    @Override
    public void loop() {
        if (gamepad1.cross) {
            motor1.setPower(TestConstants.MOTOR_POWER);
            motor2.setPower(TestConstants.MOTOR_POWER);
        } else {
            motor1.setPower(0);
            motor2.setPower(0);
        }
    }
}
