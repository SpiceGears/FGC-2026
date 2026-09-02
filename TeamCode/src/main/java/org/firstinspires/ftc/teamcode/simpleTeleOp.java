package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.cheesyDriveCommand;
import org.firstinspires.ftc.teamcode.commands.feederCommand;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
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
                () -> gamepad1.right_bumper
        ));



        driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whileHeld(new feederCommand(feeder, robotConstants.Feeder.UP_POWER));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whileHeld(new feederCommand(feeder, robotConstants.Feeder.DOWN_POWER));        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(structure::toggleDirection);
    }


    @Override
    public void run() {
        super.run();

        String mode = "none";

        if (gamepad1.right_trigger > 0.5) {
            clutch.shoot();
            structure.setPower(robotConstants.SuperStructure.SHOOT_POWER);
            mode = "shooter";
        } else if (gamepad1.left_trigger > 0.5) {
            clutch.intake();
            structure.setPower(robotConstants.SuperStructure.INTAKE_POWER);
            mode = "intake";
        } else {
            structure.setPower(0.0);
        }

        AutoLogManager.periodic();

        telemetry.addData("Mode", mode);
        telemetry.addData("Structure Reversed", structure.isReversed());
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