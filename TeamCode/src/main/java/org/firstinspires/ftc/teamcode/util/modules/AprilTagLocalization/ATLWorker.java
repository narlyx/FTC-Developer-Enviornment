package org.firstinspires.ftc.teamcode.util.modules.AprilTagLocalization;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class ATLWorker extends Thread {

    ATLProcessor processor;

    public FtcDashboard ftcDashboard = FtcDashboard.getInstance(); //TODO REMOVE LATER

    public ATLWorker(ATLProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run() {
        processor.opMode.waitForStart();

        while ( processor.opMode.opModeIsActive() ) {
            TelemetryPacket packet = new TelemetryPacket(); //TODO REMOVE

            //Gathering a list of detected tags
            List<AprilTagDetection> currentDetections = processor.aprilTagProcessor.getDetections();

            //Parsing list
            for ( AprilTagDetection detection : currentDetections ) {
                Tag tag = processor.tags.get(detection.id);
                if ( tag!=null ) {
                    packet.fieldOverlay()
                            .setFill("Green")
                            .fillCircle(tag.getX(),tag.getY(),2);
                }

                packet.fieldOverlay()
                        .setFill("Blue")
                        .fillCircle(detection.ftcPose.x,detection.ftcPose.y,1);
            }

            this.processor.opMode.telemetry.addLine("Started");
            this.processor.opMode.telemetry.update();
            ftcDashboard.sendTelemetryPacket(packet); //TODO REMOVE
        }
    }
}
