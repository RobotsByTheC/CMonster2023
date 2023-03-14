// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawBase extends SubsystemBase {

  public boolean clawToggle = false;

  public static DoubleSolenoid ClawSolenoid = RobotContainer.clawSolenoid;

  /** Creates a new ClawBase. */
  public ClawBase() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void boxSolenoidToggle(){
    if (clawToggle)
    {
    ClawSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    else
    {
    ClawSolenoid.set(DoubleSolenoid.Value.kReverse);
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

  

  
  public void coneSolenoidToggle(){
    if (clawToggle)
    {
    ClawSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    else
    {
    ClawSolenoid.set(DoubleSolenoid.Value.kReverse);
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


}
