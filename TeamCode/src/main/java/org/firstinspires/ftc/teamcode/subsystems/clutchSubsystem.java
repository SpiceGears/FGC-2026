package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.constants.robotConstants;

public class clutchSubsystem extends SubsystemBase {
    private final Servo clutch;

    public clutchSubsystem(HardwareMap hwMap) {
        clutch = hwMap.get(Servo.class, robotConstants.Clutch.SERVO);
        shoot();
    }

    public void intake() { setPosition(robotConstants.Clutch.INTAKE); }
    public void shoot() { setPosition(robotConstants.Clutch.SHOOT); }

    public void setPosition(double position) {
        clutch.setPosition(position);
    }

    public void stop() {
        shoot();
    }

    @Override
    public void periodic() {
    }
}
