package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystem;

import java.util.function.BooleanSupplier;

public class armHoldCommand extends CommandBase {
    private final armSubsystem arm;
    private final BooleanSupplier jogUp;
    private final BooleanSupplier jogDown;
    private final BooleanSupplier climbing;

    public armHoldCommand(armSubsystem arm, BooleanSupplier jogUp, BooleanSupplier jogDown, BooleanSupplier climbing) {
        this.arm = arm;
        this.jogUp = jogUp;
        this.jogDown = jogDown;
        this.climbing = climbing;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.holdCurrentAngle();
    }

    @Override
    public void execute() {
        if (climbing.getAsBoolean()) {
            arm.stop();
        } else if (jogUp.getAsBoolean()) {
            arm.setPower(robotConstants.Arm.UP_POWER);
        } else if (jogDown.getAsBoolean()) {
            arm.setPower(robotConstants.Arm.DOWN_POWER);
        } else if (!arm.isHolding()) {
            arm.holdCurrentAngle();
        }
    }

    @Override public boolean isFinished() { return false; }
    @Override public void end(boolean interrupted) { arm.stop(); }
}
