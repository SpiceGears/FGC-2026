package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants.ClutchConstants;

public class ClutchSubsystem extends SubsystemBase {

    public enum Mode {
        INTAKE,
        SHOOTER
    }

    private Mode currentMode;
    private final Servo servo;

    public ClutchSubsystem(HardwareMap hwMap) {
        servo = hwMap.get(Servo.class, ClutchConstants.SERVO);
        servo.setDirection(ClutchConstants.DIRECTION);

        setMode(Mode.INTAKE);
    }

    public void setMode(Mode mode) {
        currentMode = mode;

        switch (mode) {
            case INTAKE:
                servo.setPosition(ClutchConstants.INTAKE_POSITION);
                break;
            case SHOOTER:
                servo.setPosition(ClutchConstants.SHOOTER_POSITION);
                break;
        }
    }

    public Mode getCurrentMode() {
        return currentMode;
    }
}
