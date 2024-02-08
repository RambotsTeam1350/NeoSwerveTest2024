package frc.robot.utilities;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants.Operator;

public class MathUtils {
  public static double toUnitCircAngle(double angle) {
    double rotations = angle / (2 * Math.PI);
    return (angle - Math.round(rotations - 0.500) * Math.PI * 2.0);
  }

  public static double cubicLinear(double input, double a, double b) {
    return (a * Math.pow(input, 3) + b * input);
  }

  public static double applyDeadband(double input) {
    if (Math.abs(input) < Operator.kDeadband) {
      return 0.0;
    } else if (input < 0.0) {
      return (input + Operator.kDeadband) * (1.0 / (1 - Operator.kDeadband));
    } else if (input > 0.0) {
      return (input - Operator.kDeadband) * (1.0 / (1 - Operator.kDeadband));
    } else {
      return 0.0;
    }
  }

  public static double inputTransform(double input) {
    return cubicLinear(applyDeadband(input), Operator.kCubic, Operator.kLinear);
  }

  public static double[] inputTransform(double x, double y) {
    x = applyDeadband(x);
    y = applyDeadband(y);
    double mag = new Translation2d(x, y).getDistance(new Translation2d());

    if (mag > 1.00) {
      mag = 1.00;
    }

    if (mag != 0) {
      x = x / mag * cubicLinear(mag, Operator.kCubic, Operator.kLinear);
      y = y / mag * cubicLinear(mag, Operator.kCubic, Operator.kLinear);
    } else {
      x = 0;
      y = 0;
    }

    return new double[] { x, y };
  }

}
