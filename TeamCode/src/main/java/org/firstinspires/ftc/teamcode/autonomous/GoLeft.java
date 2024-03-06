package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.HardwareMap;

@Autonomous(name = "Go Left", group = "a")
@Disabled //Do not forget to remove this line to make it active
public class GoLeft extends LinearOpMode {

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
        robot.tweetyBird.disengage();

        waitForStart();
        //Run
        robot.tweetyBird.engage();

        robot.tweetyBird.straightLineTo(-32,0,0);

        while (opModeIsActive());
        robot.tweetyBird.stop();

    }
}
