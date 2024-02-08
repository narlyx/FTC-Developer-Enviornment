package org.firstinspires.ftc.teamcode.helpers;

import com.acmerobotics.dashboard.FtcDashboard;
import com.chesterlk.ftc.tweetybird.TweetyBirdProcessor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * The purpose of this class is to globally provide hardware configuration + other variables
 */
public class HardwareMap {
    /**
     * Opmode Reference
     */
    private LinearOpMode opMode;
    private com.qualcomm.robotcore.hardware.HardwareMap hwMap;

    /**
     * TweetyBird Reference
     */
    public TweetyBirdProcessor tweetyBird;

    /**
     * Ftc Dashboard Reference
     */
    public FtcDashboard ftcDashboard = FtcDashboard.getInstance();

    /**
     * Constructor
     */
    public HardwareMap(LinearOpMode importedOpmode) {
        //Pull OpMode
        opMode = importedOpmode;

        //Setting HardwareMap
        hwMap = importedOpmode.hardwareMap;
    }

    /**
     * Init
     */
    public void init() {

    }
}
