package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.gamepad.TriggerReader;

import org.firstinspires.ftc.teamcode.Commands.ArmCommand;
import org.firstinspires.ftc.teamcode.Commands.ClimbCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseArmCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseClimbCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

@TeleOp(name = "Poland Final")
public class PolandFinal extends CommandOpMode {
    private DriveSubsystem drive;
    private MechanismSubsystem mechanism;
    private ClutchSubsystem clutch;
    private FeederClimbSubsystem feeder;
    private GamepadEx driver;
    private Trigger intakeTrigger;
    private Trigger shooterTrigger;

    @Override
    public void initialize() {
        drive = new DriveSubsystem(hardwareMap);
        mechanism = new MechanismSubsystem(hardwareMap);
        clutch = new ClutchSubsystem(hardwareMap);
        feeder = new FeederClimbSubsystem(hardwareMap);
        driver = new GamepadEx(gamepad1);
        TriggerReader rightTrigger = new TriggerReader(
                driver,
                GamepadKeys.Trigger.RIGHT_TRIGGER
        );
        intakeTrigger = new Trigger(rightTrigger::isDown);
        TriggerReader leftTrigger = new TriggerReader(
                driver,
                GamepadKeys.Trigger.LEFT_TRIGGER
        );


        drive.setDefaultCommand(
                new DriveCommand(
                        drive,
                        () -> -driver.getLeftY(),
                        () -> -driver.getRightY()
                )
        );

        configureBindings();
    }

    private void configureBindings() {
        intakeTrigger.whileActiveOnce(
                new IntakeCommand(mechanism, clutch)
        );

        shooterTrigger.whileActiveOnce(
                new ShooterCommand(mechanism, clutch, feeder)
        );

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenHeld(
                new ReverseIntakeCommand(mechanism, clutch)
        );

        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP).whileHeld(
                new ClimbCommand(feeder)
        );

        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whileHeld(
                new ReverseClimbCommand(feeder)
        );

        driver.getGamepadButton(GamepadKeys.Button.SQUARE).whileHeld(
                new ArmCommand(feeder)
        );

        driver.getGamepadButton(GamepadKeys.Button.CROSS).whileHeld(
                new ReverseArmCommand(feeder)
        );
    }
}