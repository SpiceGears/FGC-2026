package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;

public class ClimbCommand extends CommandBase {
    private final FeederClimbSubsystem climb;

    public ClimbCommand(FeederClimbSubsystem climb) {
        this.climb = climb;

        addRequirements(climb);
    }

    @Override
    public void initialize() {
        climb.runFeeder(1);
    }

    @Override
    public void end(boolean interrupted) {
        climb.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
