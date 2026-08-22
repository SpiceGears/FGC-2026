package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.shootCommand;
import org.firstinspires.ftc.teamcode.subsystems.clutchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.superStructure;

@TeleOp
public class polandFinal extends OpMode {
    private superStructure structure;
    private clutchSubsystem clutch;
    private shootCommand shootCommand;
    private boolean lastTrigger = false;

    @Override
    public void init() {
        structure = new superStructure(hardwareMap);
        clutch = new clutchSubsystem(hardwareMap);
        shootCommand = new shootCommand(structure, clutch);
    }

    @Override
    public void loop() {
        boolean pressed = gamepad1.right_trigger > 0.5;

        if (pressed && !lastTrigger) shootCommand.initialize();
        if (pressed) shootCommand.execute();
        if (!pressed && lastTrigger) shootCommand.end(true);

        lastTrigger = pressed;
        structure.periodic();
    }
}
