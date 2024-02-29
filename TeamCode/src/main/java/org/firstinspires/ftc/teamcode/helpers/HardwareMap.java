package org.firstinspires.ftc.teamcode.helpers;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.chesterlk.ftc.tweetybird.TweetyBirdProcessor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.vision.BlankCameraStream;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.apriltag.AprilTagDetection;

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

    //Vision
    public WebcamName frontCam,backCam;
    public CameraName cameras;

    public VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    public BlankCameraStream cameraStream;


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

        //Encoders
        leftEncoder = hwMap.get(DcMotor.class, "Lencoder");
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftEncoder.setDirection(DcMotorSimple.Direction.REVERSE);

        rightEncoder = hwMap.get(DcMotor.class, "Rencoder");
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightEncoder.setDirection(DcMotorSimple.Direction.FORWARD);

        middleEncoder = hwMap.get(DcMotor.class, "Mencoder");
        middleEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        middleEncoder.setDirection(DcMotorSimple.Direction.REVERSE);

        //Cameras
        frontCam = hwMap.get(WebcamName.class, "Webcam 1");
        backCam = hwMap.get(WebcamName.class, "Webcam 2");

        cameras = ClassFactory.getInstance()
                .getCameraManager().nameForSwitchableCamera(frontCam,backCam);


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

    /**
     * Init Vision
     */
    public void initVision() {
        //Creating Processors
        cameraStream = new BlankCameraStream(opMode.telemetry);
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(false)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .build();

        //Vision portal
        visionPortal = new VisionPortal.Builder()
                //Setting Camera to the switchable camera
                .setCamera(cameras)
                //Adding Processors
                .addProcessors(cameraStream,aprilTag)
                //Resolution
                .setCameraResolution(new Size(640,480))
                .build();

        //Set Active Camera
        visionPortal.setActiveCamera(frontCam);

        //Stream Camera to dashboard
        ftcDashboard.startCameraStream(cameraStream,0);
    }

    public void breakPoint(String caption) {
        boolean proceedControl = false;

        while (opMode.opModeIsActive()&&!proceedControl) {
            proceedControl = opMode.gamepad1.a||opMode.gamepad2.a;

            opMode.telemetry.addLine("You have reached a break point, please press A to proceed.");
            opMode.telemetry.addData("Caption",caption);
            opMode.telemetry.update();
        }

        opMode.telemetry.addLine("Proceeding");
        opMode.telemetry.update();
    }
    public void breakPoint() {
        breakPoint("N/A");
    }


}