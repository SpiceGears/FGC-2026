package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.cheesyDriveCommand;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.clutchServoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.clutchServoSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;
import org.firstinspires.ftc.teamcode.subsystems.superStructureAutoLogged;

import java.util.List;

import Ori.Coval.Logging.AutoLogManager;
import Ori.Coval.Logging.Logger.KoalaGamepadLogger;
import Ori.Coval.Logging.Logger.KoalaLog;

@TeleOp(name = "RawPower", group = "SmartTeleop")
public class rawPower extends CommandOpMode {

    private superStructure structure;
    private clutchServoSubsystem clutch;
    private feederSubsystem feeder;
    private armSubsystem arm;
    private driveSubsystem drive;

    private GamepadEx driver;
    private GamepadEx operator;
    private MultipleTelemetry panelTelemetry;
    private List<LynxModule> hubs;
    private VoltageSensor battery;

    @Override
    public void runOpMode() throws InterruptedException {
        try {
            super.runOpMode();
        } finally {
            KoalaLog.stop();
        }
    }

    @Override
    public void initialize() {
        reset();
        panelTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        hubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        battery = hardwareMap.voltageSensor.iterator().next();

        KoalaLog.setup(hardwareMap);
        KoalaLog.start();
        KoalaGamepadLogger.register(gamepad1, gamepad2);

        structure = new superStructureAutoLogged(hardwareMap);
        clutch = new clutchServoSubsystemAutoLogged(hardwareMap);
        feeder = new feederSubsystemAutoLogged(hardwareMap);
        arm = new armSubsystemAutoLogged(hardwareMap);
        drive = new driveSubsystemAutoLogged(hardwareMap);

        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        register(structure, clutch, feeder, arm, drive);

        drive.setDefaultCommand(new cheesyDriveCommand(
                drive,
                driver::getLeftY,
                driver::getRightX,
                () -> driver.getButton(GamepadKeys.Button.RIGHT_BUMPER)
        ));

        structure.setDefaultCommand(new RunCommand(() -> {
            if (driver.getButton(GamepadKeys.Button.LEFT_BUMPER) || operator.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
                structure.setPower(robotConstants.SuperStructure.REVERSE_POWER);
            } else if (driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5 || operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5) {
                clutch.shoot();
                structure.setPower(-robotConstants.SuperStructure.SHOOT_POWER);
            } else if (driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5 || operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5) {
                clutch.intake();
                structure.setPower(robotConstants.SuperStructure.INTAKE_POWER);
            } else {
                structure.setPower(0.0);
            }
        }, structure, clutch));

        feeder.setDefaultCommand(new RunCommand(() -> {
            if (driver.getButton(GamepadKeys.Button.DPAD_RIGHT) || operator.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
                feeder.setPower(robotConstants.Feeder.UP_POWER);
            } else if (driver.getButton(GamepadKeys.Button.DPAD_LEFT) || operator.getButton(GamepadKeys.Button.DPAD_LEFT)) {
                feeder.setPower(robotConstants.Feeder.DOWN_POWER);
            } else {
                feeder.stop();
            }
        }, feeder));

        arm.setDefaultCommand(new RunCommand(() -> {
            if (driver.getButton(GamepadKeys.Button.LEFT_STICK_BUTTON) || operator.getButton(GamepadKeys.Button.B)) {
                arm.setPower(robotConstants.Arm.UP_POWER);
            } else if (driver.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON) || operator.getButton(GamepadKeys.Button.A)) {
                arm.setPower(robotConstants.Arm.DOWN_POWER);
            } else {
                arm.stop();
            }
        }, arm));
    }

    @Override
    public void run() {
        for (LynxModule hub : hubs) {
            hub.clearBulkCache();
        }

        super.run();

        AutoLogManager.periodic();
        KoalaLog.log("Battery Voltage", battery.getVoltage(), false);

        panelTelemetry.addData("Velocity RPM", structure.getVelocityRpm());
        panelTelemetry.addData("M1 RPM", structure.getM1Rpm());
        panelTelemetry.addData("M2 RPM", structure.getM2Rpm());
        panelTelemetry.addData("M3 RPM", structure.getM3Rpm());
        panelTelemetry.addData("M4 RPM", structure.getM4Rpm());

        panelTelemetry.addData("Clutch Position (commanded)", clutch.getPosition());

        panelTelemetry.addData("Arm Position", arm.getMotorPosition());
        panelTelemetry.addData("Arm Pot Voltage", arm.getEncoderVoltage());
        panelTelemetry.addData("Arm Angle", arm.getAngle());
        panelTelemetry.addData("Arm RPM", arm.getArmRpm());

        panelTelemetry.addData("Feeder RPM", feeder.getFeederRpm());

        panelTelemetry.addData("Drive L RPM", drive.getLeftRpm());
        panelTelemetry.addData("Drive R RPM", drive.getRightRpm());

        panelTelemetry.addData("Battery Voltage", battery.getVoltage());
        panelTelemetry.update();
    }
}
