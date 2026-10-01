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
public class feederSubsystem extends SubsystemBase {
    private final MotorEx feeder;

    public feederSubsystem(HardwareMap hwMap) {
        feeder = new MotorEx(hwMap, robotConstants.Feeder.MOTOR);

        feeder.setInverted(true);
        feeder.setRunMode(Motor.RunMode.RawPower);
        feeder.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power) { feeder.set(power); }
    public void feed() { feeder.set(robotConstants.Feeder.FEED_POWER); }

    public void stop() {
        feeder.set(0.0);
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getFeederRpm() { return motorUtils.getRPM(feeder); }

    @Override
    public void periodic() {
    }
}
