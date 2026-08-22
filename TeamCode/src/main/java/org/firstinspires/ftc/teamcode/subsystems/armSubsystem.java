package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.constants.robotConstants;

public class armSubsystem extends SubsystemBase {
    private final MotorEx arm;

    public armSubsystem(HardwareMap hwMap) {
        arm = new MotorEx(hwMap, robotConstants.Arm.MOTOR);

        arm.setInverted(false);
        arm.setRunMode(Motor.RunMode.RawPower);
        arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power) { arm.set(power); }
    public int getPosition() { return arm.getCurrentPosition(); }

    public void stop() {
        arm.set(0.0);
    }

    @Override
    public void periodic() {
    }
}
