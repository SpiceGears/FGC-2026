package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.armSubsystem;

public class armCommand extends CommandBase {
    private final armSubsystem arm;
    private final double power;

    public armCommand(armSubsystem arm, double power) {
        this.arm = arm;
        this.power = power;
        addRequirements(arm);
    }

    @Override public void initialize() { arm.setPower(power); }
    @Override public boolean isFinished() { return false; }
    @Override public void end(boolean interrupted) { arm.stop(); }
}
