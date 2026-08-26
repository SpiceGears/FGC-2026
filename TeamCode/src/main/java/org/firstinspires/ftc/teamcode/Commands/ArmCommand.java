package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Constants.ArmConstants;

public class ArmCommand extends CommandBase {
    private final FeederClimbSubsystem arm;

    public ArmCommand(FeederClimbSubsystem arm) {
        this.arm = arm;

        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.deployArm(ArmConstants.POWER);
    }

    @Override
    public boolean isFinished() {
//        return arm.isArmLimit();
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        arm.stopArm();
    }
}
