/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OutputAllDataCommand extends Command {
  public OutputAllDataCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    // this.setRunWhenDisabled(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putNumber("Drive Left Drive Value", Robot.driveSubsystem.getLeftDriveValue());
    SmartDashboard.putNumber("Drive Right Drive Value", Robot.driveSubsystem.getRightDriveValue());
    SmartDashboard.putNumber("Drive Gyro Value", Robot.driveSubsystem.getGyroValue());
    SmartDashboard.putNumber("Drive Left Encoder", Robot.driveSubsystem.getLeftDriveEncoder());
    SmartDashboard.putNumber("Drive Right Encoder", Robot.driveSubsystem.getRightDriveEncoder());
    SmartDashboard.putNumber("Drive Accel X", Robot.driveSubsystem.getDriveAccelX());
    SmartDashboard.putNumber("Drive Accel Y", Robot.driveSubsystem.getDriveAccelY());
    SmartDashboard.putNumber("Drive Accel Z", Robot.driveSubsystem.getDriveAccelZ());
    SmartDashboard.putNumber("Drive Front Distance",Robot.driveSubsystem.getFrontDriveDistance());
    SmartDashboard.putNumber("Drive Back Distance", Robot.driveSubsystem.getBackDriveDistance());
    SmartDashboard.putBoolean("Robot Front Side", RobotMap.isRobotDirectionForward());
    // SmartDashboard.putBoolean("Front Limit Switch A", Robot.climbsubsystem.getFrontLimitSwitchAValue());
    // SmartDashboard.putBoolean("Front Limit Switch B", Robot.climbsubsystem.getFrontLimitSwitchBValue());
    // SmartDashboard.putBoolean("Back Limit Switch A", Robot.climbsubsystem.getBackLimitSwitchAValue());
    // SmartDashboard.putBoolean("Back Limit Switch B", Robot.climbsubsystem.getBackLimitSwitchBValue());

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
