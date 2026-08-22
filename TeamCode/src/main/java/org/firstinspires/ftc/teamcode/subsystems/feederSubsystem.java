package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.constants.robotConstants;

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

    @Override
    public void periodic() {
    }
}
