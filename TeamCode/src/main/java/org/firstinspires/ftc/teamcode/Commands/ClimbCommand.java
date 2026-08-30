package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;

public class ClimbCommand extends CommandBase {

    private final Robot robot = Robot.getInstance();
    private final FeederClimbSubsystem.State state;

    public ClimbCommand(FeederClimbSubsystem.State state) {
        this.state = state;

        addRequirements(robot.feederClimb);
    }

    @Override
    public void initialize() {
        robot.feederClimb.setState(state);
    }

    @Override
    public void end(boolean interrupted) {
        robot.feederClimb.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}