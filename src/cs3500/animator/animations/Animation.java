package cs3500.animator.animations;

import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;


/**
 * A function object interface whose sole responsibility is mapping a 2D Shape
 * to its "animated" 2D shape at time t.
 */
public interface Animation extends Cloneable {

  /**
   * Delegates animation of rectangle to implementing Animation class.
   *
   * @param r rectangle
   * @param t integer time
   * @return An updated rectangle
   */
  IShape animateRectangle(Rectangle r, int t);

  /**
   * Delegates animation of oval to implementing Animation class.
   *
   * @param o oval
   * @param t integer time
   * @return An updated oval
   */
  IShape animateOval(Oval o, int t);

  /**
   * Get the string representation of this Animation.
   *
   * @return String
   */
  String toString();

  /**
   * Get the string name of the Animation.
   *
   * @return String
   */
  String getAnimationName();

  /**
   * Get the shape associated with this animation.
   *
   * @return name of Shape
   */
  String getName();

  /**
   * Get the duration of this animation.
   *
   * @param fps frames per second
   * @return String
   */
  String getDuration(int fps);

  /**
   * Get the start time of this animation.
   *
   * @return int
   */
  int getStartTime();

  /**
   * Get the ending time of this animation.
   *
   * @return int
   */
  int getEndTime();

  /**
   * Get the completion of the animation at time t.
   *
   * @param t time
   * @return the Ratio of Animation Completion
   */
  float getCompletionAtTimeT(int t);


  /**
   * Returns identical Animation clone of this Animation.
   *
   * @return identical Animation clone
   */
  Animation clone();

  /**
   * Returns the lifetime of an Animation.
   *
   * @param framesPerSecond given frames per second
   * @return a String description of animation, start time, and end time.
   */
  String toStringWithFPS(int framesPerSecond);
}
