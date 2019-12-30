package cs3500.animator.shapes;

import java.awt.Color;

/**
 * A class to represent an abstract shape.
 */
public abstract class AShape implements IShape {

  protected final String name;
  protected final String shapeType;
  protected final Pinhole pinhole;
  protected float x;
  protected float y;
  protected float width;
  protected float height;
  protected Color color;

  /**
   * A generic shape constructor.
   *
   * @param name      name of shape
   * @param shapeType type of shape
   * @param x         coordinate
   * @param y         coordinate
   * @param color     color of shape
   * @throws IllegalArgumentException if given negative dimensions
   */
  AShape(String name, String shapeType, Pinhole pinhole, float x, float y, float width,
         float height, Color color)
          throws IllegalArgumentException {
    this.name = name;
    this.shapeType = shapeType;
    this.pinhole = pinhole;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
  }

  /**
   * Protected enum of pinhole types, for printing description of coordinates.
   * This enum is to be used by all extending classes, to represent the pinhole
   * type of the coordinates.
   */
  protected enum Pinhole {
    Center, Corner
  }

  @Override
  public String toString() {
    return this.getDescription();
  }

  @Override
  public float getX() {
    return this.x;
  }

  @Override
  public float getY() {
    return this.y;
  }

  @Override
  public float getWidth() {
    return this.width;
  }

  @Override
  public float getHeight() {
    return this.height;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getDescription() {
    return "Name: " + getName()
            + "\nType: " + shapeType + "\n"
            + pinhole + ": " + getCoordinates();
  }

  @Override
  public String getCoordinates() {
    return "(" + x + "," + " " + y + ")";
  }

  @Override
  public IShape clone() {
    return this.clone();
  }

  /**
   * Ensure the width and height of this object are not negative.
   *
   * @param x width
   * @param y height
   */
  protected void ensureValidDimensions(float x, float y) {
    if (x <= 0 || y <= 0) {
      throw new IllegalArgumentException("Dimensions of shape can't be less than 1");
    }
  }

}
