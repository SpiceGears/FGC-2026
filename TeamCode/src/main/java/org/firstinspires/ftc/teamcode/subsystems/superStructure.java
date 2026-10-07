package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import org.firstinspires.ftc.teamcode.constants.flywheelPIDF;
import org.firstinspires.ftc.teamcode.constants.intakePIDF;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog(postToFtcDashboard = false)
public class    superStructure extends SubsystemBase {
    public enum Mode { NONE, SHOOTER, INTAKE, UNJAM }

    private final MotorEx m1, m2, m3, m4;
    private final MotorGroup motors;
    private final PIDFController pidfShooter;
    private final PIDFController pidfIntake;

    private Mode mode = Mode.NONE;
    private double direction = 1.0;
    private final ElapsedTime shooterStartTimer = new ElapsedTime();

    public superStructure(HardwareMap hwMap) {
        m1 = new MotorEx(hwMap, robotConstants.SuperStructure.M1);
        m2 = new MotorEx(hwMap, robotConstants.SuperStructure.M2);
        m3 = new MotorEx(hwMap, robotConstants.SuperStructure.M3);
        m4 = new MotorEx(hwMap, robotConstants.SuperStructure.M4);


        m1.setInverted(true);
        m2.setInverted(true);
        m3.setInverted(false);
        m4.setInverted(false);

        motors = new MotorGroup(m1, m2, m3, m4);
        motors.setRunMode(Motor.RunMode.RawPower);

        pidfShooter = new PIDFController(flywheelPIDF.Shooter.KP, flywheelPIDF.Shooter.KI,
                flywheelPIDF.Shooter.KD, flywheelPIDF.Shooter.KF);

        pidfIntake = new PIDFController(intakePIDF.Intake.KP, intakePIDF.Intake.KI,
                intakePIDF.Intake.KD, intakePIDF.Intake.KF);
    }

    public void setTargetRpm(double rpm) {
        mode = Mode.SHOOTER;
        pidfShooter.setSetPoint(rpm);
    }

    public void spinUpShooter() { spinUpShooter(false); }

    public void spinUpShooter(boolean reversed) {
        if (mode == Mode.UNJAM) {
            return;
        }
        if (mode == Mode.SHOOTER
                && shooterStartTimer.seconds() >= flywheelPIDF.Shooter.JAM_TIMEOUT_SEC
                && Math.abs(getVelocityRpm()) < flywheelPIDF.Shooter.JAM_MIN_RPM) {
            // Shooter failed to spin up in time: run it backwards until the shoot button is released.
            mode = Mode.UNJAM;
            motors.set(-Math.signum(pidfShooter.getSetPoint()) * flywheelPIDF.Shooter.JAM_REVERSE_POWER);
            return;
        }
        if (mode != Mode.SHOOTER) {
            shooterStartTimer.reset();
        }
        mode = Mode.SHOOTER;
        double rampFraction = flywheelPIDF.Shooter.SPINUP_RAMP_SEC <= 0.0
                ? 1.0
                : Math.min(1.0, shooterStartTimer.seconds() / flywheelPIDF.Shooter.SPINUP_RAMP_SEC);
        double rpm = flywheelPIDF.Shooter.TARGET_RPM * rampFraction;
        pidfShooter.setSetPoint(reversed ? -rpm : rpm);
    }

    public void spinUpIntake() {
        mode = Mode.INTAKE;
        double rpm = intakePIDF.Intake.TARGET_RPM;
        pidfIntake.setSetPoint(rpm);
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public boolean atSpeed() {
        switch (mode) {
            case SHOOTER:
                boolean rampComplete = shooterStartTimer.seconds() >= flywheelPIDF.Shooter.SPINUP_RAMP_SEC;
                return rampComplete && isWithinDropTolerance(pidfShooter.getSetPoint(), flywheelPIDF.Shooter.RPM_TOLERANCE);
            case INTAKE: return isWithinDropTolerance(pidfIntake.getSetPoint(), intakePIDF.Intake.RPM_TOLERANCE);
            default: return false;
        }
    }

    // RPM may exceed the setpoint freely; tolerance only limits how far it can drop below it.
    // Not private: AutoLog generates an override for every value-returning method.
    protected boolean isWithinDropTolerance(double setPointRpm, double toleranceRpm) {
        double drop = (setPointRpm - getVelocityRpm()) * Math.signum(setPointRpm);
        return drop <= toleranceRpm;
    }

    public Mode getMode() { return mode; }

    public boolean isJammed() { return mode == Mode.UNJAM; }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getVelocityRpm() {
        double rpm = 0.0;
        for (MotorEx motor : new MotorEx[]{m1, m2, m3, m4}) {
            double motorRpm = motorUtils.getRPM(motor);
            if (Math.abs(motorRpm) > Math.abs(rpm)) {
                rpm = motorRpm;
            }
        }
        return rpm;
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getTargetRpm() {
        switch (mode) {
            case SHOOTER: return pidfShooter.getSetPoint();
            case INTAKE: return pidfIntake.getSetPoint();
            default: return 0.0;
        }
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public boolean isShooterReadyToFeed() {
        double lockout = Math.max(flywheelPIDF.Shooter.FEED_LOCKOUT_SEC, robotConstants.Feeder.MIN_SHOOT_TO_FEED_SEC);
        return mode == Mode.SHOOTER && shooterStartTimer.seconds() >= lockout;
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getShooterModeSec() {
        return mode == Mode.SHOOTER ? shooterStartTimer.seconds() : 0.0;
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getM1Rpm() { return motorUtils.getRPM(m1); }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getM2Rpm() { return motorUtils.getRPM(m2); }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getM3Rpm() { return motorUtils.getRPM(m3); }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getM4Rpm() { return motorUtils.getRPM(m4); }

    public void setPower(double power) {
        mode = Mode.NONE;
        motors.set(power * direction);
    }

    public void toggleDirection() { direction = -direction; }
    public boolean isReversed() { return direction < 0.0; }

    public void stop() {
        mode = Mode.NONE;
        motors.set(0);
    }

    private void refreshGainsFromConstants() {
        pidfShooter.setPIDF(flywheelPIDF.Shooter.KP, flywheelPIDF.Shooter.KI, flywheelPIDF.Shooter.KD, flywheelPIDF.Shooter.KF);
        pidfIntake.setPIDF(intakePIDF.Intake.KP, intakePIDF.Intake.KI, intakePIDF.Intake.KD, intakePIDF.Intake.KF);
    }

    @Override
    public void periodic() {
        refreshGainsFromConstants();

        switch (mode) {
            case SHOOTER:
                motors.set(pidfShooter.calculate(getVelocityRpm()));
                break;
            case INTAKE:
                motors.set(pidfIntake.calculate(getVelocityRpm()));
                break;
            case UNJAM:
            case NONE:
            default:
                break;
        }
    }
}
