package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.armHoldCommand;
import org.firstinspires.ftc.teamcode.commands.cheesyDriveCommand;
import org.firstinspires.ftc.teamcode.commands.feederCommand;
import org.firstinspires.ftc.teamcode.commands.superStructureServoClutchCommand;
import org.firstinspires.ftc.teamcode.constants.flywheelPIDF;
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


@TeleOp(name = "PolandFinalMatch", group = "SmartTeleop")
public class polandFinalMatch extends CommandOpMode {

    private static final double MATCH_QUICK_TURN_GAIN = 1.0;
    private static final double MATCH_TURN_GAIN = 1.5;
    private static final double MATCH_SPINUP_RAMP_SEC = 0.2;
    private static final double MATCH_FEED_LOCKOUT_SEC = 0.0;

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
        double quickTurnGain = robotConstants.Drive.CURVATURE_QUICK_TURN_GAIN;
        double turnGain = robotConstants.Drive.CURVATURE_TURN_GAIN;
        double spinupRamp = flywheelPIDF.Shooter.SPINUP_RAMP_SEC;
        double feedLockout = flywheelPIDF.Shooter.FEED_LOCKOUT_SEC;

        robotConstants.Drive.CURVATURE_QUICK_TURN_GAIN = MATCH_QUICK_TURN_GAIN;
        robotConstants.Drive.CURVATURE_TURN_GAIN = MATCH_TURN_GAIN;
        flywheelPIDF.Shooter.SPINUP_RAMP_SEC = MATCH_SPINUP_RAMP_SEC;
        flywheelPIDF.Shooter.FEED_LOCKOUT_SEC = MATCH_FEED_LOCKOUT_SEC;

        try {
            super.runOpMode();
        } finally {
            KoalaLog.stop();
            robotConstants.Drive.CURVATURE_QUICK_TURN_GAIN = quickTurnGain;
            robotConstants.Drive.CURVATURE_TURN_GAIN = turnGain;
            flywheelPIDF.Shooter.SPINUP_RAMP_SEC = spinupRamp;
            flywheelPIDF.Shooter.FEED_LOCKOUT_SEC = feedLockout;
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

        structure.setDefaultCommand(new superStructureServoClutchCommand(
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

        arm.setDefaultCommand(new armHoldCommand(
                arm,
                () -> driver.getButton(GamepadKeys.Button.LEFT_STICK_BUTTON) || operator.getButton(GamepadKeys.Button.B),
                () -> driver.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON) || operator.getButton(GamepadKeys.Button.A),
                () -> driver.getButton(GamepadKeys.Button.DPAD_RIGHT) || operator.getButton(GamepadKeys.Button.DPAD_RIGHT) || driver.getButton(GamepadKeys.Button.DPAD_LEFT) || operator.getButton(GamepadKeys.Button.DPAD_LEFT)
        ));
    }

    @Override
    public void run() {
        for (LynxModule hub : hubs) {
            hub.clearBulkCache();
        }

        super.run();

        AutoLogManager.periodic();
        KoalaLog.log("Mode", structure.getMode(), false);
        KoalaLog.log("Arm Angle", arm.getAngle(), false);
        KoalaLog.log("Feeder Mode", (driver.getButton(GamepadKeys.Button.DPAD_RIGHT) || operator.getButton(GamepadKeys.Button.DPAD_RIGHT) || driver.getButton(GamepadKeys.Button.DPAD_LEFT) || operator.getButton(GamepadKeys.Button.DPAD_LEFT)) ? "Manual" : "Auto", false);
        KoalaLog.log("Battery Voltage", battery.getVoltage(), false);

        panelTelemetry.addData("Mode", structure.getMode());
        panelTelemetry.addData("Target RPM", structure.getTargetRpm());
        panelTelemetry.addData("Velocity RPM", structure.getVelocityRpm());
        panelTelemetry.addData("M1 RPM", structure.getM1Rpm());
        panelTelemetry.addData("M2 RPM", structure.getM2Rpm());
        panelTelemetry.addData("M3 RPM", structure.getM3Rpm());
        panelTelemetry.addData("M4 RPM", structure.getM4Rpm());
        panelTelemetry.addData("At Speed", structure.atSpeed());

        panelTelemetry.addData("Clutch Position (commanded)", clutch.getPosition());

        panelTelemetry.addData("Arm Position", arm.getMotorPosition());
        panelTelemetry.addData("Arm Pot Voltage", arm.getEncoderVoltage());
        panelTelemetry.addData("Arm Angle", arm.getAngle());
        panelTelemetry.addData("Arm Target Angle", arm.getTargetAngle());
        panelTelemetry.addData("Arm Holding", arm.isHolding());
        panelTelemetry.addData("Arm RPM", arm.getArmRpm());

        panelTelemetry.addData("Feeder RPM", feeder.getFeederRpm());
        panelTelemetry.addData("Feeder Mode", (driver.getButton(GamepadKeys.Button.DPAD_RIGHT) || operator.getButton(GamepadKeys.Button.DPAD_RIGHT) || driver.getButton(GamepadKeys.Button.DPAD_LEFT) || operator.getButton(GamepadKeys.Button.DPAD_LEFT)) ? "Manual" : "Auto");

        panelTelemetry.addData("Drive L RPM", drive.getLeftRpm());
        panelTelemetry.addData("Drive R RPM", drive.getRightRpm());

        panelTelemetry.addData("Battery Voltage", battery.getVoltage());
        panelTelemetry.update();
    }
}
