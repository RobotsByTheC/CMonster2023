// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawBase extends SubsystemBase {

  public boolean boxToggle = false;
  public boolean coneToggle = false;
  public boolean clawToggle = false;

  //public static DoubleSolenoid BoxSolenoid = RobotContainer.boxSolenoid;
  public static DoubleSolenoid ConeSolenoid = RobotContainer.coneSolenoid;
  public static DoubleSolenoid ClawExtendSolenoid = RobotContainer.clawExtendSolenoid;


  /** Creates a new ClawBase. */
  public ClawBase() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /*public void boxSolenoidToggle(){
    if (boxToggle)
    {
    BoxSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    else
    {
    BoxSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }
  public CommandBase BoxSolenoidToggle()
  {
    return runOnce(
      () -> {
        boxSolenoidToggle();
      }
    );
  }

  */


  public void coneSolenoidToggle(){
    if (coneToggle)
    {
    ConeSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    else
    {
    ConeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public CommandBase ConeSolenoidToggle()
  {
    return runOnce(
      () -> {
        coneSolenoidToggle();
      }
    );
  }


  
  public void clawSolenoidToggle(){
    if (clawToggle)
    {
    ClawExtendSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    else
    {
    ClawExtendSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
  }
  
  public CommandBase ClawSolenoidToggle()
  {
    return runOnce(
      () -> {
        clawSolenoidToggle();
      }
    );
  }

}
