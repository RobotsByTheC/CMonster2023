// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*                    TODO
 *        - Basic lineup for the targets (adjust robot to face precisely at retro tape) 
 *        - AUTOELLA
 *          - when near goal, identify goal -> move robot, arm, claw to release button(s, for high and mid)
 *          - identify target -> move robot, arm, claw to grab it button
 *        - charging station?
 */


public class LimelightBase extends SubsystemBase {

  NetworkTable limin = NetworkTableInstance.getDefault().getTable("limelight");
    

    //pulls the distance from the identified object in the x direction and y direction and also the object's area
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);


  /** Creates a new LimelightBase. */
  public LimelightBase() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    NetworkTableEntry tx = limin.getEntry("tx");
    NetworkTableEntry ty = limin.getEntry("ty");
    NetworkTableEntry ta = limin.getEntry("ta");

    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
  }
}
