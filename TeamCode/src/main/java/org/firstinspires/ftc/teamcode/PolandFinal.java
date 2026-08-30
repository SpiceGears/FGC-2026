package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.ArmCommand;
import org.firstinspires.ftc.teamcode.Commands.ClimbCommand;
import org.firstinspires.ftc.teamcode.Commands.FeederCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;

@TeleOp(name = "Poland Final")
public class PolandFinal extends CommandOpMode {

    private final Robot robot = Robot.getInstance();

    private GamepadEx driver;

    @Override
    public void initialize() {
        super.reset();

        // =========================
        // ROBOT
        // =========================

        robot.init(hardwareMap);

        driver = new GamepadEx(gamepad1);


        // =========================
        // INTAKE
        // =========================

        // RT -> Intake
        new Trigger(() ->
                driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5
        ).whileActiveContinuous(
                new IntakeCommand(1.0)
        );

        // RB -> Reverse Intake
        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileActiveContinuous(
                        new IntakeCommand(-1.0)
                );


        // =========================
        // SHOOTER
        // =========================

        // LT -> Shooter
        new Trigger(() ->
                driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1
        ).whileActiveContinuous(
                new ShooterCommand()
        );


        // =========================
        // REVERSE FEEDER
        // =========================

        // LB -> Reverse Feeder
        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileActiveContinuous(
                        new FeederCommand(
                                FeederClimbSubsystem.State.REVERSE_FEED
                        )
                );


        // =========================
        // CLIMB
        // =========================

        // D-pad UP -> Climb Up
        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whileActiveContinuous(
                        new ClimbCommand(
                                FeederClimbSubsystem.State.CLIMB_UP
                        )
                );

        // D-pad DOWN -> Climb Down
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileActiveContinuous(
                        new ClimbCommand(
                                FeederClimbSubsystem.State.CLIMB_DOWN
                        )
                );


        // =========================
        // ARM
        // =========================

        // Y -> Arm Up
        driver.getGamepadButton(GamepadKeys.Button.Y)
                .whileActiveContinuous(
                        new ArmCommand(ArmSubsystem.State.UP)
                );

        // X -> Arm Down
        driver.getGamepadButton(GamepadKeys.Button.X)
                .whileActiveContinuous(
                        new ArmCommand(ArmSubsystem.State.DOWN)
                );
    }


    @Override
    public void run() {

        // =========================
        // DRIVE
        // =========================

        if (CommandScheduler.getInstance().isAvailable(robot.drive)) {

            double forward = driver.getLeftY();
            double turn = driver.getRightX();

            robot.drive.tankDrive(forward, turn);
        }


        // =========================
        // COMMAND SCHEDULER
        // =========================

        robot.updateLoop();
    }
}