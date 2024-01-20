// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax; 
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MotorControllerTest extends SubsystemBase {
  CANSparkMax motor1;
  CANSparkMax motor2;

  
  public MotorControllerTest() {
    motor1 = new CANSparkMax(Constants.motor1, MotorType.kBrushed);
    motor2 = new CANSparkMax(Constants.motor2, MotorType.kBrushed);

    motor1.setIdleMode(IdleMode.kBrake);
    motor2.setIdleMode(IdleMode.kBrake);

  }  

  public void motorSpeed(double speed) {
    motor1.set(speed);
    motor2.set(speed);

    System.out.println(speed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  
}