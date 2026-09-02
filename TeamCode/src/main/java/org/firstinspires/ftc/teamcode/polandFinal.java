package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.armCommand;
import org.firstinspires.ftc.teamcode.commands.cheesyDriveCommand;
import org.firstinspires.ftc.teamcode.commands.feederAutoCommand;
import org.firstinspires.ftc.teamcode.commands.shootIntakeCommand;
import org.firstinspires.ftc.teamcode.constants.flywheelPIDF;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;

@TeleOp
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

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        drive.setDefaultCommand(new cheesyDriveCommand(
                drive,
                driver::getLeftY,
                driver::getRightX,
                () -> driver.getButton(GamepadKeys.Button.RIGHT_BUMPER)
        ));

        structure.setDefaultCommand(new shootIntakeCommand(
                structure,
                clutch,
                () -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5,
                () -> driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5,
                () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER)
        ));

        feeder.setDefaultCommand(new feederAutoCommand(
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
    }

    @Override
    public void run() {
        super.run();

        telemetry.addData("Mode", structure.getMode());
        telemetry.addData("Velocity RPM", structure.getVelocityRpm());
        telemetry.addData("At Speed", structure.atSpeed());
        telemetry.addData("Arm Position", arm.getMotorPosition());
        telemetry.addData("Arm Angle", arm.getAngle());
        telemetry.addData("Arm Target Angle", arm.getTargetAngle());
        telemetry.addData("Reversed", driver.getButton(GamepadKeys.Button.LEFT_BUMPER));
        telemetry.addData("Feeder Mode", (driver.getButton(GamepadKeys.Button.DPAD_RIGHT)
                || driver.getButton(GamepadKeys.Button.DPAD_LEFT)) ? "Manual" : "Auto");
        telemetry.addData("TargetRPM", flywheelPIDF.Shooter.TARGET_RPM);
        telemetry.update();
    }
}
