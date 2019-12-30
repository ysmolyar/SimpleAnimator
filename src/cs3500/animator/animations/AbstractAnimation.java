package cs3500.animator.animations;

import cs3500.animator.shapes.IShape;

/**
 * A class to represent an Abstract Animation.
 * All animations have a duration, t0 to t1.
 */
public abstract class AbstractAnimation implements Animation {

  protected String shapeName;
  protected int t0;
  protected int t1;

  /**
   * Constructor for abstract animation.
   *
   * @param shapeName name of shape being animated
   * @param t0        starting time of animation
   * @param t1        ending time of animation
   */
  AbstractAnimation(String shapeName, int t0, int t1) {
    this.shapeName = shapeName;
    this.t0 = t0;
    this.t1 = t1;
  }

  /**
   * Currently supported Animations.
   */
  protected enum AnimationName {
    Move, Scale, ColorChange
  }

  @Override
  public String getName() {
    return shapeName;
  }

  @Override
  public String getDuration(int fps) {
    float newT0 = t0 / fps;
    float newT1 = t1 / fps;
    StringBuilder sb = new StringBuilder();
    sb.append("from t = ").append(newT0).append("s to t = ").append(newT1).append("s");
    return sb.toString();
  }

  @Override
  public int getStartTime() {
    return t0;
  }

  @Override
  public int getEndTime() {
    return t1;
  }


  @Override
  public float getCompletionAtTimeT(int t) {
    if (t <= t0) {
      return 0;
    }
    if (t > t0 && t < t1) {
      return (float) (t - t0) / (float) (t1 - t0);
    } else {
      return 1;
    }
  }

  @Override
  public Animation clone() {
    return this.clone();
  }

  /**
   * Ensures that current time is greater than starting time and given shape
   * belongs to this animation.
   *
   * @param shape given shape
   * @param t     int time
   * @return true if conditions met
   */
  protected boolean ensureAnimation(IShape shape, int t) {
    return (this.shapeName.equals(shape.getName())) && (t - this.getStartTime() >= 0);
  }


}
