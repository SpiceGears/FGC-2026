package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

public class ShooterCommand extends CommandBase {

    private final Robot robot = Robot.getInstance();
    private final ElapsedTime timer = new ElapsedTime();

    private boolean feeding = false;

    public ShooterCommand() {
        addRequirements(
                robot.mechanism,
                robot.feederClimb
        );
    }

    @Override
    public void initialize() {
        feeding = false;
        timer.reset();

        robot.mechanism.setMode(MechanismSubsystem.Mode.SHOOTER);
        robot.mechanism.setShooterRPM(Constants.ShooterConstants.TARGET_RPM);
        robot.feederClimb.stop();
    }

    @Override
    public void execute() {
        if (!feeding &&
                (robot.mechanism.atShooterSpeed()
                        || timer.seconds() >= 5)) {

            robot.feederClimb.setState(FeederClimbSubsystem.State.FEED);
            feeding = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        robot.mechanism.stop();
        robot.feederClimb.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}