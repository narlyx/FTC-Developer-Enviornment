package org.firstinspires.ftc.teamcode.projects.motortester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.HardwareMap;

import java.lang.reflect.Field;
import java.util.ArrayList;

@TeleOp(name = "Motor Tester", group = "z")
//@Disabled //Do not forget to remove this line to make it active
public class AdvancedMotorTester extends LinearOpMode {

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

        //Don't clear
        telemetry.setAutoClear(false);

        //List of motors
        telemetry.addLine("Attempting to pull motors from the setup...\n");
        telemetry.update();
        ArrayList<detectedMotor> motorsList = new ArrayList<>();

        //Populating motors list
        for (Field field : robot.getClass().getDeclaredFields()) {

            if (field.getType() == DcMotor.class) {
                field.setAccessible(true);

                //Setting
                detectedMotor newMotor = null;
                try {
                    Object value = field.get(robot);

                    newMotor = new detectedMotor(field.getName(), (DcMotor) value);
                    motorsList.add(newMotor);

                    telemetry.addData("New Detection",newMotor.getName());
                } catch (IllegalAccessException e) {
                    telemetry.addData("New Detection","FAILED TO GET MOTOR");
                    throw new RuntimeException(e);
                }

                //Telemetry

                telemetry.update();
            }

        }

        telemetry.addLine("\n"+motorsList.size()+" motors have been successfully detected, press play to begin testing!");
        telemetry.update();

        //Reset to Autoclear
        telemetry.setAutoClear(true);

        waitForStart();
        //Run

        //Temp Variables
        int index = 0;
        boolean debounce = false;

        //Runtime Loop
        while (opModeIsActive()) {
            //Controls
            boolean nextButton = gamepad1.a || gamepad2.a;
            boolean prevButton = gamepad1.b || gamepad2.b;
            double power = -gamepad1.left_stick_y + -gamepad2.left_stick_y;

            //Cycle Forward
            if (nextButton&&!debounce) {
                debounce = true;
                if (index<motorsList.size()-1) {
                    index++;
                } else {
                    index = 0;
                }
            }

            //Cycle Backwards
            if (prevButton&&!debounce) {
                debounce = true;
                if (index>0) {
                    index--;
                } else {
                    index = motorsList.size()-1;
                }
            }

            //Debounce
            if (!nextButton&&!prevButton&&debounce) {
                debounce = false;
            }

            //Motor Instance
            String name = motorsList.get(index).getName();
            DcMotor instance = motorsList.get(index).getInstance();

            //Power Motor
            instance.setPower(power);

            //Display
            telemetry.addData("Selected Motor",name);
            telemetry.addLine();
            telemetry.addData("Power",instance.getPower());
            telemetry.addData("Position",instance.getCurrentPosition());
            telemetry.addData("Target Position",instance.getTargetPosition());
            telemetry.addData("Mode",instance.getMode());
            telemetry.addData("Direction",instance.getDirection());
            telemetry.addData("Zero Power Behaviour",instance.getZeroPowerBehavior());
            telemetry.addLine();
            telemetry.addData("Device Name",instance.getDeviceName());
            telemetry.addData("Type",instance.getMotorType());
            telemetry.addData("Manufacturer",instance.getManufacturer());
            telemetry.addData("Version",instance.getVersion());
            telemetry.addData("Controller",instance.getController());
            telemetry.addData("Port",instance.getPortNumber());
            telemetry.update();
        }



    }
}

class detectedMotor {
    private String name = null;
    private DcMotor instance = null;

    public detectedMotor(String motorName, DcMotor motorInstance) {
        name = motorName;
        instance = motorInstance;
    }

    public String getName() {
        return name;
    }

    public DcMotor getInstance() {
        return instance;
    }
}
