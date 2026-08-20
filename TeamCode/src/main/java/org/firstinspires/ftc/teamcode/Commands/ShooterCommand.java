package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.ClutchSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.FeederClimbSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;

public class ShooterCommand extends CommandBase {

    private final MechanismSubsystem mechanism;
    private final ClutchSubsystem clutch;
    private final FeederClimbSubsystem feeder;

    private final ElapsedTime spinupTimer = new ElapsedTime();

    public ShooterCommand(
            MechanismSubsystem mechanism,
            ClutchSubsystem clutch,
            FeederClimbSubsystem feeder
    ) {
        this.mechanism = mechanism;
        this.clutch = clutch;
        this.feeder = feeder;

        addRequirements(mechanism, clutch);
    }

    @Override
    public void initialize() {
        clutch.setMode(ClutchSubsystem.Mode.SHOOTER);

        mechanism.setShooterRPM(
                Constants.ShooterConstants.TARGET_RPM
        );

        spinupTimer.reset();
    }

    @Override
    public void execute() {
        boolean shooterReady = mechanism.atShooterSpeed();
        boolean timedOut =
                spinupTimer.seconds()
                        >= Constants.ShooterConstants.SPINUP_TIMEOUT;

        if (shooterReady || timedOut) {
            feeder.runFeeder(1);
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