package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.Trigger;

import org.firstinspires.ftc.teamcode.Commands.ArmCommand;
import org.firstinspires.ftc.teamcode.Commands.ClimbCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Commands.FeederCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseArmCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseClimbCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseFeederCommand;
import org.firstinspires.ftc.teamcode.Commands.ReverseIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;
import org.firstinspires.ftc.teamcode.Constants.OperatorConstants;

@TeleOp(name = "Poland Final")
public class PolandFinal extends CommandOpMode {

    private DriveSubsystem drive;
    private MechanismSubsystem mechanism;
    private ClutchSubsystem clutch;
    private FeederClimbSubsystem feeder;

    private Trigger intakeTrigger;
    private Trigger shooterTrigger;

    private Trigger reverseIntakeButton;
    private Trigger reverseFeeder;
    private Trigger climbUpButton;
    private Trigger climbDownButton;
    private Trigger armDeployButton;
    private Trigger armReverseButton;

    @Override
    public void initialize() {

        drive = new DriveSubsystem(hardwareMap);
        mechanism = new MechanismSubsystem(hardwareMap);
        clutch = new ClutchSubsystem(hardwareMap);
        feeder = new FeederClimbSubsystem(hardwareMap);

        /*
         * Physical controls
         *
         * RT -> Intake
         * LT -> Shooter
         * RB -> Reverse intake
         * DPad Up -> Climb
         * DPad Down -> Reverse climb
         * Square -> Deploy arm
         * Cross -> Reverse arm
         */

        intakeTrigger = new Trigger(
                () -> gamepad1.right_trigger > OperatorConstants.TRIGGER_THRESHOLD
        );

        shooterTrigger = new Trigger(
                () -> gamepad1.left_trigger > OperatorConstants.TRIGGER_THRESHOLD
        );

        reverseIntakeButton = new Trigger(
                () -> gamepad1.right_bumper
        );

        climbUpButton = new Trigger(
                () -> gamepad1.dpad_up
        );

        climbDownButton = new Trigger(
                () -> gamepad1.dpad_down
        );

        reverseFeeder = new Trigger(
                () -> gamepad1.left_bumper
        );

        armReverseButton = new Trigger(
                () -> gamepad1.cross
        );

       drive.setDefaultCommand(
               new DriveCommand(
                       drive,
                       () -> -gamepad1.right_stick_x,
                       () -> -gamepad1.left_stick_y
               )
       );

        configureBindings();
    }

    private void configureBindings() {

        intakeTrigger.whileActiveOnce(
                new IntakeCommand(
                        mechanism,
                        clutch
                )
        );

        shooterTrigger.whileActiveOnce(
                new ShooterCommand(
                        mechanism,
                        clutch,
                        feeder
                )
        );

        reverseIntakeButton.whileActiveOnce(
                new ReverseIntakeCommand(
                        mechanism,
                        clutch
                )
        );

        climbUpButton.whileActiveOnce(
                new ClimbCommand(
                        feeder
                )
        );

        climbDownButton.whileActiveOnce(
                new ReverseClimbCommand(
                        feeder
                )
        );

        reverseFeeder.whileActiveOnce(
                new ReverseFeederCommand(
                        feeder
                )
        );

        armReverseButton.whileActiveOnce(
                new ReverseArmCommand(
                        feeder
                )
        );
    }
}
