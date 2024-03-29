// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DriveRobot;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.MotorControllerTest;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final SendableChooser<Command> m_chooser;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);


  //Controllers
  private final Joystick control = new Joystick(0);


  //Motor Test buttons
    private final JoystickButton motorOut = new JoystickButton(control, XboxController.Button.kB.value);
    private final JoystickButton motorIn = new JoystickButton(control, XboxController.Button.kA.value);

  private final DriveRobot m_drive = new DriveRobot(m_drivetrain, m_driverController);

  //Motor objects
  public final MotorControllerTest m_motor = new MotorControllerTest();

  //Button configs
  private void configureButtonBindings() {
      //Driver buttons
      motorOut.onTrue(new InstantCommand(() -> m_motor.motorSpeed(0.2)));
      motorOut.onFalse(new InstantCommand(() -> m_motor.motorSpeed(0)));

      motorIn.onTrue(new InstantCommand(() -> m_motor.motorSpeed(-0.2)));
      motorIn.onFalse(new InstantCommand(() -> m_motor.motorSpeed(0)));

    }

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_drivetrain.setDefaultCommand(m_drive);
    m_chooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", m_chooser);
    // Configure the trigger bindings
    configureBindings();
    configureButtonBindings();


    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
