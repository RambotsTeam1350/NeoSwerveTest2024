// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

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
  private final CommandXboxController m_driverController = new CommandXboxController(
    OperatorConstants.kDriverControllerPort);
  
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_Drivetrain = new Drivetrain();
  private final Shooter m_Shooter = new Shooter();
  
  private final Drive m_Drive = new Drive(m_Drivetrain, m_driverController);
  // private final SendableChooser<Command> m_chooser;

  // TODO: redo controller binding bc this is not the best way to do it
  // anymore but i have to actually know what the buttons do before i can fix it
  // the below code up until the constructor will likely be deleted after i change the binding methods

  // Controllers
  private final Joystick m_control = new Joystick(0);

  // Motor Test buttons
  private final JoystickButton m_MotorOut = new JoystickButton(m_control, XboxController.Button.kB.value);
  private final JoystickButton m_MotorIn = new JoystickButton(m_control, XboxController.Button.kA.value);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_Drivetrain.setDefaultCommand(m_Drive);
    // m_chooser = AutoBuilder.buildAutoChooser();
    // SmartDashboard.putData("Auto Chooser", m_chooser);
    // Configure the trigger bindings
    configureBindings();

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
    m_MotorOut.onTrue(new InstantCommand(() -> m_Shooter.setSpeed(0.2)));
    m_MotorOut.onFalse(new InstantCommand(() -> m_Shooter.setSpeed(0)));

    m_MotorIn.onTrue(new InstantCommand(() -> m_Shooter.setSpeed(-0.2)));
    m_MotorIn.onFalse(new InstantCommand(() -> m_Shooter.setSpeed(0)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  // return m_chooser.getSelected();
  // }
}
