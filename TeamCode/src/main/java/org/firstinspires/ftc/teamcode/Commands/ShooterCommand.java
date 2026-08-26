package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

public class ShooterCommand extends CommandBase {

    private final MechanismSubsystem mechanism;
    private final ClutchSubsystem clutch;
    private final FeederClimbSubsystem feeder;

    private final ElapsedTime clutchTimer = new ElapsedTime();
    private boolean shooterStarted = false;

    public ShooterCommand(
            MechanismSubsystem mechanism,
            ClutchSubsystem clutch,
            FeederClimbSubsystem feeder
    ) {
        this.mechanism = mechanism;
        this.clutch = clutch;
        this.feeder = feeder;

        addRequirements(mechanism, clutch /*feeder*/);
    }

    @Override
    public void initialize() {
        feeder.stop();
        mechanism.stop();
        clutch.setMode(ClutchSubsystem.Mode.SHOOTER);

        shooterStarted = false;
        clutchTimer.reset();
    }

    @Override
    public void execute() {
        if (!shooterStarted) {
            if (clutchTimer.seconds() < ShooterConstants.CLUTCH_DELAY_SECONDS) {
                return;
            }

            if(mechanism.atShooterSpeed()) {
                feeder.runFeeder(1);
            }

            mechanism.setShooterRPM(
                    ShooterConstants.TARGET_RPM
            );
            shooterStarted = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        feeder.stop();
        mechanism.stop();
        clutch.setMode(ClutchSubsystem.Mode.SHOOTER);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
