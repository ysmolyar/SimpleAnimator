package cs3500.animator.model;

import java.util.ArrayList;

import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.ITextableAnimatedShape;

/**
 * An interface to represent user operations of an animatormodel. This
 * Model will be used to animate 2D objects.
 */
public interface AnimatorModel {

  /**
   * Get the contents of the model.
   *
   * @return a String representation of all shapes and animations contained
   */
  String getAnimationDescription(int fps);

  /**
   * Gets the result of applying each animation to its associated shape at time t.
   *
   * @param t time
   * @return the current state of the animation
   * @throws IllegalArgumentException when given any t < 0 or t > this.frames
   */
  ArrayList<IShape> getCurrentSceneAt(int t) throws IllegalArgumentException;

  /**
   * Returns the list of animations.
   *
   * @return String representation of animations
   */
  String printListOfAnimations(int fps);

  /**
   * Returns the list of shapes in the model.
   *
   * @return String representation of shapes
   */
  String printListOfShapes(int fps);

  /**
   * Returns the list of TextableAnimatedShapes in the model, which contain
   * the shape and all animations applied to that shape.
   *
   * @return deep copied ArrayList of ITextableAnimatedShapes.
   */
  ArrayList<ITextableAnimatedShape> getListOfAnimatedShapes();

  /**
   * Get the number of frames in the model.
   *
   * @return int representing the number of frames
   */
  int getNumberOfFrames();

}
