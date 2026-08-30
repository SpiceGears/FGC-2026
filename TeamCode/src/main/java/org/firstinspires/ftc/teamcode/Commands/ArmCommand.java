package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;

public class ArmCommand extends CommandBase {
 
    private final Robot robot = Robot.getInstance();
    private final ArmSubsystem.State state;

    public ArmCommand(ArmSubsystem.State state) {
        this.state = state;

        addRequirements(robot.arm);
    }

    @Override
    public void initialize() {
        robot.arm.setState(state);
    }

    @Override
    public void end(boolean interrupted) {
        robot.arm.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}