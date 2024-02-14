package org.firstinspires.ftc.teamcode.teleop;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.helpers.HardwareMap;

import java.io.File;
import java.io.FileWriter;

@TeleOp(name = "Recorder", group = "b")
//@Disabled //Do not forget to remove this line to make it active
public class Recorder extends LinearOpMode {

    /**
     * HardwareMap Reference
     */
    private final HardwareMap robot = new HardwareMap(this);

    //Enum
    private enum movementState {
        STOPPED,
        MOVING
    }

    //Tmp for recording
    double lastAxial = 0;
    double lastLateral = 0;
    double lastYaw = 0;
    movementState lastState = movementState.STOPPED;
    movementState currentState;
    ElapsedTime idleTime = new ElapsedTime();
    boolean changed = false;
    String data = "";

    //Controls for movement
    double axialControl;
    double lateralControl;
    double yawControl;
    double throttleControl;


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

        //Disable autoclear
        //telemetry.setAutoClear(false);

        //Add a header to the data
        data = "xpos, ypos, zpos, wait";

        while (opModeIsActive()) {
            //Controls
            axialControl = -gamepad1.left_stick_y;
            lateralControl = gamepad1.left_stick_x;
            yawControl = gamepad1.right_stick_x;
            throttleControl = (gamepad1.right_trigger/0.8)+0.2;

            //Apply Input
            setMovementPower(axialControl,lateralControl,yawControl, throttleControl);

            //Record
            currentState = getState();

            if(currentState==movementState.MOVING) {
                idleTime.reset();
            }

            if(currentState==movementState.STOPPED&&lastState==movementState.MOVING) {
                data = data+"\n"+", , , 1";
            }


            changed = getChanged();

            if (changed) {
                data = data+"\n"+robot.tweetyBird.getX()+", "+robot.tweetyBird.getY()+", "+robot.tweetyBird.getZ()+", "+"0";
            }



            //Setting up for next run
            lastAxial = axialControl;
            lastLateral = lateralControl;
            lastYaw = yawControl;
            lastState = currentState;

            //Telemetry
            telemetry.addData("Current State",currentState);
            telemetry.addData("Idle Time",idleTime);
            telemetry.addData("Test",Environment.getExternalStorageDirectory());
            telemetry.update();
        }


        //Write File
        try {
            String directoryName = "sdcard/FIRST/replays";
            String fileName = "test.cvs";

            File directory = new File("sdcard/FIRST/replays");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileWriter replay = new FileWriter(directoryName+"/"+fileName);

            replay.write(data);

            replay.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    //General Movement
    private void setMovementPower(double axial, double lateral, double yaw, double speed) {
        //Creating Individual Power for Each Motor
        double frontLeftPower  = ((axial + lateral + yaw) * speed);
        double frontRightPower = ((axial - lateral - yaw) * speed);
        double backLeftPower   = ((axial - lateral + yaw) * speed);
        double backRightPower  = ((axial + lateral - yaw) * speed);

        //Set Motor Power
        robot.FL.setPower(frontLeftPower);
        robot.FR.setPower(frontRightPower);
        robot.BL.setPower(backLeftPower);
        robot.BR.setPower(backRightPower);
    }

    //Method for recording
    private boolean getChanged() {
        return (lastAxial!=axialControl)||
                (lastLateral!=lateralControl)||
                (lastYaw!=yawControl);
    }

    private movementState getState() {
        return (axialControl!=0)||(lateralControl!=0)||(yawControl!=0)?
                movementState.MOVING:movementState.STOPPED;
    }
}
