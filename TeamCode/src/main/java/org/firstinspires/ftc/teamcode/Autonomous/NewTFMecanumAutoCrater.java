package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.teamcode.MecanumLinearOpMode;
import org.firstinspires.ftc.teamcode.OldMecanumLinearOpMode;

@Autonomous(name="NewTFMecanumAutoCrater", group = "auto")
//@Disabled
public class NewTFMecanumAutoCrater extends MecanumLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        double offset = 45;

        init(hardwareMap, true);

        // SET UP DETECTOR

        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        }

        telemetry.addData("Mode", "setting up detector...");
        telemetry.update();

        telemetry.addData("detector", "enabled");
        telemetry.update();

        double dist = 0;
        waitForStart();
        unlatch();


        //START DETECTION

        findGold(2.5); //GET GOLD POSITION
        int gold = retPos();
        sleep(1000);
        telemetry.addData("Gold is at", gold);
        telemetry.update();
        driveDistance(0.3,4); //MOVE FORWARD OUT OF LANDER ZONE

        dist = pushGold(gold,true, offset); //

        driveDistance(-0.5, dist); //MOVE TOWARD WALL
        sleep(500);
        rotate(-90, 5); //TURN TOWARD WALL
        driveTime(-0.3, 1); //ALIGN WITH WALL
        sleep(500);
        driveTime(0.3, .25);    //MOVE BACK FROM WALL
        if (getRange() < 100){
            markerMove();
        }else{
            strafeDistance(0.8, 40,true);   //STRAFE TOWARD DEPOT
        }
        marker.setPosition(0.41);   //DEPLOY MARKER
        sleep(1000);
        strafeDistance(0.8, 72,false); //STRAFE INTO CRATER
        marker.setPosition(0.2);    //RETRACT MARKER DEPLOYMENT
        telemetry.addData("Status ", " auto done");
    }
}