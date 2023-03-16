// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import java.lang.Math;

import com.revrobotics.CANSparkMax;

public class ArmBase extends SubsystemBase {
  /** Creates a new RobotArm. */


  /*             TODO
   *          
   * * OR NOT:
   * * NO positional sensing, dead reckoning for the operator
   * * -0.06-0.06 range of power inputs on each joint
   * * camera surveying arm motion?
   * *
   * *
   * *
   * *
   * *
   * *
   * *
   * *
   * 
   *            ALL ANGLES IN RADIANS, ALL LENGTHS IN CM, ALL TIMES IN S       
   *            Rotational speed assumed to be .5 radian/sec at max velocity and decreasing linearly
   *            Two 16:1 reductions on the low joint to get it down to ~.5 radians/sec at max speed 
   *            30:1 and 16:1 reductions on the upper joint to get also down to ~.5/sec at max speed 
   *              This will need better values w/ testing and data - fudge factor for motor values
   *              Full power should produce slightly greater than target .5 radians/sec value so that the value can be modulated down 
   *  
   * 
   *       1. Functionality for setting the angles of the joints DONE(?)
   *          a. set a specific angle value for the joint to hold
   *          b. periodic function compares measured angle values to target angle values and corrects with motor speed if necessary
   *       2. Functionality for sending the joints to a preset DONE(?)
   *          a. fairly simple - command to set both angles: either
   *    SetBothAngles(double lambda, double phi)
   *    {
   *      SetLowJoint(lambda);
   *      SetHighJoint(phi);
   *    }
   *                                                          OR simply
   *    SetLowJoint(lambda);
   *    SetHighJoint(phi);
   *       3. Functionality for setting angular speed of joints DONE(?)
   *          a. Might need experimental testing for values? but ideally not, vary speed to produce different angular velocities
   *          b. SetLowJointAngVel(double vel) [vel in radians/sec]
   *              {
   *                LowJoint.set([some transformation of vel])
   *              }
   *       4. Functionality for (x,y) presets DONE(?)
   *          a. SetXYPos(double x, double y)
   *              {
   *                lambda = (atan(y/x) + acos( (Math.pow(HighArmLength, 2) - Math.pow(LowArmLength, 2) - Math.pow(x, 2) - Math.pow(y,2))/ (-2 * LowArmLength * Math.sqrt(Math.pow(x,2) + Math.pow(y,2))) ));
   *                phi = acos( (Math.pow(x,2) + Math.pow(y,2) - Math.pow(LowArmLength,2) - Math.pow(HighArmLength,2)) / (-2 * LowArmLength * HighArmLength) );
   *                SetLowJoint(lambda);
   *                SetHighJoint(phi);
   *              }
   *       5. Functionality for translations of the arm
   *          Might be possible to just use (x,y) correction and then have the joystick modify TARGX and TARGY?
   *          LATER
   * 
   */
  public ArmBase() {}

  public static CANSparkMax LowArmJoint = RobotContainer.lowArmJoint;
  public static CANSparkMax HighArmJoint = RobotContainer.highArmJoint;
  //Encoders for both LowJoint and HighJoint


  //TARGET and CURRENT angles (in radians) for the upper and lower arm joint
  double TargetLowJointAngle;
  double TargetHighJointAngle;

  double CurrentLowJointAngle;
  double CurrentHighJointAngle;


  //LENGTHS (in cm) of the LOW and HIGH arms
  double LowArmLength;
  double HighArmLength;

  double sqlow = LowArmLength * LowArmLength;
  double sqhi = HighArmLength * HighArmLength;

  //HARD X LIMIT
  double MaxX;

  //TARGET and CURRENT POSITIONS
  double TargetX;
  double TargetY;
  double CurrentX;
  double CurrentY;

  //FLAGS designating whether or not to use translational or correctional motor velocities
  // when translational control inputs are active, set this to FALSE to disable arm self-correction. 
  //at the conclusion of joystick inputs (i.e. when the values equal 0) set it to TRUE to hold the angle
  //boolean AngleMode;
    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    /* 
     * 
     * ANGLE CORRECTION
     * Combine current angle + target angle: do something like [Motor].set((tarang - curang)/halfpi)
     */ 
    /*
     CurrentX = GetXPos();
    CurrentY = GetYPos();

      LowArmJoint.set((TargetLowJointAngle - GetLowJointAngle()) / 1.571);
      HighArmJoint.set((TargetHighJointAngle - GetHighJointAngle()) / 1.571);
      
      if ((TargetX != CurrentX || TargetY != CurrentY))
      {
        GoToXYPos(TargetX, TargetY);
      }
    */
  }

  //SWITCHMODE SETTINGS
  /*
  public void SetAngleMode()
  {
    AngleMode = true;
  }
  public void SetXYPosMode()
  {
    AngleMode = false;
  }
  */
  //SETANGLE METHODS
  public void SetLowJoint(double lambda)
  {
    TargetLowJointAngle = lambda;
   
  }
  public void SetHighJoint(double phi)
  {
    TargetHighJointAngle = phi;
  }


  
  //GETANGLE METHODS
  /*
  Need more info on how encoders work + our specific setup
  might need to instead constantly update?
  */
  public double GetLowJointAngle()
  {
    return CurrentLowJointAngle;
  }
  public double GetHighJointAngle()
  {
    return CurrentHighJointAngle;
  }
  

  //SETANGVEL METHODS
  /*
  Need more info on the precise translation from -1 - 1 motor input and rotational velocity
   * 
   */
  public void SetLowJointAngVel(double vel)
  {
    LowArmJoint.set(vel);
    SetLowJoint(GetLowJointAngle());    
  }
  public void SetHighJointAngVel(double vel)
  {
    HighArmJoint.set(vel);
    SetHighJoint(GetHighJointAngle());
  }


  //XYPOS Methods

  public void GoToXYPos(double x, double y)
  //takes an (x,y) position relative to the base of the arm where x and y are in centimeters
  {
    double sqx = x * x;
    double sqy = y * y;
    double lambda = (Math.atan(y/x) + Math.acos( (sqhi - sqlow - sqx - sqy))/ (-2 * LowArmLength * Math.sqrt(sqx + sqy)) );
    double phi = Math.acos( (sqx + sqy - sqlow - sqhi) / (-2 * LowArmLength * HighArmLength) );
    SetLowJoint(lambda);
    SetHighJoint(phi);
  }

  public double GetXPos()
  {
    return Math.cos(GetLowJointAngle()) + Math.cos(3.141 - GetHighJointAngle() - GetLowJointAngle());
  }
  public double GetYPos()
  {
    return Math.sin(GetLowJointAngle()) + Math.sin(3.141 - GetHighJointAngle() - GetLowJointAngle());
  }
  public void SetXPos(double targ)
  {
    TargetX = targ;
    if (TargetX > MaxX)
    {
      TargetX = MaxX;
    }
  } 
  public void SetYPos(double targ)
  {
    TargetY = targ;
  }
  

  public void SimpleArmRotationalControl(double low, double high)
  {
    LowArmJoint.set(low * .06);
    HighArmJoint.set(high * .06);
  }

}
