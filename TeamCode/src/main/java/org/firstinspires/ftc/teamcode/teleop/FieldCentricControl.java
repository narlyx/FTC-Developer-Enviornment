package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.util.HardwareMap;

@TeleOp(name = "Field Centric Control (IMU)", group = "a")
public class FieldCentricControl extends LinearOpMode {

    /**
     * HardwareMap Reference
     */
    private final HardwareMap robot = new HardwareMap(this);

    boolean fcdDebounce = false;
    boolean fcd = true;

    /**
     * Opmode Start
     */
    @Override
    public void runOpMode() {
        //Initialize
        robot.initGeneral();

        waitForStart();
        //Run

        //Runtime
        while (opModeIsActive()) {
            //Controls
            double inputX = gamepad1.left_stick_x;
            double inputY = -gamepad1.left_stick_y;
            double inputZ = gamepad1.right_stick_x;
            double inputThrottle = gamepad1.right_trigger;
            boolean fcdToggleButton = gamepad1.dpad_down;
            boolean fcdResetButton = gamepad1.dpad_up;

            //Toggle fcd(field centric driving) on or off
            if(fcdToggleButton&&!fcdDebounce) {
                fcdDebounce=true;
                fcd=!fcd;
            }

            //Debounce
            if(!fcdToggleButton&&fcdDebounce) {
                fcdDebounce=false;
            }

            //ResetZ
            if(fcdResetButton) {
                robot.resetIMUZ();
            }

            //Axial, lateral, yaw, and throttle output
            double axial = inputY;
            double lateral = inputX;
            double yaw = inputZ;
            //inputThrottle = (1/4)+(inputThrottle*(3/4));

            //Field centric calculations if enabled
            if(fcd) {
                double gamepadRadians = Math.atan2(inputX, inputY);
                double gamepadHypot = Range.clip(Math.hypot(inputX, inputY), 0, 1);
                double robotRadians = -robot.getIMUZ();
                double targetRadians = gamepadRadians + robotRadians;
                lateral = Math.sin(targetRadians)*gamepadHypot;
                axial = Math.cos(targetRadians)*gamepadHypot;
            }

            //Sending movements
            robot.setMovementPower(axial,lateral,yaw,Range.clip(inputThrottle,0.2,1));

            telemetry.addData("IMU yaw",robot.getIMUZ());
            telemetry.addData("FCD",fcd);
            telemetry.addData("Debounce",fcdDebounce);
            telemetry.update();
        }
    }

    public void setMovementPower(double axial, double lateral, double yaw, double speed) {
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
}
