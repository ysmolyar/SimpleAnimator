package cs3500.animator.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import cs3500.animator.animations.Animation;

/**
 * Implementation class of ITextableShape. This class is a decorator of an IShape, with
 * a time the shape appears and disappears.
 */
public class TextableShapeImpl implements ITextableShape {

  private IShape shape;
  private float appearsTime;
  private float disappearsTime;

  /**
   * Public constructor for TextableShapeImpl.
   *
   * @param shape          the IShape this ITextableShape is decorating
   * @param appearsTime    the start time of the shape
   * @param disappearsTime the end time of the shape
   */
  public TextableShapeImpl(IShape shape, float appearsTime, float disappearsTime) {
    this.appearsTime = appearsTime;
    this.disappearsTime = disappearsTime;
    this.shape = shape;
  }

  @Override
  public float getDisappearsTime() {
    return this.disappearsTime;
  }

  @Override
  public float getAppearsTime() {
    return this.appearsTime;
  }

  @Override
  public String getSvgX() {
    switch (shape.getType()) {
      case "ellipse":
        return "cx";
      case "rect":
        return "x";
      default:
        throw new IllegalArgumentException("Illegal shape!");
    }
  }

  @Override
  public String getSvgY() {
    switch (shape.getType()) {
      case "ellipse":
        return "cy";
      case "rect":
        return "y";
      default:
        throw new IllegalArgumentException("Illegal shape!");
    }
  }

  @Override
  public String getSvgWidth() {
    switch (shape.getType()) {
      case "ellipse":
        return "rx";
      case "rect":
        return "width";
      default:
        throw new IllegalArgumentException("Illegal shape!");
    }
  }

  @Override
  public String getSvgHeight() {
    switch (shape.getType()) {
      case "ellipse":
        return "ry";
      case "rect":
        return "height";
      default:
        throw new IllegalArgumentException("Illegal shape!");
    }
  }

  @Override
  public float getX() {
    return shape.getX();
  }

  @Override
  public float getY() {
    return shape.getY();
  }

  @Override
  public float getWidth() {
    return shape.getWidth();
  }

  @Override
  public float getHeight() {
    return shape.getHeight();
  }

  @Override
  public float getMaxWidth() {
    return shape.getMaxWidth();
  }

  @Override
  public float getMaxHeight() {
    return shape.getMaxHeight();
  }

  @Override
  public Color getColor() {
    return shape.getColor();
  }

  @Override
  public String getDescription() {
    return shape.getDescription();
  }

  @Override
  public String getName() {
    return shape.getName();
  }

  @Override
  public IShape acceptAnimation(Animation animation, int t) {
    return shape.acceptAnimation(animation, t);
  }

  @Override
  public String getCoordinates() {
    return shape.getCoordinates();
  }

  @Override
  public String getSize() {
    return shape.getSize();
  }

  @Override
  public ITextableShape clone() {
    return new TextableShapeImpl(shape.clone(), this.appearsTime, this.disappearsTime);
  }

  @Override
  public void draw(Graphics2D g) {
    shape.draw(g);
  }

  @Override
  public String toString() {
    return shape.toString() + "\nAppears at t=" + getAppearsTime() + "s\nDisappears at t=" +
            getDisappearsTime() + "s\n";
  }

  @Override
  public String toStringWithFPS(int framesPerSecond) {
    float newAppearsTime = appearsTime / framesPerSecond;
    float newDisappearsTime = disappearsTime / framesPerSecond;
    return shape.toString() + "\nAppears at t=" + newAppearsTime + "s\nDisappears at t=" +
            newDisappearsTime + "s\n";
  }

  @Override
  public String getType() {
    return shape.getType();
  }

  @Override
  public IShape getShape() {
    return shape.clone();
  }
}
