package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.constants.robotConstants;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog(postToFtcDashboard = false)
public class clutchServoSubsystem extends SubsystemBase {
    private final Servo clutch;

    public clutchServoSubsystem(HardwareMap hwMap) {
        clutch = hwMap.get(Servo.class, robotConstants.Clutch.SERVO);
        shoot();
    }

    public void intake() { setPosition(robotConstants.Clutch.INTAKE_POS); }
    public void shoot() { setPosition(robotConstants.Clutch.SHOOT_POS); }

    public void setPosition(double position) {
        clutch.setPosition(position);
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getPosition() {
        return clutch.getPosition();
    }

    public void stop() {
        shoot();
    }

    @Override
    public void periodic() {
    }
}
