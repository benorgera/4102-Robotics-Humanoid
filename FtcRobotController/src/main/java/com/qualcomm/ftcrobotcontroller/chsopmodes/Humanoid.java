package com.qualcomm.ftcrobotcontroller.chsopmodes;

import com.qualcomm.ftcrobotcontroller.systems.Arm;
import com.qualcomm.ftcrobotcontroller.systems.Wheels;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.ftcrobotcontroller.systems.Necessities;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by benorgera on 6/1/16.
 */

public class Humanoid extends LinearOpMode {

    //robot systems

    Wheels wheels;
    Arm left;
    Arm right;

    boolean isRightArm = true;

    Necessities n;

    public void initialize() {

        n = new Necessities(this.telemetry, this);

        hardwareMap.logDevices();

        n.syso("Initializing:", "Initializing 4102 Began");

        wheels = new Wheels(
                new DcMotor[] {hardwareMap.dcMotor.get("left1"), hardwareMap.dcMotor.get("left2")},
                new DcMotor[] {hardwareMap.dcMotor.get("right1"), hardwareMap.dcMotor.get("right2")},
                n);

        left = new Arm(
                hardwareMap.dcMotor.get("left_shoulder_fb"),
                hardwareMap.servo.get("left_shoulder_lr"),
                hardwareMap.servo.get("left_elbow_rotate"),
                hardwareMap.servo.get("left_elbow_hinge"),
                hardwareMap.servo.get("left_hand"));

        right = new Arm(
                hardwareMap.dcMotor.get("right_shoulder_fb"),
                hardwareMap.servo.get("right_shoulder_lr"),
                hardwareMap.servo.get("right_elbow_rotate"),
                hardwareMap.servo.get("right_elbow_hinge"),
                hardwareMap.servo.get("right_hand"));
        
        n.syso("Initializing:", "Initializing 4102 Completed");

    }

    public void run() throws InterruptedException {


        while (opModeIsActive()) {

            wheels.processMainstream(gamepad1.left_stick_x, gamepad1.left_stick_y * -1);

            isRightArm = gamepad2.right_bumper ? true : gamepad2.left_bumper ? false : isRightArm;

            controlArm(isRightArm ? right : left);

        }

    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitOneFullHardwareCycle();

        waitForStart();

        run();

    }

    private void controlArm(Arm a) {
        a.setShoulderFB(gamepad2.left_stick_y);
        a.setShoulderLR(gamepad2.left_stick_x);
        a.setElbowHinge(gamepad2.right_stick_y);
        a.setElbowHinge(gamepad2.right_stick_x);
        a.toggleHand();
    }

}