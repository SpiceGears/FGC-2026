package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog
public class shooter {
    private final LinearOpMode opMode;
    private DcMotorEx shooter;
    private DcMotorEx shooter2;
    public double shooterPower = 0;

    public shooter(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void init() {
        shooter  = opMode.hardwareMap.get(DcMotorEx.class, "shooter");
        shooter2 = opMode.hardwareMap.get(DcMotorEx.class, "shooter2");

        shooter.setDirection(DcMotor.Direction.REVERSE);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shooter2.setDirection(DcMotor.Direction.FORWARD);
        shooter2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void ShooterForward() {
        shooter.setPower(1);
        shooter2.setPower(1);
        shooterPower = 1;
    }

    public void ShooterReverse() {
        shooter.setPower(-1);
        shooter2.setPower(-1);
        shooterPower = -1;
    }

    @AutoLogOutput
    public double getShooterRPM() {
        return motorUtils.getRPM(shooter);
    }

    @AutoLogOutput
    public double getShooter2RPM() {
        return motorUtils.getRPM(shooter2);
    }

    public void stop() {
        shooter.setPower(0);
        shooter2.setPower(0);
        shooterPower = 0;
    }
}
