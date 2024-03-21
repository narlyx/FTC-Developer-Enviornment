package org.firstinspires.ftc.teamcode.projects.tweetybird;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.HardwareMap;

@Autonomous(name = "TweetyBird Debugger",group = "z")
//@Disabled //DO NOT FORGET TO UNCOMMENT THIS FOR USE
public class TweetyDebug extends LinearOpMode {
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
        robot.tweetyBird.engage();

        while (opModeIsActive());

        robot.tweetyBird.stop();
    }
}
