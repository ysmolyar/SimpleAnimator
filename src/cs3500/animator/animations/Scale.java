package cs3500.animator.animations;

import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;

/**
 * A class to represent a scale function object.
 */
public class Scale extends AbstractAnimation {

  private float scaleX;
  private float scaleY;
  private float startWidth;
  private float startHeight;
  private AnimationName animationName = AnimationName.Scale;

  /**
   * A constructor for a scaling function object.
   *
   * @param shapeName   name of shape to be applied to
   * @param t0          starting time
   * @param t1          ending time
   * @param startWidth  starting width
   * @param startHeight starting height
   * @param scaleX      ending width
   * @param scaleY      ending height
   */
  public Scale(String shapeName, int t0, int t1, float startWidth,
               float startHeight, float scaleX, float scaleY) {
    super(shapeName, t0, t1);
    this.startWidth = startWidth;
    this.startHeight = startHeight;
    this.scaleX = scaleX;
    this.scaleY = scaleY;
  }

  /**
   * Gets starting width of scale.
   *
   * @return starting width
   */
  public float getStartWidth() {
    return this.startWidth;
  }

  /**
   * Gets starting height of scale.
   *
   * @return starting height.
   */
  public float getStartHeight() {
    return this.startHeight;
  }

  /**
   * Gets end width of scale.
   *
   * @return width after scale.
   */
  public float getScaleX() {
    return scaleX;
  }

  /**
   * Gets end height after scale.
   *
   * @return height after scale.
   */
  public float getScaleY() {
    return scaleY;
  }

  @Override
  public IShape animateRectangle(Rectangle r, int t) {
    if (t - this.getStartTime() >= 0) {
      return new Rectangle(r.getName(), r.getX(),
              r.getY(), r.getColor(),
              ((scaleX - startWidth) * getCompletionAtTimeT(t)) + startWidth,
              ((scaleY - startHeight) * getCompletionAtTimeT(t)) + startHeight);
    } else {
      return r;
    }
  }

  @Override
  public IShape animateOval(Oval o, int t) {
    if (t - this.getStartTime() >= 0) {
      return new Oval(o.getName(), o.getX(),
              o.getY(), o.getColor(),
              ((scaleX - startWidth) * getCompletionAtTimeT(t)) + startWidth,
              ((scaleY - startHeight) * getCompletionAtTimeT(t)) + startHeight);
    } else {
      return o;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Shape ").append(shapeName).append(" scales from ").append(getScaleFrom())
            .append(" to ").append(getScaleTo()).append(" " + getDuration(1));
    return sb.toString();
  }

  @Override
  public String toStringWithFPS(int fps) {
    StringBuilder sb = new StringBuilder();
    sb.append("Shape ").append(shapeName).append(" scales from ").append(getScaleFrom())
            .append(" to ").append(getScaleTo()).append(" " + getDuration(fps));
    return sb.toString();
  }

  @Override
  public String getAnimationName() {
    return this.animationName.toString();
  }

  @Override
  public Animation clone() {
    return new Scale(this.shapeName, this.t0, this.t1, this.startWidth, this.startHeight,
            this.scaleX, this.scaleY);
  }


  /**
   * Displays in String for the new width and height.
   *
   * @return String representation of post-scale width and height
   */
  private String getScaleTo() {
    return "Width: " + scaleX + ", Height: " + scaleY;
  }


  /**
   * Displays in String for the old width and height.
   *
   * @return String representation of pre-scale width and height
   */
  private String getScaleFrom() {
    return "Width: " + startWidth + ", Height: " + startHeight;
  }
}
