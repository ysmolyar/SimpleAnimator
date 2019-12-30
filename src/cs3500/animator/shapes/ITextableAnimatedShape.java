package cs3500.animator.shapes;

import java.util.ArrayList;

import cs3500.animator.animations.Animation;

/**
 * Read-Only Decorator interface for ITexableShape, includes the
 * the list of Animations which the shape experiences during the
 * duration of the animation. This interface is intended for implementation
 * whose constructor can take an ITextableShape and ArrayList of Animations.
 */
public interface ITextableAnimatedShape extends ITextableShape {

  /**
   * Creates a deep-copy of every Animation in this shape, and returns an ArrayList
   * of the Animations, with their order preserved.
   *
   * @return deep-copied ArrayList of Animations which are in this ITextableAnimatedShape
   */
  ArrayList<Animation> getListOfAnimations();
}
