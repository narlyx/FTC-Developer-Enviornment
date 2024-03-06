package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.HardwareMap;

@TeleOp(name = "Basic Driver Control", group = "a")
public class BasicDriverControl extends LinearOpMode {

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

        waitForStart();
        //Run

        //Runtime
        while (opModeIsActive()) {
            //Controls
            double axialControl = -gamepad1.left_stick_y;
            double lateralControl = gamepad1.left_stick_x;
            double yawControl = gamepad1.right_stick_x;
            double throttleControl = (gamepad1.right_trigger/0.8)+0.2;

            //Apply Input
            setMovementPower(axialControl,lateralControl,yawControl, throttleControl);
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
