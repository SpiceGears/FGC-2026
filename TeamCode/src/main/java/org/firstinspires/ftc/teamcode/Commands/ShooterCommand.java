package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

public class ShooterCommand extends CommandBase {
    private final MechanismSubsystem mechanism;
    private final ClutchSubsystem clutch;

    public ShooterCommand(MechanismSubsystem mechanism, ClutchSubsystem clutch) {
        this.mechanism = mechanism;
        this.clutch = clutch;

        addRequirements(mechanism, clutch);
    }

    @Override
    public void initialize() {
        clutch.setMode(ClutchSubsystem.Mode.SHOOTER);
        mechanism.setShooterRPM(Constants.ShooterConstants.TARGET_RPM);
    }

    @Override
    public void execute() {
        if(mechanism.atShooterSpeed()) {
            clutch.setMode(ClutchSubsystem.Mode.FEEDER_AND_SHOOTER);
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
