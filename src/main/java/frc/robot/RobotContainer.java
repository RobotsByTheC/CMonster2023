// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

//Imports needed for driving
import edu.wpi.first.wpilibj2.command.CommandScheduler;

//Imports all commands and subsystems
import frc.robot.commands.*; 
import frc.robot.subsystems.*; 

//Imports things to be on smart dashboard
import edu.wpi.first.wpilibj.DigitalInput; 
import edu.wpi.first.cscore.UsbCamera;


//imports joysticks and buttons
import edu.wpi.first.wpilibj.Joystick; 
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Timer;


//imports the pheonix products 
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


//Spark max imports (to import, install vendor library online and put this link in https://software-metadata.revrobotics.com/REVLib.json)
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//Pneumatic imports
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.GenericHID;

//Servo import
import edu.wpi.first.wpilibj.Servo; 

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

public static LimelightBase limelightBase;


 //declared and initallizes talons and victors for drive train 
 public static WPI_TalonSRX leftFrontTalon = new WPI_TalonSRX(2);
 public static WPI_VictorSPX leftBackVictor = new WPI_VictorSPX(3);
 public static WPI_TalonSRX rightFrontTalon = new WPI_TalonSRX(4); 
 public static WPI_VictorSPX rightBackVictor = new WPI_VictorSPX(5);

 //Arm motors and subsystem
  public static CANSparkMax lowArmJoint = new CANSparkMax(5, MotorType.kBrushless);
 public static CANSparkMax highArmJoint = new CANSparkMax(6, MotorType.kBrushless);

 //compressor and solenoids
 //Compressors
 public static Compressor robotCompressor;
 public static DoubleSolenoid clawSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,0,1);


//Initialzes subsystems in RobotContainer
public static DriveBase driveBase;
public static ClawBase clawBase;
public static ArmBase armBase;


//Initializes commands in RobotContainer
public static DriveWithJoystick driveWithJoystick; 


//Creates Joysticks
public static Joystick rightJoystick; 
public static Joystick leftJoystick; 
public static Joystick logiTech; 

public static JoystickButton boxButton;
public static JoystickButton coneButton;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

//Initializes Joystick
    leftJoystick = new Joystick(0);
    rightJoystick = new Joystick(1);
    logiTech = new Joystick(2);
    
    boxButton = new JoystickButton(logiTech, 1);
    coneButton = new JoystickButton(logiTech, 2);

    driveBase = new DriveBase();
    driveWithJoystick = new DriveWithJoystick();
    CommandScheduler.getInstance().setDefaultCommand(driveBase, driveWithJoystick);

    clawBase = new ClawBase();
    armBase = new ArmBase();
    limelightBase = new LimelightBase();

    boxButton.onTrue(new BoxOpenClose());
    coneButton.onTrue(new ConeOpenClose());

    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
  
  public static Joystick getRightJoystick(){
    return rightJoystick;
  }

  public static Joystick getLeftJoystick(){
    return leftJoystick;
  }

  public static Joystick getLogiTech(){
    return logiTech;
  }
}
