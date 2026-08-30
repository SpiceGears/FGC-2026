package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.constants.robotConstants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class visionSubsystem extends SubsystemBase {
    private final AprilTagProcessor aprilTag;
    private final VisionPortal visionPortal;

    public visionSubsystem(HardwareMap hwMap) {
        AprilTagLibrary library = new AprilTagLibrary.Builder()
                .addTag(robotConstants.Vision.TAG_ID, "Tag " + robotConstants.Vision.TAG_ID,
                        robotConstants.Vision.TAG_SIZE_METERS, DistanceUnit.METER)
                .build();

        aprilTag = new AprilTagProcessor.Builder()
                .setTagLibrary(library)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, robotConstants.Vision.CAMERA))
                .addProcessor(aprilTag)
                .build();
    }

    public AprilTagDetection getDetection(int id) {
        for (AprilTagDetection d : aprilTag.getDetections()) {
            if (d.id == id) return d;
        }
        return null;
    }

    public boolean isVisible(int id) {
        return getDetection(id) != null;
    }

    public void close() {
        visionPortal.close();
    }

    @Override
    public void periodic() {
    }
}
