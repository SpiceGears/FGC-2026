package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;
import org.firstinspires.ftc.teamcode.Constants.IntakeConstants;

public class IntakeCommand extends CommandBase {

    private final MechanismSubsystem mechanism;
    private final ClutchSubsystem clutch;

    private final ElapsedTime clutchTimer = new ElapsedTime();
    private boolean motorStarted = false;

    public IntakeCommand(
            MechanismSubsystem mechanism,
            ClutchSubsystem clutch
    ) {
        this.mechanism = mechanism;
        this.clutch = clutch;

        addRequirements(mechanism, clutch);
    }

    @Override
    public void initialize() {
        mechanism.stop();

        clutch.setMode(ClutchSubsystem.Mode.INTAKE);

        motorStarted = false;
        clutchTimer.reset();
    }

    @Override
    public void execute() {
        if (!motorStarted && clutchTimer.seconds() >= IntakeConstants.CLUTCH_DELAY_SECONDS) {
            mechanism.setPower(IntakeConstants.POWER);
            motorStarted = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        mechanism.stop();
        clutch.setMode(ClutchSubsystem.Mode.SHOOTER);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
