package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.armCommand;
import org.firstinspires.ftc.teamcode.commands.cheesyDriveCommand;
import org.firstinspires.ftc.teamcode.constants.flywheelPIDF;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
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
                () -> -gamepad1.left_stick_y,
                () -> gamepad1.right_stick_x,
                () -> gamepad1.right_bumper
        ));

        driver.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON)
                .whileHeld(new armCommand(arm, robotConstants.Arm.UP_POWER));
        driver.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
                .whileHeld(new armCommand(arm, robotConstants.Arm.DOWN_POWER));
    }

    @Override
    public void run() {
        super.run();

        boolean shootHeld = gamepad1.right_trigger > 0.5;
        boolean intakeHeld = gamepad1.left_trigger > 0.5;
        boolean reverseHeld = gamepad1.left_bumper;

        if (shootHeld) {
            clutch.shoot();
            structure.spinUpShooter(reverseHeld);
        } else if (intakeHeld) {
            clutch.intake();
            structure.spinUpIntake(reverseHeld);
        } else {
            structure.stop();
        }

        boolean feederManualForward = gamepad1.dpad_right;
        boolean feederManualReverse = gamepad1.dpad_left;

        if (feederManualForward) {
            feeder.setPower(robotConstants.Feeder.UP_POWER);
        } else if (feederManualReverse) {
            feeder.setPower(robotConstants.Feeder.DOWN_POWER);
        } else {
            boolean feederGateOpen = structure.getVelocityRpm()
                    >= (flywheelPIDF.Shooter.TARGET_RPM - flywheelPIDF.Shooter.RPM_TOLERANCE);
            if (shootHeld && feederGateOpen) {
                feeder.feed();
            } else {
                feeder.stop();
            }
        }

        telemetry.addData("Mode", structure.getMode());
        telemetry.addData("Velocity RPM", structure.getVelocityRpm());
        telemetry.addData("At Speed", structure.atSpeed());
        telemetry.addData("Arm Position", arm.getPosition());
        telemetry.addData("Reversed", reverseHeld);
        telemetry.addData("Feeder Mode", (feederManualForward || feederManualReverse) ? "Manual" : "Auto");
        telemetry.addData("TargetRPM", flywheelPIDF.Shooter.TARGET_RPM);
        telemetry.update();
    }
}
