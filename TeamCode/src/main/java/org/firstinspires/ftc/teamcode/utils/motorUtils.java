package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class motorUtils {
    public static double getRPM(DcMotorEx motor) {
        return motor != null ? motor.getVelocity() / 28.0 * 60.0 : 0;
    }

    public static double getRPM(MotorEx motor) {
        return motor != null ? getRPM(motor.motorEx) : 0;
    }
}
