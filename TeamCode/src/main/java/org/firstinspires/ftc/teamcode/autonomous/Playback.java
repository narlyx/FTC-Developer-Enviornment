package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.helpers.HardwareMap;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

@Autonomous(name = "Playback", group = "b")
//@Disabled //Do not forget to remove this line to make it active
public class Playback extends LinearOpMode {

    /**
     * HardwareMap Reference
     */
    private final HardwareMap robot = new HardwareMap(this);

    //Reading
    String data = "";

    /**
     * Opmode Start
     */
    @Override
    public void runOpMode() {
        //Initialize
        robot.initGeneral();
        robot.initTweetyBird();
        robot.tweetyBird.engage();
        robot.tweetyBird.speedLimit(0.3);

        waitForStart();
        //Run

        data = getRawReplayCVS("test.cvs");
        parseData(data);

        while (opModeIsActive());

        robot.tweetyBird.stop();
    }

    //Get Data
    private String getRawReplayCVS(String fileName) {
        String returnData = "";

        try {
            String replayFileName = "sdcard/FIRST/replays/"+fileName;
            File replayFile = new File(replayFileName);
            Scanner fileReader = new Scanner(replayFile);

            while (fileReader.hasNextLine()) {
                returnData = returnData+"\n"+fileReader.nextLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return returnData;
    }

    //Parse data
    private void parseData(String data) {

        int currentLine = 1;
        int xIndex = 1;
        String currentValue = "";
        ArrayList<String> curentValues = new ArrayList<>();

        for (int i = 0; i<data.length(); i++) {
            char currentChar = data.charAt(i);

            //Telemetry
            telemetry.addLine("Your data is currently being executed...");
            telemetry.addLine("Please sit back, bite your nails, and watch!");
            telemetry.addLine();
            double rawProgress = 100/(data.length()/(i+0.001));
            telemetry.addData("Progress",rawProgress);
            telemetry.addLine();

            //Gathering Data
            if (currentChar==' ') { //Space

            } else if (currentChar==',') { //Separator
                curentValues.add(currentValue);
                currentValue = "";
                xIndex++;
            } else if (currentChar=='\n') { //New Line
                curentValues.add(currentValue);
                currentValue = "";

                execute(curentValues);

                curentValues.clear();
                xIndex = 1;
                currentLine++;
            } else { //Data
                currentValue = currentValue+currentChar;
            }

            //Send Telemetry;
            telemetry.update();
        }

        curentValues.add(currentValue);
        currentValue = "";

        execute(curentValues);

        telemetry.addLine("Done!");
        telemetry.update();
    }

    //Execute Data
    private void execute(ArrayList<String> dataList) {
        if (dataList.size()>2) {
            try {
                double x = Double.parseDouble(dataList.get(0));
                double y = Double.parseDouble(dataList.get(1));
                double z = Double.parseDouble(dataList.get(2));

                robot.tweetyBird.straightLineTo(x,y, Math.toDegrees(z));

            } catch (NumberFormatException e) {}

            try {
                boolean wait = Double.parseDouble(dataList.get(3)) == 1;
                if (wait) {
                    robot.tweetyBird.waitWhileBusy();
                    robot.tweetyBird.waitWhileBusy();
                    robot.tweetyBird.waitWhileBusy();
                }

            } catch (NumberFormatException e) {}
        }


    }
}
