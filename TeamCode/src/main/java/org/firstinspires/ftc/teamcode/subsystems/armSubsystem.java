package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog(postToFtcDashboard = false)
public class armSubsystem extends SubsystemBase {
    private final MotorEx arm;

    public armSubsystem(HardwareMap hwMap) {
        arm = new MotorEx(hwMap, robotConstants.Arm.MOTOR);

        arm.setInverted(false);
        arm.setRunMode(Motor.RunMode.RawPower);
        arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power) { arm.set(power); }
    public int getPosition() { return arm.getCurrentPosition(); }

    public void stop() {
        arm.set(0.0);
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getArmRpm() { return motorUtils.getRPM(arm); }

    @Override
    public void periodic() {
    }
}
