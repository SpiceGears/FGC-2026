package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

public class ShooterCommand extends CommandBase {
    private final MechanismSubsystem mechanism;
    private final ClutchSubsystem clutch;
    private final FeederClimbSubsystem feeder;

    public ShooterCommand(MechanismSubsystem mechanism, ClutchSubsystem clutch, FeederClimbSubsystem feeder) {
        this.mechanism = mechanism;
        this.clutch = clutch;
        this.feeder = feeder;

        addRequirements(mechanism, clutch, feeder);
    }

    @Override
    public void initialize() {
        clutch.setMode(ClutchSubsystem.Mode.SHOOTER);
        mechanism.setShooterRPM(Constants.ShooterConstants.TARGET_RPM);
    }

    @Override
    public void execute() {
        if(mechanism.atShooterSpeed()) {
            feeder.runFeeder(1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        mechanism.stop();
        clutch.setMode(ClutchSubsystem.Mode.SHOOTER);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
