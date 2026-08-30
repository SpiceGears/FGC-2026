package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Subsystems.MechanismSubsystem;
import org.firstinspires.ftc.teamcode.Constants.IntakeConstants;

public class IntakeCommand extends CommandBase {

    private final Robot robot = Robot.getInstance();
    private final double power;

    public IntakeCommand(double power) {
        this.power = power;

        addRequirements(robot.mechanism);
    }

    @Override
    public void initialize() {
        robot.mechanism.setMode(MechanismSubsystem.Mode.INTAKE);
        robot.mechanism.setPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        robot.mechanism.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
