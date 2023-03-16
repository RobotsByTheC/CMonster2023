// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//imports the pheonix products 
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//Spark max imports (to import, install vendor library online and put this link in https://software-metadata.revrobotics.com/REVLib.json)
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
//Pneumatic imports
import edu.wpi.first.wpilibj.DoubleSolenoid;
//imports joysticks and buttons
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
//Imports needed for driving
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
//Imports all commands and subsystems
import frc.robot.commands.Autos;
import frc.robot.commands.BoxOpenClose;
import frc.robot.commands.ConeOpenClose;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.SimpleArmRotationalControl;
import frc.robot.subsystems.ArmBase;
import frc.robot.subsystems.ClawBase;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.LimelightBase; 

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
  public static CANSparkMax lowArmJoint = new CANSparkMax(6, MotorType.kBrushless);
 public static CANSparkMax highArmJoint = new CANSparkMax(7, MotorType.kBrushless);

 //compressor and solenoids
 //Compressors
 public static Compressor robotCompressor;
 public static DoubleSolenoid boxSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,0,1);
 public static DoubleSolenoid coneSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,2,3);
 public static DoubleSolenoid clawExtendSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,4,5);

//Initialzes subsystems in RobotContainer
public static DriveBase driveBase;
public static ClawBase clawBase;
public static ArmBase armBase;


//Initializes commands in RobotContainer
public static DriveWithJoystick driveWithJoystick; 
public static SimpleArmRotationalControl simpleArmRotationalControl;


//Creates Joysticks
public static Joystick rightJoystick; 
public static Joystick leftJoystick; 
public static Joystick logiTech; 

public static JoystickButton boxButton;
public static JoystickButton coneButton;
public static JoystickButton  clawExtendButton;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

//Initializes Joystick
    leftJoystick = new Joystick(0);
    rightJoystick = new Joystick(1);
    logiTech = new Joystick(2);
    
 //   boxButton = new JoystickButton(logiTech, 1);
    coneButton = new JoystickButton(logiTech, 2);
    clawExtendButton = new JoystickButton(logiTech, 3);

    driveBase = new DriveBase();
    clawBase = new ClawBase();
    armBase = new ArmBase();
    limelightBase = new LimelightBase();


    driveWithJoystick = new DriveWithJoystick();
    CommandScheduler.getInstance().setDefaultCommand(driveBase, driveWithJoystick);
    CommandScheduler.getInstance().setDefaultCommand(armBase, simpleArmRotationalControl);

    boxButton.onTrue(new BoxOpenClose());
    coneButton.onTrue(new ConeOpenClose());
    clawExtendButton.onTrue(new ClawInOut());

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
