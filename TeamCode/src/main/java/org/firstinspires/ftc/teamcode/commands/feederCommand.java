package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.feederSubsystem;

public class feederCommand extends CommandBase {
    private final feederSubsystem feeder;
    private final double power;

    public feederCommand(feederSubsystem feeder, double power) {
        this.feeder = feeder;
        this.power = power;
        addRequirements(feeder);
    }

    @Override public void initialize() { feeder.setPower(power); }
    @Override public boolean isFinished() { return false; }
    @Override public void end(boolean interrupted) { feeder.stop(); }
}
