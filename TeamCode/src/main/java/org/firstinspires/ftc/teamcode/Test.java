package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;

@TeleOp(name="FGC-2026", group="Linear Opmode")
public class Test extends LinearOpMode {

    private final Drive drivetrain= new Drive(this);

    @Override
    public void runOpMode() {
        drivetrain.init();

        telemetry.addData("Status", "Initalized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();

        drivetrain.stop();

        while(opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            telemetry.addData("Forward", drive);
            telemetry.addData("Turn", turn);
            drivetrain.drive(drive, turn);

            if(gamepad1.cross) {
                drivetrain.expand(-1);
            } else if(gamepad1.circle) {
                drivetrain.expand(1);
            } else {
                drivetrain.expand(0);
            }



            telemetry.update();
        }
    }
}
