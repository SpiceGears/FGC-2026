package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

public class ClutchSubsystem extends SubsystemBase {

    public enum Mode {
        INTAKE,
        SHOOTER
    }

    private Mode currentMode;
    private final ServoEx servo1;
    private final ServoEx servo2;

    public ClutchSubsystem(HardwareMap hwMap) {
        servo1 = hwMap.get(ServoEx.class, "servo1");
        servo2 = hwMap.get(ServoEx.class, "servo2");

        setMode(Mode.SHOOTER);
    }

    public void setMode(Mode mode) {
        currentMode = mode;

        switch (mode) {
            case INTAKE:
                servo1.set(1);
                servo2.set(1);
                break;
            case SHOOTER:
                servo1.set(0);
                servo2.set(0);
                break;
        }
    }

    public Mode getCurrentMode() {
        return currentMode;
    }
}
