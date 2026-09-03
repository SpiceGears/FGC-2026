package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;

import java.util.function.BooleanSupplier;

public class feederCommand extends CommandBase {
    private final feederSubsystem feeder;
    private final superStructure structure;
    private final BooleanSupplier manualForward;
    private final BooleanSupplier manualReverse;
    private final BooleanSupplier shootHeld;

    public feederCommand(feederSubsystem feeder, superStructure structure,
                              BooleanSupplier manualForward, BooleanSupplier manualReverse, BooleanSupplier shootHeld) {
        this.feeder = feeder;
        this.structure = structure;
        this.manualForward = manualForward;
        this.manualReverse = manualReverse;
        this.shootHeld = shootHeld;
        addRequirements(feeder);
    }

    @Override
    public void execute() {
        if (manualForward.getAsBoolean()) {
            feeder.setPower(robotConstants.Feeder.UP_POWER);
        } else if (manualReverse.getAsBoolean()) {
            feeder.setPower(robotConstants.Feeder.DOWN_POWER);
        } else {
            boolean feederGateOpen = structure.atSpeed() && structure.isShooterReadyToFeed();
            if (shootHeld.getAsBoolean() && feederGateOpen) {
                feeder.feed();
            } else {
                feeder.stop();
            }
        }
    }

    @Override public boolean isFinished() { return false; }
    @Override public void end(boolean interrupted) { feeder.stop(); }
}
