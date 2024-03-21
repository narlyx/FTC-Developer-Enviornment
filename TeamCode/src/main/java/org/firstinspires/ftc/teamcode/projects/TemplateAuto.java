package org.firstinspires.ftc.teamcode.projects;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.HardwareMap;

@Autonomous(name = "Change Me", group = "a")
@Disabled //Do not forget to remove this line to make it active
public class TemplateAuto extends LinearOpMode {

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


    }
}
