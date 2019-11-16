/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.teamlibraries.DriveInputPipeline;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Add your docs here.
 */
public class DriveSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // API Objects
  // Motors and encoders and gyroscopes and stuff
  // Allocate memory to speed up instantiation later
  private WPI_TalonSRX leftDriveMotor1;
  private WPI_TalonSRX leftDriveMotor2;
  private WPI_TalonSRX rightDriveMotor1;
  private WPI_TalonSRX rightDriveMotor2;
  private RobotDrive robotDrive;
  
  private Encoder leftDriveEncoder;
  private Encoder rightDriveEncoder;
  
  private AnalogGyro driveGyro;
  
  private BuiltInAccelerometer driveAccel;
  
  private Ultrasonic frontDriveDistance;
  private Ultrasonic backDriveDistance;

  private boolean arcadeDrive = false;





  //Instantiate the subsystem
  public DriveSubsystem() {
    leftDriveMotor1 = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A_TALON_SRX_ID);
    leftDriveMotor2 = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B_TALON_SRX_ID);
    rightDriveMotor1 = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A_TALON_SRX_ID);
    rightDriveMotor2 = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B_TALON_SRX_ID);
    robotDrive = new RobotDrive(leftDriveMotor1, leftDriveMotor2, rightDriveMotor1, rightDriveMotor2);

    leftDriveEncoder = new Encoder(RobotMap.DRIVE_LEFT_ENCODER_CHANNELA_ID, RobotMap.DRIVE_LEFT_ENCODER_CHANNELB_ID);
    rightDriveEncoder = new Encoder(RobotMap.DRIVE_RIGHT_ENCODER_CHANNELA_ID, RobotMap.DRIVE_RIGHT_ENCODER_CHANNELB_ID);
    driveGyro = new AnalogGyro(RobotMap.DRIVE_GYRO_ID);
    driveAccel = new BuiltInAccelerometer();
    frontDriveDistance = new Ultrasonic(RobotMap.DRIVE_FRONT_DISTANCE_PING_ID, RobotMap.DRIVE_FRONT_DISTANCE_ECHO_ID);
    backDriveDistance = new Ultrasonic(RobotMap.DRIVE_BACK_DISTANCE_PING_ID, RobotMap.DRIVE_BACK_DISTANCE_ECHO_ID);

    leftDriveMotor2.follow(leftDriveMotor1);
    rightDriveMotor2.follow(rightDriveMotor1);

    setLeftBrakemode(false);
    setRightBrakemode(false);

    driveGyro.initGyro();
    driveGyro.calibrate();

    leftDriveEncoder.setDistancePerPulse(RobotMap.DRIVE_ENCODER_DISTANCE_PER_PULSE);
    rightDriveEncoder.setDistancePerPulse(RobotMap.DRIVE_ENCODER_DISTANCE_PER_PULSE);
    leftDriveEncoder.reset();
    rightDriveEncoder.reset();

    frontDriveDistance.setEnabled(true);
    backDriveDistance.setEnabled(true);
  }





  // set and get the motors stuff

  //control left motor
  public void setLeftMotorValue(double motorValue){
    leftDriveMotor1.set(ControlMode.PercentOutput, motorValue);
  }

  //control right motor
  public void setRightMotorValue(double motorValue){
    rightDriveMotor1.set(ControlMode.PercentOutput, motorValue);
  }

  //read left motor
  public double getLeftDriveValue(){
    return leftDriveMotor1.getMotorOutputPercent();
  }

  //read right motor
  public double getRightDriveValue(){
    return rightDriveMotor1.getMotorOutputPercent();
  }

  // set the left breaks to break or coast
  public void setLeftBrakemode(boolean isBraking) {
    // when true, set to breaking mode
    if(isBraking) {
      leftDriveMotor1.setNeutralMode(NeutralMode.Brake);
      leftDriveMotor2.setNeutralMode(NeutralMode.Brake);
    } else { // else set to coast
      leftDriveMotor1.setNeutralMode(NeutralMode.Coast);
      leftDriveMotor2.setNeutralMode(NeutralMode.Coast);
    }
  }

  // set the right breaks to break or coast
  public void setRightBrakemode(boolean isBraking) {
    // when true, set to breaking mode
    if(isBraking) {
      rightDriveMotor1.setNeutralMode(NeutralMode.Brake);
      rightDriveMotor2.setNeutralMode(NeutralMode.Brake);
    } else { // else set to coast
      rightDriveMotor1.setNeutralMode(NeutralMode.Coast);
      rightDriveMotor2.setNeutralMode(NeutralMode.Coast);
    }
  }

  // Automatically set the breaks on when the robot is not moving
  // and disable them when the robot is moving
  public void autoBreakTankDrive(double[] values) {
    // if the input is 0, set break, else don't
    if(values[0] == 0) {
      setLeftBrakemode(true);
    } else {
      setLeftBrakemode(false);
    }

    if(values[1] == 0) {
      setRightBrakemode(true);
    } else {
      setRightBrakemode(false);
    }
  }





  // Control code for the motors

  //drive both motors at once
  public void tankDrive(double leftValue, double rightValue){
    //robotDrive.tankDrive(leftValue, rightValue);
    if(RobotMap.isRobotDirectionForward()){
      robotDrive.tankDrive(-leftValue, -rightValue);
    }else{
      robotDrive.tankDrive(rightValue, leftValue);
    }
  }

  // A simple wrapper for tank drive that converts a double array to
  // the correct values
  public void tankDrive(double[] values) {
    tankDrive(values[0], values[1]);
  }

  // standard arcade drive with directional toggle
  public void arcadeDrive(double forwardValue, double angleValue) {
    if(RobotMap.isRobotDirectionForward()) {
      robotDrive.arcadeDrive(-forwardValue, -angleValue);
    } else {
      robotDrive.arcadeDrive(forwardValue, -angleValue);
    }
  }

  // a wrapper around arcade to make my life easy
  public void arcadeDrive(double[] values) {
    arcadeDrive(values[0], values[1]);
  }

  // stop driving
  public void stopDrive(){
    leftDriveMotor1.set(ControlMode.PercentOutput, 0);
    rightDriveMotor1.set(ControlMode.PercentOutput, 0);
  }

  // a wrapper around tank drive that sets stuff up to be better optimized for teleop controll
  public void teleopTankDriveWrapper(double leftValue, double rightValue) {
    // Convert to an array to allow for easy data transmission
    double[] values = {leftValue, rightValue};

    // do fancy array manipulation stuffs
    DriveInputPipeline dip = new DriveInputPipeline(values);
    dip.inputMapWrapper(DriveInputPipeline.InputMapModes.IMM_SQUARE);
    dip.magnetizeTankDrive();
    dip.applyDeadzones();
    values = dip.getValues();

    autoBreakTankDrive(values);

    // use the modified arrays to drive the robot
    tankDrive(values);
  }

  // a wrapper around arcade drive that sets stuff up to be better optimized for teleop controll
  public void teleopArcadeDriveWrapper(double forwardValue, double angleValue) {
    // Convert to an array to allow for easy data transmission
    double[] values = {forwardValue, angleValue};

    // do fancy array manipulation stuffs
    /*values = inputMapWrapper(values, InputMapModes.IMM_SQUARE, InputMapModes.IMM_SQUARE);
    values = deadzoneTankDrive(values);*/
    DriveInputPipeline dip = new DriveInputPipeline(values);
    dip.inputMapWrapper(DriveInputPipeline.InputMapModes.IMM_CUBE, DriveInputPipeline.InputMapModes.IMM_CUBE);
    dip.applyDeadzones();
    values = dip.getValues();
    
    autoBreakTankDrive(dip.convertArcadeDriveToTank(values));

    // use the modified arrays to drive the robot
    arcadeDrive(values);
  }

  // get whether the robot is in arcade drive mode or not
  public boolean isArcadeDrive() {
    return arcadeDrive;
  }

  // set the robot to arcade drive or not
  public void setArcadeDrive(boolean mode) {
    arcadeDrive = mode;
  }





  // Gyro stuffs

  //read gyro angle
  public double getGyroValue(){
    return driveGyro.getAngle();
  }

  //reset gyro
  public void resetGyro(){
    driveGyro.calibrate();
  }




  // Encoder stuffs

  //read left encoder
  public double getLeftDriveEncoder(){
    return -leftDriveEncoder.getDistance();
  }

  //read right encoder
  public double getRightDriveEncoder(){
    return rightDriveEncoder.getDistance();
  }

  public double getDistance(){
    if(RobotMap.isRobotDirectionForward()){
      return (getRightDriveEncoder() + getLeftDriveEncoder()) / 2.0;
    }else{
      return (getRightDriveEncoder() + getLeftDriveEncoder()) / -2.0;
    }
  }

  //reset left encoder
  public void resetLeftDriveEncoder(){
    leftDriveEncoder.reset();
  }

  //reset right encoder
  public void resetRightDriveEncoder(){
    rightDriveEncoder.reset();
  }

  //reset both encoders
  public void resetEncoders(){
    resetLeftDriveEncoder();
    resetRightDriveEncoder();
  }





  // Accelerometr stuffs

  //read acceleromter
  public double getDriveAccelX(){
    return driveAccel.getX();
  }

  public double getDriveAccelY(){
    return driveAccel.getY();
  }

  public double getDriveAccelZ(){
    return driveAccel.getZ();
  }





  // Range finder

  // read front distance
  public double getFrontDriveDistance(){
    return frontDriveDistance.getRangeMM() / 10.0;
  }

  //read back distance
  public double getBackDriveDistance(){
    return backDriveDistance.getRangeMM() / 10.0;
  }

  //enable/disable front distance
  public void setFrontDriveDistanceEnable(boolean enable){
    frontDriveDistance.setEnabled(enable);
  }

  //enable/disable back distance
  public void setBackDriveDistanceEnable(boolean enable){
    backDriveDistance.setEnabled(enable);
  }





  // Set the default command
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new TeleopDriveCommand());
  }
}
