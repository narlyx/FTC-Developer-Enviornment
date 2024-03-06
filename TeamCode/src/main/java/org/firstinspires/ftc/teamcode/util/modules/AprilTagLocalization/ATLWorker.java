package org.firstinspires.ftc.teamcode.util.modules.AprilTagLocalization;

import com.acmerobotics.dashboard.FtcDashboard;

public class ATLWorker extends Thread {

    ATLProcessor processor;

    public FtcDashboard ftcDashboard = FtcDashboard.getInstance(); //TODO REMOVE LATER

    public ATLWorker(ATLProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run() {
        while (processor.opMode.opModeIsActive()) {
            
        }
    }
}
