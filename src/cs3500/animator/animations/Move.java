package cs3500.animator.animations;

import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;

/**
 * A class to represent a move transformation.
 */
public class Move extends AbstractAnimation {

  private float fromX;
  private float fromY;
  private float toX;
  private float toY;
  private AnimationName animationName = AnimationName.Move;

  /**
   * A move constructor.
   *
   * @param shapeName name of shape
   * @param t0        unit-less starting time
   * @param t1        unit-less ending time
   * @param fromX     from x coordinate
   * @param fromY     from y coordinate
   * @param toX       to x coordinate
   * @param toY       to y coordinate
   */
  public Move(String shapeName, int t0, int t1, float fromX,
              float fromY, float toX, float toY) {
    super(shapeName, t0, t1);
    this.fromX = fromX;
    this.fromY = fromY;
    this.toX = toX;
    this.toY = toY;
  }

  /**
   * Gets fromX Coordinate of move.
   *
   * @return fromX
   */
  public int getFromX() {
    return (int) fromX;
  }

  /**
   * Gets fromY Coordinate of move.
   *
   * @return fromY
   */
  public int getFromY() {
    return (int) fromY;
  }

  /**
   * Gets toX Coordinate of move.
   *
   * @return toX
   */
  public int getToX() {
    return (int) toX;
  }

  /**
   * Gets toY Coordinate of move.
   *
   * @return toY
   */
  public int getToY() {
    return (int) toY;
  }

  @Override
  public IShape animateRectangle(Rectangle r, int t) {
    if (super.ensureAnimation(r, t)) {
      return new Rectangle(r.getName(), ((toX - fromX) * getCompletionAtTimeT(t)) + fromX,
              ((toY - fromY) * getCompletionAtTimeT(t)) + fromY, r.getColor(),
              r.getWidth(), r.getHeight());
    } else {
      return r;
    }
  }

  @Override
  public IShape animateOval(Oval o, int t) {
    if (super.ensureAnimation(o, t)) {
      return new Oval(o.getName(), ((toX - fromX) * getCompletionAtTimeT(t)) + fromX,
              ((toY - fromY) * getCompletionAtTimeT(t)) + fromY, o.getColor(),
              o.getWidth(), o.getHeight());
    } else {
      return o;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Shape ").append(shapeName).append(" moves from ")
            .append(getMoveFrom()).append(" to " + getMoveTo() + " ")
            .append(getDuration(1));
    return sb.toString();
  }

  @Override
  public String toStringWithFPS(int fps) {
    StringBuilder sb = new StringBuilder();
    sb.append("Shape ").append(shapeName).append(" moves from ")
            .append(getMoveFrom()).append(" to " + getMoveTo() + " ")
            .append(getDuration(fps));
    return sb.toString();
  }

  @Override
  public String getAnimationName() {
    return this.animationName.toString();
  }

  @Override
  public Animation clone() {
    return new Move(this.shapeName, this.t0, this.t1, this.fromX, this.fromY, this.toX, this.toY);
  }

  /**
   * Get a string representation of to move coordinates.
   *
   * @return String
   */
  private String getMoveTo() {
    return "(" + toX + "," + " " + toY + ")";
  }

  /**
   * Get a string representation of from move coordinates.
   *
   * @return String
   */
  private String getMoveFrom() {
    return "(" + fromX + "," + " " + fromY + ")";
  }
}
