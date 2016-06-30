package com.qualcomm.ftcrobotcontroller.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by benorgera on 6/1/16.
 */
public class Arm {

    private final double movementThreshold = 0.35;
    private final double motorMovementPower = 0.10;
    private final double servoMovementStep = 0.00005;

    private DcMotor shoulderFB;
    private Servo shoulderLR;
    private Servo elbowRotate;
    private Servo elbowHinge;
    private Servo hand;

    private boolean isOpen;
    private double shoulderLRPos = 0.5;
    private double elbowRotatePos = 0.5;
    private double elbowHingePos = 0.5;

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
        shoulderFB.setPower(Math.abs(pos) > movementThreshold ? (pos > 0 ? motorMovementPower : motorMovementPower * -1) : 0);
    }

    public void setShoulderLR(double pos) {
        shoulderLRPos = updateServo(pos, shoulderLRPos);
        shoulderLR.setPosition(shoulderLRPos);
    }

    public void setElbowRotate(double pos) {
        elbowRotatePos = updateServo(pos, elbowRotatePos);
        elbowRotate.setPosition(elbowRotatePos);
    }

    public void setElbowHinge(double pos) {
        elbowHingePos = updateServo(pos, elbowHingePos);
        elbowHinge.setPosition(elbowHingePos);
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

    private double updateServo(double stickPos, double servoPos) {
        return servoTrim(Math.abs(stickPos) > movementThreshold ? (servoPos + (stickPos > 0 ? servoMovementStep : servoMovementStep * -1)) : servoPos);
    }

}
