package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.commands.cheesyDriveCommand;
import org.firstinspires.ftc.teamcode.commands.feederCommand;
import org.firstinspires.ftc.teamcode.commands.superStructureCommand;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystemAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;
import org.firstinspires.ftc.teamcode.subsystems.superStructureAutoLogged;

import Ori.Coval.Logging.AutoLogManager;
import Ori.Coval.Logging.Logger.KoalaGamepadLogger;
import Ori.Coval.Logging.Logger.KoalaLog;

@TeleOp
public class simpleTeleOp extends CommandOpMode {

    private superStructure structure;
    private clutchSubsystem clutch;
    private feederSubsystem feeder;
    private driveSubsystem drive;
    private GamepadEx driver;

    @Override
    public void initialize() {

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        KoalaLog.setup(hardwareMap);
        KoalaLog.start();

        KoalaGamepadLogger.register(gamepad1, gamepad2);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        structure = new superStructureAutoLogged(hardwareMap);
        clutch = new clutchSubsystem(hardwareMap);
        feeder = new feederSubsystemAutoLogged(hardwareMap);
        drive = new driveSubsystemAutoLogged(hardwareMap);

        driver = new GamepadEx(gamepad1);
        register(structure, clutch, feeder, drive);

        drive.setDefaultCommand(new cheesyDriveCommand(
                drive,
                () -> -gamepad1.left_stick_y,
                () -> gamepad1.right_stick_x,
                () -> gamepad1.right_bumper));

        structure.setDefaultCommand(new superStructureCommand(
                structure,
                clutch,
                () -> gamepad1.right_trigger > 0.5,
                () -> gamepad1.left_trigger > 0.5,
                () -> gamepad1.left_bumper));

        feeder.setDefaultCommand(new feederCommand(
                feeder, structure,
                () -> gamepad1.dpad_right,
                () -> gamepad1.dpad_left,
                () -> gamepad1.right_trigger > 0.5));
    }

    @Override
    public void run() {
        super.run();

        AutoLogManager.periodic();

        telemetry.addData("Structure RPM", structure.getVelocityRpm());
        telemetry.addData("Clutch State", clutch.getCurrentState());
        telemetry.addData("Clutch Target", clutch.getTargetState());
        telemetry.addData("Clutch Power (commanded)", clutch.getPower());
        telemetry.addData("Clutch L-Sensor (Intake, inverted)", clutch.isAtIntake());
        telemetry.addData("Clutch R-Sensor (Shoot, inverted)", clutch.isAtShooter());
        telemetry.addData("Clutch L-Sensor (raw)", clutch.getRawLeftSensor());
        telemetry.addData("Clutch R-Sensor (raw)", clutch.getRawRightSensor());
        telemetry.addData("Clutch Retry Count", clutch.getRetryCount());
        telemetry.addData("Clutch Stalled", clutch.isStalled());
        telemetry.addData("Battery", hardwareMap.voltageSensor.iterator().next().getVoltage());
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
