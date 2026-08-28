package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.cheesyDriveCommand;
import org.firstinspires.ftc.teamcode.commands.armCommand;
import org.firstinspires.ftc.teamcode.commands.feederCommand;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;

@TeleOp
public class simpleTeleOp extends CommandOpMode {

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
                () -> -gamepad1.left_stick_y,
                () -> gamepad1.right_stick_x,
                () -> gamepad1.right_bumper
        ));



        driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whileHeld(new feederCommand(feeder, robotConstants.Feeder.UP_POWER));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whileHeld(new feederCommand(feeder, robotConstants.Feeder.DOWN_POWER));




        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whileHeld(new armCommand(arm, robotConstants.Arm.UP_POWER));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileHeld(new armCommand(arm, robotConstants.Arm.DOWN_POWER));

        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
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

        telemetry.addData("Mode", mode);
        telemetry.addData("Structure Reversed", structure.isReversed());
        telemetry.update();
    }
}
