package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.teamcode.Constants.DriveConstants;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

public class Robot extends com.seattlesolvers.solverslib.command.Robot {
    private static final Robot instance = new Robot();
    public DriveSubsystem drive;
    public MechanismSubsystem mechanism;
    public FeederClimbSubsystem feederClimb;
    public ArmSubsystem arm;

    public static Robot getInstance() {
        return instance;
    }

    public MotorEx leftDrive;
    public MotorEx rightDrive;

    public MotorEx structure1;
    public MotorEx structure2;
    public MotorEx structure3;
    public MotorEx structure4;
    public ServoEx clutchServo;

    public MotorEx feederClimbMotor;

    public MotorEx armMotor;
    public AnalogInput armPotentiometer;

    public void init(HardwareMap hwMap) {
        leftDrive = new MotorEx(hwMap, "left_drive");
        rightDrive = new MotorEx(hwMap, "right_drive");

        leftDrive.setRunMode(Motor.RunMode.RawPower);
        rightDrive.setRunMode(Motor.RunMode.RawPower);

        leftDrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        rightDrive.setInverted(true);

        structure1 = new MotorEx(hwMap, "structure_1");
        structure2 = new MotorEx(hwMap, "structure_2");
        structure3 = new MotorEx(hwMap, "structure_3");
        structure4 = new MotorEx(hwMap, "structure_4");

        structure1.setRunMode(Motor.RunMode.RawPower);
        structure2.setRunMode(Motor.RunMode.RawPower);
        structure3.setRunMode(Motor.RunMode.RawPower);
        structure4.setRunMode(Motor.RunMode.RawPower);

        structure1.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        structure2.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        structure3.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        structure4.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);

        structure3.setInverted(true);
        structure4.setInverted(true);

        clutchServo = new ServoEx(hwMap, "clutch_servo");

        feederClimbMotor = new MotorEx(hwMap, "feeder");
        feederClimbMotor.setRunMode(Motor.RunMode.RawPower);
        feederClimbMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        armMotor = new MotorEx(hwMap, "arm");
        armMotor.setRunMode(Motor.RunMode.RawPower);
        armMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        armPotentiometer = hwMap.get(AnalogInput.class, "potencjometr");

        drive = new DriveSubsystem();
        mechanism = new MechanismSubsystem();
        feederClimb = new FeederClimbSubsystem();
        arm = new ArmSubsystem();
    }

    public void updateLoop() {
        super.run();
    }
}
