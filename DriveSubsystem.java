// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.math.filter.SlewRateLimiter;

public class DriveSubsystem extends SubsystemBase {
  // The motors on the left side of the drive.

  private final TalonSRX m_leftLeader = new TalonSRX(11);
  private final TalonSRX m_leftFollower = new TalonSRX(9);

  // The motors on the right side of the drive.
  private final TalonSRX m_rightLeader = new TalonSRX(13);
  private final TalonSRX m_rightFollower = new TalonSRX(12);


  
	public void setMotors(double left, double right) {
    left = scaleLeft(left);
    right = scaleRight(right);
    
    setMotorsRaw(left, right);
  }
  
  public void setMotorsRaw(double left, double right) {
    double velocidadleft = safetyTest(left);
    double velocidadright = safetyTest(right);
    
    m_leftLeader.set(ControlMode.PercentOutput, -velocidadleft);
    m_leftFollower.set(ControlMode.PercentOutput, -velocidadleft);
    m_rightLeader.set(ControlMode.PercentOutput, velocidadright);	
    m_rightFollower.set(ControlMode.PercentOutput, velocidadright);
}
  
  private double safetyTest(double motorValue) {
      motorValue = (motorValue < -1) ? -1 : motorValue;
      motorValue = (motorValue > 1) ? 1 : motorValue;
      
      return motorValue;
  }
  
  private double scaleLeft(double left) {
    return 0.5 * left;
  }
  
  private double scaleRight(double right) {
    return 0.5 * right;
  }

  /* 
  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader::set, m_rightLeader::set);

  // The left-side drive encoder
  private final Encoder m_leftEncoder =
      new Encoder(
          DriveConstants.kLeftEncoderPorts[0],
          DriveConstants.kLeftEncoderPorts[1],
          DriveConstants.kLeftEncoderReversed);

  // The right-side drive encoder
  private final Encoder m_rightEncoder =
      new Encoder(
          DriveConstants.kRightEncoderPorts[0],
          DriveConstants.kRightEncoderPorts[1],
          DriveConstants.kRightEncoderReversed);

  /** Creates a new DriveSubsystem. 
  public DriveSubsystem() {
    SendableRegistry.addChild(m_drive, m_leftLeader);
    SendableRegistry.addChild(m_drive, m_rightLeader);

    m_leftLeader.addFollower(m_leftFollower);
    m_rightLeader.addFollower(m_rightFollower);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightLeader.setInverted(true);

    // Sets the distance per pulse for the encoders
    m_leftEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);
    m_rightEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  /** Resets the drive encoders to currently read a position of 0. 
  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  /**
   * Gets the average distance of the TWO encoders.
   *
   * @return the average of the TWO encoder readings
   
  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
  }

  
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    super.initSendable(builder);
    // Publish encoder distances to telemetry.
    builder.addDoubleProperty("leftDistance", m_leftEncoder::getDistance, null);
    builder.addDoubleProperty("rightDistance", m_rightEncoder::getDistance, null);
  }*/
}
