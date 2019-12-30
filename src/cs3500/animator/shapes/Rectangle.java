package cs3500.animator.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import cs3500.animator.animations.Animation;

/**
 * A class to represent a Rectangle.
 */
public class Rectangle extends AShape {

  private static final String TYPE = "rectangle";
  private static final Pinhole pinhole = Pinhole.Corner;
  private float width;
  private float height;

  /**
   * Represents a basic rectangle.
   * @param name name of rectangle
   * @param x coordinate
   * @param y coordinate
   * @param color color
   * @param width in integer units
   * @param height in integer units
   */

  public Rectangle(String name, float x, float y,
                   Color color, float width, float height) {
    super(name, TYPE, pinhole, x, y, width, height, color);
    super.ensureValidDimensions(width, height);
    this.width = width;
    this.height = height;
  }

  @Override
  public float getMaxWidth() {
    return this.x + this.width;
  }

  @Override
  public float getMaxHeight() {
    return this.y + this.height;
  }

  @Override
  public String getType() {
    return "rect";
  }

  @Override
  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(super.getDescription());
    sb.append(", ").append("Width: ").append(width).append(", Height: ").append(height);
    sb.append(", Color: (").append(getColor().getRed()).append(",").append(getColor().getGreen());
    sb.append(",").append(getColor().getBlue()).append(")");
    return sb.toString();
  }

  @Override
  public IShape acceptAnimation(Animation animation, int t) {
    return animation.animateRectangle(this ,t);
  }

  @Override
  public String getSize() {
    return "Width: " + width + ", Height: " + height;
  }

  @Override
  public IShape clone() {
    return new Rectangle(this.name, this.x, this.y, this.color, this.width, this.height);
  }

  @Override
  public void draw(Graphics2D g) {
    g.setColor(color);
    g.fillRect((int)x, (int)y, (int)width, (int)height);
  }


}
