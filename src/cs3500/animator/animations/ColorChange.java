package cs3500.animator.animations;

import java.awt.Color;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;

/**
 * A function object class to represent a change in color.
 */
public class ColorChange extends AbstractAnimation {

  private Color colorFrom;
  private Color colorTo;
  private AnimationName animationName = AnimationName.ColorChange;

  /**
   * Constructor for color change function object.
   *
   * @param shapeName name of shape
   * @param t0        starting time
   * @param t1        ending time
   * @param colorFrom starting color
   * @param colorTo   ending color
   */
  public ColorChange(String shapeName, int t0, int t1, Color colorFrom, Color colorTo) {
    super(shapeName, t0, t1);
    this.colorFrom = colorFrom;
    this.colorTo = colorTo;
  }

  /**
   * Returns from Color of animation.
   *
   * @return fromColor
   */
  public Color getColorFrom() {
    return new Color(colorFrom.getRed(), colorFrom.getGreen(), colorFrom.getBlue());
  }

  /**
   * Returns new Color after animation.
   *
   * @return post-animation Color
   */
  public Color getColorTo() {
    return new Color(colorTo.getRed(), colorTo.getGreen(), colorTo.getBlue());
  }

  @Override
  public IShape animateRectangle(Rectangle r, int t) {
    if (super.ensureAnimation(r, t)) {
      return new Rectangle(r.getName(), r.getX(), r.getY(), getColorAtTimeT(t),
              r.getWidth(), r.getHeight());
    } else {
      return r;
    }
  }

  @Override
  public IShape animateOval(Oval o, int t) {
    if (super.ensureAnimation(o, t)) {
      return new Oval(o.getName(), o.getX(), o.getY(), getColorAtTimeT(t),
              o.getWidth(), o.getHeight());
    } else {
      return o;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Shape ").append(shapeName).append(" changes color from ")
            .append(colorFrom.toString())
            .append(" to ").append(colorTo.toString() + " ").append(getDuration(1));
    return sb.toString();
  }

  @Override
  public String toStringWithFPS(int fps) {
    StringBuilder sb = new StringBuilder();
    sb.append("Shape ").append(shapeName).append(" changes color from ")
            .append(colorFrom.toString())
            .append(" to ").append(colorTo.toString() + " ").append(getDuration(fps));
    return sb.toString();
  }

  @Override
  public String getAnimationName() {
    return this.animationName.toString();
  }

  @Override
  public Animation clone() {
    return new ColorChange(this.shapeName, this.t0, this.t1, this.colorFrom, this.colorTo);
  }

  /**
   * Calculates color in transition of animation at time t.
   *
   * @param t time
   * @return Color at time t
   */
  private Color getColorAtTimeT(int t) {
    double dRed = (colorTo.getRed() - colorFrom.getRed()) * getCompletionAtTimeT(t) +
            colorFrom.getRed();
    double dBlue = (colorTo.getBlue() - colorFrom.getBlue()) * getCompletionAtTimeT(t) +
            colorFrom.getBlue();
    double dGreen = (colorTo.getGreen() - colorFrom.getGreen()) * getCompletionAtTimeT(t) +
            colorFrom.getGreen();
    Color newColor = new Color((int) dRed, (int) dGreen, (int) dBlue);
    return newColor;
  }
}
