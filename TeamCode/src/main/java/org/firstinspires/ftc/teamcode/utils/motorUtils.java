package org.firstinspires.ftc.teamcode.utils;

import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class motorUtils {
    public static double getRPM(MotorEx motor) {
        return motor != null ? motor.motorEx.getVelocity() / 28.0 * 60.0 : 0;
    }
}
