package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.constants.robotConstants;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog(postToFtcDashboard = false)
public class clutchSubsystem extends SubsystemBase {

    public enum State {
        IDLE,
        MOVING_TO_INTAKE,
        MOVING_TO_SHOOTER,
        BACKING_OFF_INTAKE,
        BACKING_OFF_SHOOTER,
        STALLED   
    }

    private static final int MAX_RETRIES = 3;

    private final CRServo clutch;
    private final DigitalChannel leftSensor;  
    private final DigitalChannel rightSensor; 

    private State currentState = State.IDLE;
    private State targetState = State.IDLE;
    private final ElapsedTime timer = new ElapsedTime();

    private int retryCount = 0;

    public clutchSubsystem(HardwareMap hwMap) {
        clutch = hwMap.get(CRServo.class, robotConstants.Clutch.SERVO);
        clutch.setDirection(DcMotorSimple.Direction.REVERSE);

        leftSensor = hwMap.get(DigitalChannel.class, robotConstants.Clutch.LEFT_SENSOR);
        leftSensor.setMode(DigitalChannel.Mode.INPUT);

        rightSensor = hwMap.get(DigitalChannel.class, robotConstants.Clutch.RIGHT_SENSOR);
        rightSensor.setMode(DigitalChannel.Mode.INPUT);

        shoot();
    }

    public void intake() {
        if (currentState == State.STALLED) {
            return; 
        }
        if (targetState != State.MOVING_TO_INTAKE) {
            targetState = State.MOVING_TO_INTAKE;
            retryCount = 0; 
            if (!isAtIntake()) {
                startMovingToIntake();
            }
        }
    }

    public void shoot() {
        if (currentState == State.STALLED) {
            return; 
        }
        if (targetState != State.MOVING_TO_SHOOTER) {
            targetState = State.MOVING_TO_SHOOTER;
            retryCount = 0; 
            if (!isAtShooter()) {
                startMovingToShooter();
            }
        }
    }

    private void startMovingToIntake() {
        currentState = State.MOVING_TO_INTAKE;
        clutch.setPower(robotConstants.Clutch.INTAKE_POWER);
        timer.reset();
    }

    private void startMovingToShooter() {
        currentState = State.MOVING_TO_SHOOTER;
        clutch.setPower(robotConstants.Clutch.SHOOT_POWER);
        timer.reset();
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public boolean isAtIntake() {
        return !leftSensor.getState();
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public boolean isAtShooter() {
        return !rightSensor.getState();
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public boolean getRawLeftSensor() {
        return leftSensor.getState();
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public boolean getRawRightSensor() {
        return rightSensor.getState();
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getPower() {
        return clutch.getPower();
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public State getCurrentState() {
        return currentState;
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public State getTargetState() {
        return targetState;
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public int getRetryCount() {
        return retryCount;
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public boolean isStalled() {
        return currentState == State.STALLED;
    }

    public void resetStall() {
        if (currentState == State.STALLED) {
            currentState = State.IDLE;
            targetState = State.IDLE;
            retryCount = 0;
            clutch.setPower(0);
        }
    }

    public void stop() {
        currentState = State.IDLE;
        targetState = State.IDLE;
        clutch.setPower(0);
    }

    @Override
    public void periodic() {
        switch (currentState) {
            case MOVING_TO_INTAKE:
                if (isAtIntake()) {
                    clutch.setPower(0);
                    currentState = State.IDLE;
                    retryCount = 0;
                } else if (timer.seconds() > robotConstants.Clutch.TIMEOUT_SEC) {
                    if (retryCount >= MAX_RETRIES) {          
                        clutch.setPower(0);                    
                        currentState = State.STALLED;          
                        break;                                  
                    }
                    retryCount++;
                    clutch.setPower(robotConstants.Clutch.BACKOFF_POWER);
                    currentState = State.BACKING_OFF_INTAKE;
                    timer.reset();
                }
                break;

            case MOVING_TO_SHOOTER:
                if (isAtShooter()) {
                    clutch.setPower(0);
                    currentState = State.IDLE;
                    retryCount = 0;
                } else if (timer.seconds() > robotConstants.Clutch.TIMEOUT_SEC) {
                    if (retryCount >= MAX_RETRIES) {          
                        clutch.setPower(0);                    
                        currentState = State.STALLED;          
                        break;                                  
                    }
                    retryCount++;
                    clutch.setPower(-robotConstants.Clutch.BACKOFF_POWER);
                    currentState = State.BACKING_OFF_SHOOTER;
                    timer.reset();
                }
                break;

            case BACKING_OFF_INTAKE:
                if (isAtIntake()) {
                    clutch.setPower(0);
                    currentState = State.IDLE;
                    retryCount = 0;
                } else if (timer.seconds() > robotConstants.Clutch.BACKOFF_TIME_SEC) {
                    startMovingToIntake();
                }
                break;

            case BACKING_OFF_SHOOTER:
                if (isAtShooter()) { 
                    clutch.setPower(0);
                    currentState = State.IDLE;
                    retryCount = 0;
                } else if (timer.seconds() > robotConstants.Clutch.BACKOFF_TIME_SEC) {
                    startMovingToShooter();
                }
                break;

            case IDLE:
                if (targetState == State.MOVING_TO_INTAKE && !isAtIntake()) {
                    startMovingToIntake();
                } else if (targetState == State.MOVING_TO_SHOOTER && !isAtShooter()) {
                    startMovingToShooter();
                }
                break;

            case STALLED: 
                break;
        }
    }
}