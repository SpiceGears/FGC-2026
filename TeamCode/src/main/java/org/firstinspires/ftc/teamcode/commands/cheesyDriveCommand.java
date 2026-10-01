package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.RunCommand;
import org.firstinspires.ftc.teamcode.subsystems.driveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class cheesyDriveCommand extends RunCommand {
    public cheesyDriveCommand(driveSubsystem drive, DoubleSupplier throttle, DoubleSupplier wheel, BooleanSupplier quickTurn) {
        super(() -> drive.cheesyDrive(throttle.getAsDouble(), wheel.getAsDouble(), quickTurn.getAsBoolean()), drive);
    }
}
