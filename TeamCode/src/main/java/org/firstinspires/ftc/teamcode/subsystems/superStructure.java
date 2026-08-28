package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import org.firstinspires.ftc.teamcode.constants.flywheelPIDF;
import org.firstinspires.ftc.teamcode.constants.robotConstants;

public class superStructure extends SubsystemBase {
    private final MotorEx m1, m2, m3, m4;
    private final MotorGroup motors;
    private final PIDFController pidf;

    private double targetRpm = 0.0;
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

        pidf = new PIDFController(flywheelPIDF.Shooter.KP, flywheelPIDF.Shooter.KI,
                flywheelPIDF.Shooter.KD, flywheelPIDF.Shooter.KF);
        pidf.setTolerance(flywheelPIDF.Shooter.RPM_TOLERANCE, Double.POSITIVE_INFINITY);
    }

    public void setTargetRpm(double rpm) { targetRpm = rpm; pidf.setSetPoint(rpm); }
    public boolean atSpeed() { return pidf.atSetPoint(); }

    public void setPower(double power) { motors.set(power * direction); }
    public void toggleDirection() { direction = -direction; }
    public boolean isReversed() { return direction < 0.0; }

    public void stop() {
        targetRpm = 0.0;
        motors.stopMotor();
    }

    @Override
    public void periodic() {

        if (targetRpm <= 0.0) return;
        motors.set(pidf.calculate(m1.getVelocity()));
    }
}
