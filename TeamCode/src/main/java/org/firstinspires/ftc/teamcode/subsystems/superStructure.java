package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import org.firstinspires.ftc.teamcode.constants.flywheelPIDF;
import org.firstinspires.ftc.teamcode.constants.intakePIDF;
import org.firstinspires.ftc.teamcode.constants.robotConstants;

public class superStructure extends SubsystemBase {
    public enum Mode { NONE, SHOOTER, INTAKE }

    private final MotorEx m1, m2, m3, m4;
    private final MotorGroup motors;
    private final PIDFController pidfShooter;
    private final PIDFController pidfIntake;

    private Mode mode = Mode.NONE;
    private double direction = 1.0;

    public superStructure(HardwareMap hwMap) {
        m1 = new MotorEx(hwMap, robotConstants.SuperStructure.M1, flywheelPIDF.Shooter.CPR, flywheelPIDF.Shooter.RPM);
        m2 = new MotorEx(hwMap, robotConstants.SuperStructure.M2, flywheelPIDF.Shooter.CPR, flywheelPIDF.Shooter.RPM);
        m3 = new MotorEx(hwMap, robotConstants.SuperStructure.M3, flywheelPIDF.Shooter.CPR, flywheelPIDF.Shooter.RPM);
        m4 = new MotorEx(hwMap, robotConstants.SuperStructure.M4, flywheelPIDF.Shooter.CPR, flywheelPIDF.Shooter.RPM);

        m1.setInverted(false);
        m2.setInverted(false);
        m3.setInverted(true);
        m4.setInverted(true);

        motors = new MotorGroup(m1, m2, m3, m4);
        motors.setRunMode(Motor.RunMode.RawPower);

        pidfShooter = new PIDFController(flywheelPIDF.Shooter.KP, flywheelPIDF.Shooter.KI,
                flywheelPIDF.Shooter.KD, flywheelPIDF.Shooter.KF);
        pidfShooter.setTolerance(flywheelPIDF.Shooter.RPM_TOLERANCE, Double.POSITIVE_INFINITY);

        pidfIntake = new PIDFController(intakePIDF.Intake.KP, intakePIDF.Intake.KI,
                intakePIDF.Intake.KD, intakePIDF.Intake.KF);
        pidfIntake.setTolerance(intakePIDF.Intake.RPM_TOLERANCE, Double.POSITIVE_INFINITY);
    }

    public void setTargetRpm(double rpm) {
        mode = Mode.SHOOTER;
        pidfShooter.setSetPoint(rpm);
    }

    public void spinUpShooter() { spinUpShooter(false); }

    public void spinUpShooter(boolean reversed) {
        mode = Mode.SHOOTER;
        double rpm = flywheelPIDF.Shooter.TARGET_RPM;
        pidfShooter.setSetPoint(reversed ? -rpm : rpm);
    }

    public void spinUpIntake() { spinUpIntake(false); }

    public void spinUpIntake(boolean reversed) {
        mode = Mode.INTAKE;
        double rpm = intakePIDF.Intake.TARGET_RPM;
        pidfIntake.setSetPoint(reversed ? rpm : -rpm);
    }

    public boolean atSpeed() {
        switch (mode) {
            case SHOOTER: return pidfShooter.atSetPoint();
            case INTAKE: return pidfIntake.atSetPoint();
            default: return false;
        }
    }

    public Mode getMode() { return mode; }
    public double getVelocityRpm() { return m1.getVelocity(); }

    public void setPower(double power) { motors.set(power * direction); }
    public void toggleDirection() { direction = -direction; }
    public boolean isReversed() { return direction < 0.0; }

    public void stop() {
        mode = Mode.NONE;
        motors.stopMotor();
    }

    private void refreshGainsFromConstants() {
        pidfShooter.setPIDF(flywheelPIDF.Shooter.KP, flywheelPIDF.Shooter.KI, flywheelPIDF.Shooter.KD, flywheelPIDF.Shooter.KF);
        pidfShooter.setTolerance(flywheelPIDF.Shooter.RPM_TOLERANCE, Double.POSITIVE_INFINITY);
        pidfIntake.setPIDF(intakePIDF.Intake.KP, intakePIDF.Intake.KI, intakePIDF.Intake.KD, intakePIDF.Intake.KF);
        pidfIntake.setTolerance(intakePIDF.Intake.RPM_TOLERANCE, Double.POSITIVE_INFINITY);
    }

    @Override
    public void periodic() {
        refreshGainsFromConstants();

        switch (mode) {
            case SHOOTER:
                motors.set(pidfShooter.calculate(m1.getVelocity()));
                break;
            case INTAKE:
                motors.set(pidfIntake.calculate(m1.getVelocity()));
                break;
            case NONE:
            default:
                break;
        }
    }
}
