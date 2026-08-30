package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants.ArmConstants;
import org.firstinspires.ftc.teamcode.Constants.FeederConstants;
import org.firstinspires.ftc.teamcode.Robot;

public class FeederClimbSubsystem extends SubsystemBase {

    private final Robot robot = Robot.getInstance();

    public enum State {
        STOP,
        FEED,
        REVERSE_FEED,
        CLIMB_UP,
        CLIMB_DOWN
    }

    private State currentState = State.STOP;

    public FeederClimbSubsystem() {

    }

    public void setState(State state) {
        if (currentState == state) {
            return;
        }

        switch (state) {
            case STOP:
                robot.feederClimbMotor.set(0.0);
                break;

            case FEED:
                robot.feederClimbMotor.set(1.0);
                break;

            case REVERSE_FEED:
                robot.feederClimbMotor.set(-1.0);

            case CLIMB_UP:
                robot.feederClimbMotor.set(1.0);
                break;

            case CLIMB_DOWN:
                robot.feederClimbMotor.set(-1.0);
                break;
        }

        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void stop() {
        setState(State.STOP);
    }

 }
