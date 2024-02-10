package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.helpers.HardwareMap;
import org.opencv.core.Mat;

@TeleOp(name = "Experimental Boundaries", group = "c")
//@Disabled //Do not forget to remove this line to make it active
public class ExperimentalBounderies extends LinearOpMode {

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

        while (opModeIsActive()) {
            //Controls
            double axialControl = -gamepad1.left_stick_y;
            double lateralControl = gamepad1.left_stick_x;
            double yawControl = gamepad1.right_stick_x;
            double speedControl = gamepad1.right_trigger;

            power(axialControl,lateralControl,yawControl);
        }

    }

    /**
     * Experimental Methods in Question
     */
    private void power(double axial, double lateral, double yaw, double speed) {

        ElapsedTime runtime = new ElapsedTime();

        double targetHeading = Math.atan2(lateral,axial);

        double headingModifier = 0;
        double distance = 10;
        while (opModeIsActive()) {
            boolean clearPath = true;


            for (int x = 0; x<2; x++) {
                headingModifier = headingModifier*-1;

                for (int i = 0; i<distance; i++) {
                    double castedX = (distance*Math.sin(targetHeading+headingModifier))+robot.tweetyBird.getX(); //TODO: Position add here.
                    double castedY = (distance*Math.cos(targetHeading+headingModifier))+robot.tweetyBird.getY();

                    //TODO: The check goes here
                    if (castedY>20) {


                        clearPath = false;
                    }
                }
            }


            telemetry.addData("Heading",Math.toDegrees(targetHeading+headingModifier));
            telemetry.update();

            if (clearPath||headingModifier>Math.toRadians(180)) {
                break;
            } else {
                headingModifier+=Math.toRadians(1);
            }
        }

        double finalHeading = targetHeading+headingModifier;

        double finalAxial = axial+lateral!=0? Math.cos(finalHeading) :0;
        double finalLateral = axial+lateral!=0? Math.sin(finalHeading) :0;

        double frontLeftPower  = ((finalAxial + finalLateral + yaw) * speed);
        double frontRightPower = ((finalAxial - finalLateral - yaw) * speed);
        double backLeftPower   = ((finalAxial - finalLateral + yaw) * speed);
        double backRightPower  = ((finalAxial + finalLateral - yaw) * speed);

        robot.FL.setPower(frontLeftPower);
        robot.FR.setPower(frontRightPower);
        robot.BL.setPower(backLeftPower);
        robot.BR.setPower(backRightPower);


    }

    private void power (double axial, double lateral, double yaw) {
        double speed = Range.clip(Math.hypot(lateral, axial)+Math.abs(yaw), 0, 1);
        power(axial,lateral,yaw,speed);
    }

}
