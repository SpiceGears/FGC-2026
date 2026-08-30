package org.firstinspires.ftc.teamcode.Subsystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Robot;

public class ArmSubsystem extends SubsystemBase {
    private final Robot robot = Robot.getInstance();

    public enum State {
        STOP,
        UP,
        DOWN
    }

    private State currentState = State.STOP;

    public ArmSubsystem() {
    }

    public void setState(State state) {
        if (currentState == state) {
            return;
        }

        switch (state) {
            case STOP:
                robot.armMotor.set(0.0);
                break;

            case UP:
                robot.armMotor.set(1.0);
                break;

            case DOWN:
                robot.armMotor.set(-1.0);
                break;
        }

        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

    public double getPotentiometerVoltage() {
        return robot.armPotentiometer.getVoltage();
    }

    public void stop() {
        setState(State.STOP);
    }
}
