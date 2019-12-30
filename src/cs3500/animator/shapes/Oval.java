package cs3500.animator.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import cs3500.animator.animations.Animation;

/**
 * A class to represent an Oval.
 */
public class Oval extends AShape {

  private static final String TYPE = "oval";
  private static final Pinhole pinhole = Pinhole.Center;
  private float radX;
  private float radY;

  /**
   * Every shape has a name and a starting x,y pair.
   *
   * @param name of shape
   * @param x    coordinate
   * @param y    coordinate
   */
  public Oval(String name, float x, float y,
              Color color, float radX, float radY) {
    super(name, TYPE, pinhole, x, y, radX, radY, color);
    super.ensureValidDimensions(radX, radY);
    this.radX = radX;
    this.radY = radY;
  }


  @Override
  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(super.getDescription());
    sb.append(", ").append("X radius: ").append(radX).append(", Y radius: ").append(radY);
    sb.append(", Color: (").append(getColor().getRed()).append(",").append(getColor().getGreen());
    sb.append(",").append(getColor().getBlue()).append(")");
    return sb.toString();
  }

  @Override
  public IShape acceptAnimation(Animation animation, int t) {
    return animation.animateOval(this, t);
  }

  @Override
  public String getType() {
    return "ellipse";
  }

  @Override
  public String getSize() {
    return "X radius: " + radX + ", Y radius: " + radY;
  }

  @Override
  public IShape clone() {
    return new Oval(this.name, this.x, this.y, this.color, this.radX, this.radY);
  }

  @Override
  public float getMaxWidth() {
    return this.x + (this.width / 2);
  }

  @Override
  public float getMaxHeight() {
    return this.y + (this.height / 2);
  }

  @Override
  public void draw(Graphics2D g) {
    g.setColor(color);
    g.fillOval((int) x, (int) y, (int) radX, (int) radY);
  }


}
