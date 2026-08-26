package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Constants.FeederConstants;

public class ReverseFeederCommand extends CommandBase {
    private final FeederClimbSubsystem feeder;

    public ReverseFeederCommand(FeederClimbSubsystem feeder) {
        this.feeder = feeder;

        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        feeder.runFeeder(FeederConstants.REVERSE_POWER);
    }

    @Override
    public void end(boolean interrupted) {
        feeder.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
