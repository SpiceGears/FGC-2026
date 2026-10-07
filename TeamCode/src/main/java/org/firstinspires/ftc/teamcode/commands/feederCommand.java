package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.constants.flywheelPIDF;
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
    private boolean rpmGateOpen = false;

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
        } else if (manualReverse.getAsBoolean() || structure.isJammed()) {
            feeder.setPower(robotConstants.Feeder.DOWN_POWER);
        } else {
            // Hysteresis: gate closes when RPM drops below the threshold and reopens only after recovering to FEEDER_RECOVERY_RPM.
            double rpm = Math.abs(structure.getVelocityRpm());
            if (!structure.isShooterReadyToFeed() || rpm < robotConstants.Feeder.FEEDER_THRESHOLD_RPM) {
                rpmGateOpen = false;
            } else if (rpm >= flywheelPIDF.Shooter.FEEDER_RECOVERY_RPM) {
                rpmGateOpen = true;
            }

            if (shootHeld.getAsBoolean() && rpmGateOpen) {
                feeder.feed();
            } else {
                feeder.stop();
            }
        }
    }

    @Override public boolean isFinished() { return false; }
    @Override public void end(boolean interrupted) { feeder.stop(); }
}
