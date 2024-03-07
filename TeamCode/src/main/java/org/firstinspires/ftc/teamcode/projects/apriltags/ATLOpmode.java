package org.firstinspires.ftc.teamcode.projects.apriltags;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.HardwareMap;
import org.firstinspires.ftc.teamcode.util.modules.AprilTagLocalization.ATLProcessor;

@Autonomous(name = "ATL Testing", group = "a")
//@Disabled //Do not forget to remove this line to make it active
public class ATLOpmode extends LinearOpMode {

    /**
     * HardwareMap Reference
     */
    private final HardwareMap robot = new HardwareMap(this);

    /**
     * Opmode Start
     */
    @Override
    public void runOpMode() {
        //Initialize
        robot.initGeneral();
        robot.initTweetyBird();
        robot.initVision();

        //robot.tweetyBird.disengage();
        robot.tweetyBird.engage();

        //Starting ATL
        ATLProcessor atlProcessor = new ATLProcessor.Builder()
                .setOpMode(this)
                .setAprilTagProcessor(robot.aprilTag)
                .setTweetyBirdProcessor(robot.tweetyBird)
                .addTag(7,-1,20,0)
                .addTag(8,1,20,0)
                .build();

        waitForStart();
        //Run

        while (opModeIsActive()) {
            if (gamepad1.a) {
                atlProcessor.scan();
            }
        }

        robot.tweetyBird.stop();
    }
}
