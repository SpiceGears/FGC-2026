package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.armCommand;
import org.firstinspires.ftc.teamcode.commands.cheesyDriveCommand;
import org.firstinspires.ftc.teamcode.commands.feederCommand;
import org.firstinspires.ftc.teamcode.commands.superStructureCommand;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;
import org.firstinspires.ftc.teamcode.subsystems.superStructureAutoLogged;

import Ori.Coval.Logging.AutoLogManager;
import Ori.Coval.Logging.Logger.KoalaGamepadLogger;
import Ori.Coval.Logging.Logger.KoalaLog;

@TeleOp(name = "PolandFinal", group = "SmartTeleop")
public class polandFinal extends CommandOpMode {

    private superStructure structure;
    private clutchSubsystem clutch;
    private feederSubsystem feeder;
    private armSubsystem arm;
    private driveSubsystem drive;

    private GamepadEx driver;
    private GamepadEx operator;

    @Override
    public void initialize() {
        reset();

        KoalaLog.setup(hardwareMap);
        KoalaLog.start();
        KoalaGamepadLogger.register(gamepad1, gamepad2);

        structure = new superStructureAutoLogged(hardwareMap);
        clutch = new clutchSubsystemAutoLogged(hardwareMap);
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

        structure.setDefaultCommand(new superStructureCommand(
                structure,
                clutch,
                () -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5,
                () -> driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5,
                () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER) || operator.getButton(GamepadKeys.Button.LEFT_BUMPER),
                () -> operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5,
                () -> operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5
        ));

        feeder.setDefaultCommand(new feederCommand(
                feeder,
                structure,
                () -> driver.getButton(GamepadKeys.Button.DPAD_RIGHT) || operator.getButton(GamepadKeys.Button.DPAD_RIGHT),
                () -> driver.getButton(GamepadKeys.Button.DPAD_LEFT) || operator.getButton(GamepadKeys.Button.DPAD_LEFT),
                () -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5
        ));

        arm.setDefaultCommand(new armCommand(
                arm,
                () -> driver.getButton(GamepadKeys.Button.LEFT_STICK_BUTTON) || operator.getButton(GamepadKeys.Button.B),
                () -> driver.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON) || operator.getButton(GamepadKeys.Button.A)
        ));

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void run() {
        super.run();

        AutoLogManager.periodic();
        KoalaLog.log("Mode", structure.getMode(), false);
        KoalaLog.log("Feeder Mode", (driver.getButton(GamepadKeys.Button.DPAD_RIGHT) || operator.getButton(GamepadKeys.Button.DPAD_RIGHT) || driver.getButton(GamepadKeys.Button.DPAD_LEFT) || operator.getButton(GamepadKeys.Button.DPAD_LEFT)) ? "Manual" : "Auto", false);
        KoalaLog.log("Battery Voltage", hardwareMap.voltageSensor.iterator().next().getVoltage(), false);

        // SuperStructure
        telemetry.addData("Mode", structure.getMode());
        telemetry.addData("Target RPM", structure.getTargetRpm());
        telemetry.addData("Velocity RPM", structure.getVelocityRpm());
        telemetry.addData("At Speed", structure.atSpeed());

        // Clutch & Sensors
        telemetry.addData("Clutch State", clutch.getCurrentState());
        telemetry.addData("Clutch Target", clutch.getTargetState());
        telemetry.addData("Clutch Power (commanded)", clutch.getPower());
        telemetry.addData("Clutch L-Sensor (Intake, inverted)", clutch.isAtIntake());
        telemetry.addData("Clutch R-Sensor (Shoot, inverted)", clutch.isAtShooter());
        telemetry.addData("Clutch L-Sensor (raw)", clutch.getRawLeftSensor());
        telemetry.addData("Clutch R-Sensor (raw)", clutch.getRawRightSensor());
        telemetry.addData("Clutch Retry Count", clutch.getRetryCount());
        telemetry.addData("Clutch Stalled", clutch.isStalled());

        // Arm Sensors
        telemetry.addData("Arm Position", arm.getMotorPosition());
//        telemetry.addData("Arm Pot Voltage", arm.getEncoderVoltage());
//        telemetry.addData("Arm Angle", arm.getAngle());
//        telemetry.addData("Arm Target Angle", arm.getTargetAngle());
        telemetry.addData("Arm RPM", arm.getArmRpm());

        // Feeder
        telemetry.addData("Feeder RPM", feeder.getFeederRpm());
        telemetry.addData("Feeder Mode", (driver.getButton(GamepadKeys.Button.DPAD_RIGHT) || operator.getButton(GamepadKeys.Button.DPAD_RIGHT) || driver.getButton(GamepadKeys.Button.DPAD_LEFT) || operator.getButton(GamepadKeys.Button.DPAD_LEFT)) ? "Manual" : "Auto");

        // Drive
        telemetry.addData("Drive L RPM", drive.getLeftRpm());
        telemetry.addData("Drive R RPM", drive.getRightRpm());

        telemetry.addData("Battery Voltage", hardwareMap.voltageSensor.iterator().next().getVoltage());
        telemetry.update();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        try {
            super.runOpMode();
        } finally {
            KoalaLog.stop();
        }
    }
}
