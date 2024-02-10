package org.firstinspires.ftc.teamcode.helpers;

import com.acmerobotics.dashboard.FtcDashboard;
import com.chesterlk.ftc.tweetybird.TweetyBirdProcessor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * The purpose of this class is to globally provide hardware configuration + other variables
 */
public class HardwareMap {
    /**
     * Opmode Reference
     */
    private LinearOpMode opMode;

    /**
     * TweetyBird Reference
     */
    public TweetyBirdProcessor tweetyBird;

    /**
     * Ftc Dashboard Reference
     */
    public FtcDashboard ftcDashboard = FtcDashboard.getInstance();

    /**
     * Defining Hardware
     */
    //Motors
    public DcMotor FL,FR,BL,BR;

    //Encoders
    public DcMotor leftEncoder,rightEncoder,middleEncoder;


    /**
     * Constructor
     */
    public HardwareMap(LinearOpMode importedOpmode) {
        //Pull OpMode
        opMode = importedOpmode;
    }

    /**
     * Init
     */
    public void initGeneral() {
        com.qualcomm.robotcore.hardware.HardwareMap hwMap = opMode.hardwareMap;

        //Motors
        FL = hwMap.get(DcMotor.class, "FL");
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FL.setDirection(DcMotorSimple.Direction.FORWARD);

        FR = hwMap.get(DcMotor.class, "FR");
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);

        BL = hwMap.get(DcMotor.class, "BL");
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);

        BR = hwMap.get(DcMotor.class, "BR");
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        /* TODO: Fix when actually setup
        //Encoders
        leftEncoder = hwMap.get(DcMotor.class, "FR");
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightEncoder = hwMap.get(DcMotor.class, "BL");
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        middleEncoder = hwMap.get(DcMotor.class, "BR");
        middleEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);*/
    }

    /**
     * Init TweetyBird
     */
    public void initTweetyBird() {
        tweetyBird = new TweetyBirdProcessor.Builder()
                //Setting opmode
                .setOpMode(opMode)

                //Hardware Config
                .setFrontLeftMotor(FL)
                .setFrontRightMotor(FR)
                .setBackLeftMotor(BL)
                .setBackRightMotor(BR)

                .setLeftEncoder(leftEncoder)
                .setRightEncoder(rightEncoder)
                .setMiddleEncoder(middleEncoder)

                .flipLeftEncoder(true)
                .flipRightEncoder(true)
                .flipMiddleEncoder(true)

                .setInchesBetweenSideEncoders(12+5.0/8.0)
                .setInchesToBackEncoder(-3)

                .setTicksPerEncoderRotation(8192)
                .setEncoderWheelRadius(1)

                //Other Config
                .setMinSpeed(0.25)
                .setMaxSpeed(0.8)
                .setStartSpeed(0.4)
                .setSpeedModifier(0.04)
                .setStopForceSpeed(0.1)

                .setCorrectionOverpowerDistance(5)
                .setDistanceBuffer(1)
                .setRotationBuffer(8)

                .build();
    }


}
