package frc.robot;

import java.io.File;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* subsystems */
    private final Swerve drivebase = new Swerve(new File(Filesystem.getDeployDirectory(),
            "swerve"));

    CommandXboxController driverXbox = new CommandXboxController(Constants.Controllers.Driver.id);

    /* subsystems */

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        /* initialize all instance variables */
        /* subsystems */

        // Configure the button bindings
        configureButtonBindings();
        AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
                () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
                        Constants.Controllers.Driver.LEFT_Y_DEADBAND),
                () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
                        Constants.Controllers.Driver.LEFT_X_DEADBAND),
                () -> MathUtil.applyDeadband(driverXbox.getRightX(),
                        Constants.Controllers.Driver.RIGHT_X_DEADBAND),
                driverXbox.getHID()::getYButtonPressed,
                driverXbox.getHID()::getAButtonPressed,
                driverXbox.getHID()::getXButtonPressed,
                driverXbox.getHID()::getBButtonPressed);

        // Applies deadbands and inverts controls because joysticks
        // are back-right positive while robot
        // controls are front-left positive
        // left stick controls translation
        // right stick controls the desired angle NOT angular rotation
        Command driveFieldOrientedDirectAngle = drivebase.driveCommand(
                () -> MathUtil.applyDeadband(driverXbox.getLeftY(), Constants.Controllers.Driver.LEFT_Y_DEADBAND),
                () -> MathUtil.applyDeadband(driverXbox.getLeftX(), Constants.Controllers.Driver.LEFT_X_DEADBAND),
                () -> driverXbox.getRightX(),
                () -> driverXbox.getRightY());

        // Applies deadbands and inverts controls because joysticks
        // are back-right positive while robot
        // controls are front-left positive
        // left stick controls translation
        // right stick controls the angular velocity of the robot
        Command driveFieldOrientedAnglularVelocity = drivebase.driveCommand(
                () -> MathUtil.applyDeadband(driverXbox.getLeftY(), Constants.Controllers.Driver.LEFT_Y_DEADBAND),
                () -> MathUtil.applyDeadband(driverXbox.getLeftX(), Constants.Controllers.Driver.LEFT_X_DEADBAND),
                () -> driverXbox.getRawAxis(2));

        Command driveFieldOrientedDirectAngleSim = drivebase.simDriveCommand(
                () -> MathUtil.applyDeadband(driverXbox.getLeftY(), Constants.Controllers.Driver.LEFT_Y_DEADBAND),
                () -> MathUtil.applyDeadband(driverXbox.getLeftX(), Constants.Controllers.Driver.LEFT_X_DEADBAND),
                () -> driverXbox.getRawAxis(2));

        drivebase.setDefaultCommand(
                !RobotBase.isSimulation() ? driveFieldOrientedDirectAngle : driveFieldOrientedDirectAngleSim);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /*
         * this is where you bind controller buttons to actions. more information can be
         * found at
         * https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands
         * -to-triggers.html
         */
        driverXbox.a().onTrue(new InstantCommand(drivebase::zeroGyro));
        // new JoystickButton(driverXbox, 1).onTrue((new
        // InstantCommand(drivebase::zeroGyro)));
        // new JoystickButton(driverXbox, 3).onTrue(new
        // InstantCommand(drivebase::addFakeVisionReading));
        driverXbox.b().whileTrue(Commands.deferredProxy(() -> drivebase.driveToPose(
                new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))));
        // new JoystickButton(driverXbox,
        // 2).whileTrue(
        // Commands.deferredProxy(() -> drivebase.driveToPose(
        // new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))));
        driverXbox.x().whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));
        // new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new
        // InstantCommand(drivebase::lock, drivebase)));
    }

    public void setDriveMode() {
        // drivebase.setDefaultCommand();
    }

    public void setMotorBrake(boolean brake) {
        drivebase.setMotorBrake(brake);
    }
}