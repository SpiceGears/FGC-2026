package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;

import java.util.function.BooleanSupplier;

public class superStructureCommand extends CommandBase {
    private final superStructure structure;
    private final clutchSubsystem clutch;
    private final BooleanSupplier shootHeld;
    private final BooleanSupplier intakeHeld;
    private final BooleanSupplier reversed;

    public superStructureCommand(superStructure structure, clutchSubsystem clutch,
                               BooleanSupplier shootHeld, BooleanSupplier intakeHeld, BooleanSupplier reversed) {
        this.structure = structure;
        this.clutch = clutch;
        this.shootHeld = shootHeld;
        this.intakeHeld = intakeHeld;
        this.reversed = reversed;
        addRequirements(structure, clutch);
    }

    @Override
    public void execute() {
        if (shootHeld.getAsBoolean()) {
            clutch.shoot();
            structure.spinUpShooter(reversed.getAsBoolean());
        } else if (intakeHeld.getAsBoolean()) {
            clutch.intake();
            structure.spinUpIntake(reversed.getAsBoolean());
        } else {
            structure.stop();
        }
    }

    @Override public boolean isFinished() { return false; }
    @Override public void end(boolean interrupted) { structure.stop(); }
}
