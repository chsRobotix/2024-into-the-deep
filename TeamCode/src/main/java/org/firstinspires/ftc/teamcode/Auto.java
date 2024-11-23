package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.HashSet;
import java.util.List;

@Autonomous(name = "Auto")
public class Auto extends CustomLinearOp {
    /**
     * Raise the arm 28.5 inches and ejects the sample.
     */
    public void raiseArmAndEject() {
        // Set target degrees to reach the height of 28.5 inches
        double targetDegrees = 170.0; // Replace with actual degrees needed to reach 28.5 inches

        // Move the arm to the calculated target position
        // ARM.rotateArmToAngle(targetDegrees);
        // autoSleep(ARM.getRotationMotor());

        // Eject the object using the claw
        try {
            CLAW.ejectIntake();

        } catch (IllegalStateException e) {
            telemetry.addLine("Failed to eject intake");
        }
    }

    public void pickUpSample() {
        // ARM.rotateArmToAngle(0);
        // CLAW.startIntake();
        // sleep(100);
        // CLAW.stopIntake();
    }

    /**
     * When on the near side(i.e. next to bucket),
     * drive to the bucket.
     */
    public void nearDriveToBucket() {
        int targetTagId = (ALLIANCE_COLOR == AllianceColor.RED) ? 16 : 13;

        // Find the right AprilTag
        List<AprilTagDetection> currentDetections = WEBCAM.getAprilTag().getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == targetTagId) {
                // Drive to the AprilTag
                double forwardDistance = detection.ftcPose.y + WEBCAM.getPoseAdjust()[1] - 12;
                double sidewaysDistance = detection.ftcPose.x + WEBCAM.getPoseAdjust()[0] - 12;
                WHEELS.driveDistance(sidewaysDistance, forwardDistance);
            }
        }
    }

    private void performNearBasketActions() {
        telemetry.addLine("Starting Near Basket Action");
        telemetry.update();

        // Step 1: Lift arm 45 degrees
        double targetDegrees = -145; // Replace with actual degrees needed

        // ARM.rotateArmToAngle(targetDegrees); // Move the arm to the calculated target position
        // sleep(1500);

        // Step 2: Extend Arm
        // ARM.foldArmToAngle(75); // Adjust this value as needed
        // sleep(500);

        // Step 3: Rotate Claw
        // CLAW.startIntake();
        // sleep(500);

        // Step 4: Strafe left 72 inches
        WHEELS.driveDistance(-72, 0); // Negative X for strafing left
        sleep(500);

        // Step 5: Lift arm 45 degrees again
        // ARM.rotateArmToAngle(ARM.getRotationDegrees() - 45);
        // sleep(500);

        // Step 6: Reverse the claw intake to score
        // CLAW.ejectIntake();
        // sleep(500);

        // Step 7: Rotate servo back
        // CLAW.stopIntake(); // Not too sure if this is what I need to do but this resets servo
        // sleep(500);

        // Step 8: Bring arm down 90 degrees
        // ARM.rotateArmToAngle(ARM.getRotationDegrees() + 90);
        // sleep(500);

        // Step 9: Strafe right 65 inches
        WHEELS.driveDistance(65, 0); // Postive X for strafing right
        sleep(500);

        // Step 10: Drive Forward 72 inches
        WHEELS.driveDistance(0, 72); // Positive Y for forward
        sleep(500);

        // Step 11: Turn right 90 degrees
        WHEELS.turn(90);
        sleep(500);

        // Step 12: Lift arm 35 degrees
        // ARM.rotateArmToAngle( ARM.getRotationDegrees() - 35);
        // sleep(500);

        telemetry.addLine("Finished Near Basket Action");
        telemetry.update();
    }

    private void performFarBasketActions() {
        telemetry.addLine("Starting Far Basket Action");
        telemetry.update();

        // Strafe left 48 inches
        WHEELS.driveDistance(-48, 0); // Negative X for strafing left
        sleep(500);

        telemetry.addLine("Finished Far Basket Actions");
        telemetry.update();
    }

    /**
     * Automatically runs after pressing the "Init" button on the Control Hub
     */
    @Override
    public void runOpMode() {
        super.runOpMode();

        if (TEAM_SIDE == TeamSide.NEAR) {
            performNearBasketActions();
        } else {
            performFarBasketActions();
        }
    }
}