package org.firstinspires.ftc.teamcode.projects.atl;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.HardwareMap;

@Autonomous(name = "ATL Cycle", group = "a")
//@Disabled //Do not forget to remove this line to make it active
public class ATLCycle extends LinearOpMode {

    /**
     * HardwareMap Reference
     */
    private final HardwareMap robot = new HardwareMap(this);

    private ATLProcessor atlProcessor;

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
        atlProcessor = new ATLProcessor.Builder()
                .setOpMode(this)
                .setAprilTagProcessor(robot.aprilTag)
                .setTweetyBirdProcessor(robot.tweetyBird)
                .build();

        waitForStart();
        //Run

        robot.tweetyBird.speedLimit(0.4);

        robot.tweetyBird.straightLineTo(0,10,0);
        robot.tweetyBird.straightLineTo(10,30,90);

        scan();

        robot.tweetyBird.straightLineTo(23,30,90);

        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();

        robot.tweetyBird.straightLineTo(0,10,80);

        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();

        scan();

        robot.tweetyBird.straightLineTo(-50,13,90);

        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();

        robot.tweetyBird.straightLineTo(-60,20,90);

        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();

        robot.tweetyBird.straightLineTo(-60,30,-90);

        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();
        robot.tweetyBird.waitWhileBusy();

        scan();


        while (opModeIsActive()) {
        }

        robot.tweetyBird.stop();
    }

    private void scan() {
        sleep(500);
        for (int i = 0; i<3; i++) {
            atlProcessor.scan();
            sleep(500);
        }
    }
}
