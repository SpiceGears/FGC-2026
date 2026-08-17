package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

public class ReverseIntakeCommand extends CommandBase {
    private final MechanismSubsystem mechanism;
    private final ClutchSubsystem clutch;

    public ReverseIntakeCommand(MechanismSubsystem mechanism, ClutchSubsystem clutch) {
        this.mechanism = mechanism;
        this.clutch = clutch;

        addRequirements(mechanism, clutch);
    }

    @Override
    public void initialize() {
        clutch.setMode(ClutchSubsystem.Mode.INTAKE);
        mechanism.runIntake(-1);
    }

    @Override
    public void end(boolean interrupted) {
        clutch.setMode(ClutchSubsystem.Mode.SHOOTER);
        mechanism.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}