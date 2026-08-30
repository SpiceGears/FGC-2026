package org.firstinspires.ftc.teamcode.Subsystems;

import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.Robot;

public class MechanismSubsystem extends SubsystemBase {

    public enum Mode {
        INTAKE,
        SHOOTER
    }

    private Mode currentMode = Mode.INTAKE;

    private final Robot robot = Robot.getInstance();
    public MechanismSubsystem() {
        robot.clutchServo.set(Constants.ClutchConstants.INTAKE_POSITION);
    }

    public void setMode(Mode mode) {
        if (currentMode == mode) return;

        switch (mode) {
            case INTAKE:
                robot.clutchServo.set(Constants.ClutchConstants.INTAKE_POSITION);
                break;

            case SHOOTER:
                robot.clutchServo.set(Constants.ClutchConstants.SHOOTER_POSITION);
                break;
        }

        currentMode = mode;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public boolean isIntakeMode() {
        return currentMode == Mode.INTAKE;
    }

    public boolean isShooterMode() {
        return currentMode == Mode.SHOOTER;
    }

    public void setPower(double power) {
        robot.structure1.set(power);
        robot.structure2.set(power);
        robot.structure3.set(power);
        robot.structure4.set(power);
    }

    public void setShooterRPM(double rpm) {
        double ticksPerSecond = rpmToTicksPerSecond(rpm);

        robot.structure1.setVelocity(ticksPerSecond);
        robot.structure2.setVelocity(ticksPerSecond);
        robot.structure3.setVelocity(ticksPerSecond);
        robot.structure4.setVelocity(ticksPerSecond);
    }

    public boolean atShooterSpeed() {
        return Math.abs(getShooterRPM() - ShooterConstants.TARGET_RPM) <= ShooterConstants.RPM_TOLERANCE;
    }

    public double getShooterRPM() {
        double rpm1 = Math.abs(ticksPerSecondToRPM(
                robot.structure1.getVelocity()
        ));

        double rpm2 = Math.abs(ticksPerSecondToRPM(
                robot.structure2.getVelocity()
        ));

        double rpm3 = Math.abs(ticksPerSecondToRPM(
                robot.structure3.getVelocity()
        ));

        double rpm4 = Math.abs(ticksPerSecondToRPM(
                robot.structure4.getVelocity()
        ));

        return (rpm1 + rpm2 + rpm3 + rpm4) / 4.0;
    }

    private double rpmToTicksPerSecond(double rpm) {
        return (rpm / 60.0) * ShooterConstants.TICKS_PER_REV;
    }

    private double ticksPerSecondToRPM(double ticksPerSecond) {
        return (ticksPerSecond / ShooterConstants.TICKS_PER_REV) * 60.0;
    }

    public void stop() {
        setPower(0);
    }
}
