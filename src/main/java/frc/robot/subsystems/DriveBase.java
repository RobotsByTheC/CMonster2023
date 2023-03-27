// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


import frc.robot.RobotContainer;
import frc.robot.Robot;
import frc.robot.commands.*;

public class DriveBase extends SubsystemBase {
  /** Creates a new DriveBase. */

  //Creates motors refering to ones made in RobotContainer
  WPI_TalonSRX leftTalon = RobotContainer.leftFrontTalon;
  WPI_TalonSRX rightTalon = RobotContainer.rightFrontTalon;
  WPI_VictorSPX leftVictor = RobotContainer.leftBackVictor;
  WPI_VictorSPX rightVictor = RobotContainer.rightBackVictor;
 
  //Sets motor Speeds
  double leftMotorSpeed = 0;
  double rightMotorSpeed = 0; 
 
  public DriveBase() {
    
  }

  public void JoystickInputs(Joystick rightJoystick, Joystick leftJoystick){
    leftMotorSpeed = leftJoystick.getY()*-1;//get values from joystick
    rightMotorSpeed = rightJoystick.getY();

    leftTalon.set(leftMotorSpeed);
    leftVictor.set(leftMotorSpeed);
    rightTalon.set(rightMotorSpeed);
    rightVictor.set(rightMotorSpeed);

  }
  public void driveAuto(double input){
    if (input < 0)
    //go BACKWARDS
    {
       leftTalon.set(-.5);
       rightTalon.set(.5);
       leftVictor.set(-.5);
       rightVictor.set(.5);
    }
    else
    //go FORWARDS
    {
       leftTalon.set(.5);
       leftVictor.set(.5);
       rightTalon.set(-.5);
       rightVictor.set(-.5);
    }
    }
    public void driveStopAuto()
    {
      leftTalon.set(0);
      rightTalon.set(0);
      leftVictor.set(0);
      rightVictor.set(0);
    }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
