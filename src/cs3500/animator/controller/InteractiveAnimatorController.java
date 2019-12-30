package cs3500.animator.controller;

/**
 * A controller interface that promises run, restart, pause, resume, loop, and speed adjustment.
 */
public interface InteractiveAnimatorController extends IAnimatorController {

  /**
   * Run the animation with given fps.
   * @param fps frames per second
   */
  void run(int fps);

  /**
   * Restart the animation.
   */
  void restart();

  /**
   * Pause the animation.
   */
  void pause();

  /**
   * Resume the animation.
   */
  void resume();

  /**
   * Enable looping.
   */
  void enableLoop();

  /**
   * Disable looping.
   */
  void disableLoop();

  /**
   * Start the animation.
   */
  void start();

  /**
   * Adjust the speed.
   * @param t speed to be adjusted.
   */
  void adjustSpeed(int t);

  /**
   * Download this svg.
   */
  void downloadSVG();

}
