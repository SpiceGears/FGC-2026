package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;

import java.util.function.BooleanSupplier;

public class clutchCommand extends CommandBase {
    private final clutchSubsystem clutch;
    private final BooleanSupplier intakeHeld;
    private final BooleanSupplier shootHeld;
    private final BooleanSupplier reverseHeld;

    public clutchCommand(clutchSubsystem clutch, BooleanSupplier intakeHeld, BooleanSupplier shootHeld, BooleanSupplier reverseHeld) {
        this.clutch = clutch;
        this.intakeHeld = intakeHeld;
        this.shootHeld = shootHeld;
        this.reverseHeld = reverseHeld;
        addRequirements(clutch);
    }

    @Override
    public void execute() {
        if (intakeHeld.getAsBoolean()) {
            clutch.intake();
        } else if (shootHeld.getAsBoolean() || reverseHeld.getAsBoolean()) {
            clutch.shoot();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        clutch.stop();
    }
}
