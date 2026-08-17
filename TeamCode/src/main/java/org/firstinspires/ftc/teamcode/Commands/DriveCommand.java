package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    private final DriveSubsystem drive;

    private final DoubleSupplier leftSupplier;
    private final DoubleSupplier rightSupplier;

    public DriveCommand(
            DriveSubsystem drive,
            DoubleSupplier leftSupplier,
            DoubleSupplier rightSupplier
    ) {
        this.drive = drive;
        this.leftSupplier = leftSupplier;
        this.rightSupplier = rightSupplier;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        double left = leftSupplier.getAsDouble();
        double right = rightSupplier.getAsDouble();

        if(Math.abs(left) < 0.05)
            left = 0;

        if(Math.abs(right) < 0.05)
            right = 0;

        drive.tankDrive(left, right);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
