package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.shooter;

public class motorUtils {
    public static double getRPM(DcMotorEx motor) {
        return motor != null ? motor.getVelocity() / 28.0 * 60.0 : 0;
    }

    public boolean shooterTarget = false;

}