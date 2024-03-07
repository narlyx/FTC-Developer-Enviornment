package org.firstinspires.ftc.teamcode.util.modules.AprilTagLocalization;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.opencv.core.Point3;

import java.util.ArrayList;
import java.util.List;

public class ATLWorker extends Thread {

    ATLProcessor processor;

    protected boolean auto = false;

    public ATLWorker(ATLProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run() {
        processor.opMode.waitForStart();

        while ( processor.opMode.opModeIsActive() && auto) {
            scan();

            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void scan() {
        TelemetryPacket packet = new TelemetryPacket();

        //Gathering a list of detected tags
        List<AprilTagDetection> currentDetections = processor.aprilTagProcessor.getDetections();

        //Estimated Positions
        List<Vector3> estimatedPositions = new ArrayList<>();

        //Parsing list
        for ( AprilTagDetection detection : currentDetections ) {
            Tag tag = processor.tags.get(detection.id);
            if ( tag!=null && detection.metadata != null ) {

                double tagX = tag.getX();
                double tagY = tag.getY();
                double tagZ = tag.getZ();

                double distance = detection.ftcPose.y;
                double lateralDistance = detection.ftcPose.x;
                double yawDistance = Math.toRadians(detection.ftcPose.yaw);

                double camX = tagX - ( (distance * Math.sin((tagZ + yawDistance) )) + (lateralDistance * Math.sin((tagZ + yawDistance) + 90)) );
                double camY = tagY - ( (distance * Math.cos((tagZ + yawDistance) )) + (lateralDistance * Math.cos((tagZ + yawDistance)  + 90)) );
                double camZ = tagZ + yawDistance;

                double robotX = camX;
                double robotY = camY;
                double robotZ = camZ;

                estimatedPositions.add(new Vector3(robotX,robotY,robotZ));

                packet.fieldOverlay()
                        .setFill("Blue")
                        //.fillCircle(detection.ftcPose.x+processor.tweetyBirdProcessor.getX(),detection.ftcPose.y+processor.tweetyBirdProcessor.getY(),1);
                        .fillCircle(tagX,tagY,1);
            }

            if ( tag==null && detection.metadata != null ) {
                packet.fieldOverlay()
                        .setFill("Black")
                        .fillCircle(detection.ftcPose.x+processor.tweetyBirdProcessor.getX(),detection.ftcPose.y+processor.tweetyBirdProcessor.getY(),1);
            }
        }

        if (estimatedPositions.size()>0) {
            double estimatedX = 0;
            double estimatedY = 0;
            double estimatedZ = 0;

            for ( Vector3 vector3 : estimatedPositions ) {
                estimatedX += vector3.getX();
                estimatedY += vector3.getY();
                estimatedZ += vector3.getZ();
            }

            estimatedX = estimatedX/estimatedPositions.size();
            estimatedY = estimatedY/estimatedPositions.size();
            estimatedZ = estimatedZ/estimatedPositions.size();

            processor.tweetyBirdProcessor.resetTo(estimatedX,estimatedY,estimatedZ);

            packet.put("Robot X",estimatedX);
            packet.put("Robot Y",estimatedY);
            packet.put("Robot Z",estimatedZ);

            packet.fieldOverlay()
                    .setFill("Green")
                    .fillCircle(estimatedX,estimatedY,2);
        }

        processor.ftcDashboard.sendTelemetryPacket(packet);
    }
}
