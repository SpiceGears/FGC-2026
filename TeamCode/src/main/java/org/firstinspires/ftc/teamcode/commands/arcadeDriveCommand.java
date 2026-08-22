package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.RunCommand;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystem;

import java.util.function.DoubleSupplier;

public class arcadeDriveCommand extends RunCommand {
    public arcadeDriveCommand(driveSubsystem drive, DoubleSupplier forward, DoubleSupplier turn) {
        super(() -> drive.arcade(forward.getAsDouble(), turn.getAsDouble()), drive);
    }
}
