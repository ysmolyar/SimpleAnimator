package cs3500.animator.controller;

/**
 * This interface represents the operations offered by a Simple Animation Controller.
 */
public interface IAnimatorController {

  /**
   * Starts the animation.
   */
  void run();

  /**
   * Runs the animation, with a speed parameter for frames per second.
   */
  void run(int fps);
}
