package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.climberAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.driveAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.intakeAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.shooterAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.fedderAutoLogged;
import org.firstinspires.ftc.teamcode.subsystems.elevatorAutoLogged;

import Ori.Coval.Logging.AutoLogManager;
import Ori.Coval.Logging.Logger.KoalaGamepadLogger;
import Ori.Coval.Logging.Logger.KoalaLog;

@TeleOp(name = "PolandFinal26", group = "Poland")
public class polandfinal extends LinearOpMode {
    private final driveAutoLogged   drive   = new driveAutoLogged(this);
    private final intakeAutoLogged  intake  = new intakeAutoLogged(this);
    private final fedderAutoLogged  fedder  = new fedderAutoLogged(this);
    private final shooterAutoLogged shooter = new shooterAutoLogged(this);
    private final elevatorAutoLogged elevator = new elevatorAutoLogged(this);
    private final climberAutoLogged climber = new climberAutoLogged(this);
    boolean jammed = false;
    boolean prevLeftTrigger = false;
    boolean fire = false;
    static final double spinupTimer = 0.5;
    static final double unjamTimer = 0.5;
    static final double shooterReady = 3500;
    static final double shooterMin = 2000;
    static final double shooterJammed = 100;

    ElapsedTime shooterSpinup = new ElapsedTime();
    ElapsedTime unJam = new ElapsedTime();




    @Override
    public void runOpMode() {
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        KoalaLog.setup(hardwareMap);
        KoalaLog.start();

        KoalaGamepadLogger.register(gamepad1, gamepad2);

        drive.init();
        intake.init();
        fedder.init();
        shooter.init();
        elevator.init();
        climber.init();


        waitForStart();

        try {
            while (opModeIsActive()) {
                AutoLogManager.periodic();

                KoalaLog.log("BatteryVoltage", voltageSensor.getVoltage(), true);
                KoalaLog.log("Jammed", jammed, true);

                drive.drive(-gamepad1.right_stick_x, gamepad1.left_stick_y);

                if (gamepad1.left_trigger > 0.5 && !prevLeftTrigger) {
                    shooterSpinup.reset();
                    jammed = false;
                }

                if (!(gamepad1.left_trigger > 0.5)) {
                    jammed = false;
                    fire = false;
                }
                prevLeftTrigger = gamepad1.left_trigger > 0.5;

                if (gamepad1.left_trigger > 0.5 && !jammed && shooterSpinup.seconds() > spinupTimer && shooter.getShooterRPM() <= shooterJammed) {
                    jammed = true;
                    fire = false;
                    unJam.reset();
                    gamepad1.rumbleBlips(3);
                }

                if (jammed && unJam.seconds() >= unjamTimer) {
                    jammed = false;
                    shooterSpinup.reset();
                }

                if (jammed) {
                    shooter.ShooterReverse();
                    fedder.FedderReverse();
                } else if (gamepad1.left_trigger > 0.5) {
                    shooter.ShooterForward();
                    if (!fire && shooter.getShooterRPM() >= shooterReady) {
                        fire = true;
                    } else if (fire && shooter.getShooterRPM() < shooterMin) {
                        fire = false;
                    }

                    if (fire) {
                        fedder.FedderForward();
                    } else {
                        fedder.stop();
                    }

                } else {
                    shooter.stop();
                    fedder.stop();
                }

                if (jammed) {
                    intake.IntakeForward();
                } else if (gamepad1.left_trigger > 0.5 && fire) {
                    intake.IntakeForward();
                } else if (gamepad1.right_bumper) {
                    intake.IntakeForward();
                } else if (gamepad1.left_bumper) {
                    intake.IntakeReverse();
                } else {
                    intake.stop();
                }

                if (gamepad1.dpad_up) {
                    elevator.elevatorForward();
                } else if(gamepad1.dpad_down) {
                    elevator.elevatorReverse();
                } else {
                    elevator.stop();
                }

                if (gamepad1.dpad_left) {
                    climber.climberForward();
                } else if(gamepad1.dpad_right) {
                    climber.climberReverse();
                } else {
                    climber.stop();
                }

                if (gamepad2.left_trigger > 0.5) {
                    fedder.FedderForward();
                }

                if (gamepad2.right_trigger > 0.5) {
                    shooter.ShooterForward();
                }

                telemetry.addData("Shooter RPM", shooter.getShooterRPM());
                telemetry.update();
            }
        } finally {
            KoalaGamepadLogger.stop();
            KoalaLog.stop();
        }
    }
}
