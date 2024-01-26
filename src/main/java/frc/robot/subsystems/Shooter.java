package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private CANSparkMax motor1;
  private CANSparkMax motor2;

  public Shooter() {
    motor1 = new CANSparkMax(Constants.Motors.Shooter.ids[0], MotorType.kBrushless);
    motor2 = new CANSparkMax(Constants.Motors.Shooter.ids[1], MotorType.kBrushless);
    motor1.setIdleMode(IdleMode.kBrake);
    motor2.setIdleMode(IdleMode.kBrake);
  }

  public void setSpeed(double speed) {
    motor1.set(speed);
    motor2.set(-speed);
  }

  public void stop() {
    motor1.set(0);
    motor2.set(0);
  }

  public CANSparkMax getMotor1() {
    return this.motor1;
  }

  public CANSparkMax getMotor2() {
    return this.motor2;
  }
}