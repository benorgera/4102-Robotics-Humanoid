package com.qualcomm.ftcrobotcontroller.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by benorgera on 6/1/16.
 */
public class Arm {

    private DcMotor shoulderFB;
    private Servo shoulderLR;
    private Servo elbowRotate;
    private Servo elbowHinge;
    private Servo hand;
    private boolean isOpen;

    private final double[] handPositions = {Servo.MIN_POSITION, Servo.MAX_POSITION}; //open and closed respectively

    public Arm(DcMotor shoulderFB, Servo shoulderLR, Servo elbowRotate, Servo elbowHinge, Servo hand) {
        this.shoulderFB = shoulderFB;
        this.shoulderLR = shoulderLR;
        this.elbowRotate = elbowRotate;
        this.elbowHinge = elbowHinge;
        this.hand = hand;

        shoulderFB.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void setShoulderFB(double pos) {
        shoulderFB.setPower(trim(pos, -1, 1));
    }

    public void setShoulderLR(double pos) {
        shoulderLR.setPosition(servoTrim(pos));
    }

    public void setElbowRotate(double pos) {
        elbowRotate.setPosition(servoTrim(pos));
    }

    public void setElbowHinge(double pos) {
        elbowHinge.setPosition(servoTrim(pos));
    }

    public void toggleHand() {
        hand.setPosition(handPositions[isOpen ? 0 : 1]);
        isOpen = !isOpen;
    }

    private double trim(double val, double min, double max) {
        return Math.min(Math.max(val, min), max);
    }

    private double servoTrim(double val) {
        return trim(val, Servo.MIN_POSITION, Servo.MAX_POSITION);
    }

}
