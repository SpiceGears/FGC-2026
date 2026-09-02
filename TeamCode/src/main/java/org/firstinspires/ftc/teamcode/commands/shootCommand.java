package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.constants.flywheelPIDF;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;

public class shootCommand extends CommandBase {
    private final superStructure structure;
    private final clutchSubsystem clutch;

    public shootCommand(superStructure structure, clutchSubsystem clutch) {
        this.structure = structure;
        this.clutch = clutch;
        addRequirements(structure, clutch);
    }

    @Override public void initialize() { structure.setTargetRpm(flywheelPIDF.Shooter.TARGET_RPM); }
    @Override public void execute() { if (structure.atSpeed()) clutch.shoot(); }
    @Override public boolean isFinished() { return false; }
    @Override public void end(boolean interrupted) { structure.stop(); clutch.shoot(); }
}