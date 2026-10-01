package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
//import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
//import com.seattlesolvers.solverslib.util.InterpLUT;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.teamcode.constants.armPIDF;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.utils.motorUtils;

import Ori.Coval.Logging.AutoLog;
import Ori.Coval.Logging.AutoLogOutput;

@AutoLog(postToFtcDashboard = false)
public class armSubsystem extends SubsystemBase {
    private final MotorEx arm;
//    private final AnalogInput pot;
//    private final InterpLUT lut;
//    private final PIDFController pidController;
//    private double targetAngle;

    public armSubsystem(HardwareMap hwMap) {
        arm = new MotorEx(hwMap, robotConstants.Arm.MOTOR);

        arm.setInverted(false);
        arm.setRunMode(Motor.RunMode.RawPower);
        arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

//        pot = hwMap.get(AnalogInput.class, robotConstants.Arm.POTENTIOMETER);
//        lut = new InterpLUT();
//        lut.add(0.45, 90);
//        lut.add(0.945, 0);
//        lut.add(1.44, -45.0);
//        lut.createLUT();
//
//        targetAngle = getAngle();
//        armPIDF.Arm.START_ANGLE = targetAngle;
//
//        pidController = new PIDFController(
//                armPIDF.Arm.KP,
//                armPIDF.Arm.KI,
//                armPIDF.Arm.KD,
//                armPIDF.Arm.KF
//        );
//        pidController.setTolerance(armPIDF.Arm.TOLERANCE_DEG);
    }

    public void setPower(double power) { arm.set(power); }
    public int getMotorPosition() { return arm.getCurrentPosition(); }

//    public double getEncoderVoltage() { return pot.getVoltage(); }
//    public double getAngle() { return lut.get(pot.getVoltage()); }
//
//    public void setTargetAngle(double targetAngle) {
//        this.targetAngle = Math.max(armPIDF.Arm.MIN_ANGLE, Math.min(armPIDF.Arm.MAX_ANGLE, targetAngle));
//    }
//
//    public double getTargetAngle() { return targetAngle; }

    public void stop() {
        arm.set(0.0);
    }

    @AutoLogOutput(postToFtcDashboard = false)
    public double getArmRpm() { return motorUtils.getRPM(arm); }

//    private void refreshGainsFromConstants() {
//        pidController.setPIDF(
//                armPIDF.Arm.KP,
//                armPIDF.Arm.KI,
//                armPIDF.Arm.KD,
//                armPIDF.Arm.KF
//        );
//    }
//
//    @Override
//    public void periodic() {
//        refreshGainsFromConstants();
//
//        double power = pidController.calculate(getAngle(), targetAngle);
//        arm.set(power);
//    }
}