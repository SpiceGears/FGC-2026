package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;

public class ReverseFeederCommand extends CommandBase {
    private final FeederClimbSubsystem feeder;

    public ReverseFeederCommand(FeederClimbSubsystem feeder) {
        this.feeder = feeder;

        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        feeder.runFeeder(1);
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
