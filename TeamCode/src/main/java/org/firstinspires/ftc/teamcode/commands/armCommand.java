package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
//import org.firstinspires.ftc.teamcode.constants.armPIDF;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystem;

import java.util.function.BooleanSupplier;

public class armCommand extends CommandBase {
    private final armSubsystem arm;
    private final BooleanSupplier jogUp;
    private final BooleanSupplier jogDown;

    public armCommand(armSubsystem arm, BooleanSupplier jogUp, BooleanSupplier jogDown) {
        this.arm = arm;
        this.jogUp = jogUp;
        this.jogDown = jogDown;
        addRequirements(arm);
    }

//    @Override
//    public void initialize() {
//        arm.setTargetAngle(armPIDF.Arm.START_ANGLE);
//    }

    @Override
    public void execute() {
        if (jogUp.getAsBoolean()) {
            arm.setPower(robotConstants.Arm.UP_POWER);
        } else if (jogDown.getAsBoolean()) {
            arm.setPower(robotConstants.Arm.DOWN_POWER);
        } else {
            arm.stop();
        }

//        double target = arm.getTargetAngle();
//        if (jogUp.getAsBoolean()) {
//            target += armPIDF.Arm.JOG_STEP_DEG;
//        } else if (jogDown.getAsBoolean()) {
//            target -= armPIDF.Arm.JOG_STEP_DEG;
//        }
//        arm.setTargetAngle(target);
    }

    @Override public boolean isFinished() { return false; }
    @Override public void end(boolean interrupted) { arm.stop(); }
}
