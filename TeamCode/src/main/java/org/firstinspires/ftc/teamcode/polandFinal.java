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
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;

@TeleOp(name = "PolandFinal", group = "SmartTeleop")
public class polandFinal extends CommandOpMode {

    private superStructure structure;
    private clutchSubsystem clutch;
    private feederSubsystem feeder;
    private armSubsystem arm;
    private driveSubsystem drive;

    private GamepadEx driver;

    @Override
    public void initialize() {
        structure = new superStructure(hardwareMap);
        clutch = new clutchSubsystem(hardwareMap);
        feeder = new feederSubsystem(hardwareMap);
        arm = new armSubsystem(hardwareMap);
        drive = new driveSubsystem(hardwareMap);

        driver = new GamepadEx(gamepad1);

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
                () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER)
        ));

        feeder.setDefaultCommand(new feederCommand(
                feeder,
                structure,
                () -> driver.getButton(GamepadKeys.Button.DPAD_RIGHT),
                () -> driver.getButton(GamepadKeys.Button.DPAD_LEFT),
                () -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5
        ));

        arm.setDefaultCommand(new armCommand(
                arm,
                () -> driver.getButton(GamepadKeys.Button.LEFT_STICK_BUTTON),
                () -> driver.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
        ));

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void run() {
        super.run();

        telemetry.addData("Mode", structure.getMode());
        telemetry.addData("Target RPM", structure.getTargetRpm());
        telemetry.addData("Velocity RPM", structure.getVelocityRpm());
        telemetry.addData("At Speed", structure.atSpeed());

        telemetry.addData("Arm Position", arm.getMotorPosition());
        telemetry.addData("Arm Angle", arm.getAngle());
        telemetry.addData("Arm Target Angle", arm.getTargetAngle());

        telemetry.addData(
                "Feeder Mode",
                (driver.getButton(GamepadKeys.Button.DPAD_RIGHT)
                        || driver.getButton(GamepadKeys.Button.DPAD_LEFT)) ? "Manual" : "Auto"
        );

        telemetry.addData("Reversed", driver.getButton(GamepadKeys.Button.LEFT_BUMPER));

        telemetry.update();
    }
}