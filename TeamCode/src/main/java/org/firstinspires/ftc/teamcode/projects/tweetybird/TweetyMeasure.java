package org.firstinspires.ftc.teamcode.projects.tweetybird;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.HardwareMap;

@Autonomous(name = "TweetyBird Tape Measure",group = "z")
//@Disabled //DO NOT FORGET TO UNCOMMENT THIS FOR USE
public class TweetyMeasure extends LinearOpMode {
    HardwareMap robot;

    @Override
    public void runOpMode() {
        //Init Robot
        robot = new HardwareMap(this);
        robot.initGeneral();
        robot.initVision();

        //robot.setDropperPosition(Hardwaremap.DroperPosition.CLOSED);

        //Waiting for start
        waitForStart();
        robot.tweetyBird.disengage();

        while (opModeIsActive()) {
            telemetry.addData("X",robot.tweetyBird.getX());
            telemetry.addData("Y",robot.tweetyBird.getY());
            telemetry.addData("Z",robot.tweetyBird.getZ());
            telemetry.update();
        }

        robot.tweetyBird.stop();
    }
}
