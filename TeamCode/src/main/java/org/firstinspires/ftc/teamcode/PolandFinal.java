package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.gamepad.TriggerReader;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

@TeleOp(name = "Poland Final")
public class PolandFinal extends CommandOpMode {
    private DriveSubsystem drive;
    private MechanismSubsystem mechanism;
    private ClutchSubsystem clutch;
    private GamepadEx driver;
    private Trigger intakeTrigger;
    private Trigger shooterTrigger;

    @Override
    public void initialize() {
        drive = new DriveSubsystem(hardwareMap);
        mechanism = new MechanismSubsystem(hardwareMap);
        clutch = new ClutchSubsystem(hardwareMap);
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
                new ShooterCommand(mechanism, clutch)
        );

        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenHeld(
                new ReverseIntakeCommand(mechanism, clutch)
        );

        

    }
}