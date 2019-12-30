package cs3500.animator.controller;

import java.util.Timer;
import java.util.TimerTask;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.IHybridView;

/**
 * A controller for GUI view. Controller takes a model and a view and run speed.
 */
public class AdvancedGuiController  {

  protected AnimatorModel model;
  protected IHybridView view;
  protected int framesPerSecond;
  private Timer timer;
  private UpdateInteractiveScreen timerMethod;

  /**
   * Public constructor for AdvancedGuiAnimatorController.
   *
   * @param model           the animator model
   * @param view            the GuiView
   * @param framesPerSecond the speed in frames per second of the animation
   */
  public AdvancedGuiController(AnimatorModel model, IHybridView view, int framesPerSecond) {
    this.model = model;
    this.view = view;
    this.framesPerSecond = framesPerSecond;
    this.timer = new Timer();
    this.timerMethod = new UpdateInteractiveScreen(this.model, this.view);
  }

  /**
   * Run method to advance the Controller.
   *
   * @param t the frame or time t to display.
   */
  public void run(int t) {
    float f = (1 / (float) t) * 1000;
    long l = (long) f;
    view.updateContents(model.getListOfAnimatedShapes());
    timer.scheduleAtFixedRate(this.timerMethod, 0, l);
    view.display();
  }

  /**
   * Restarts the timer.
   */
  protected void restart() {
    this.timerMethod.restart();
  }

  /**
   * Resumes the timer.
   */
  protected void resume() {
    this.timerMethod.resume();
  }

  /**
   * Pauses the timer.
   */
  protected void pause() {
    this.timerMethod.pause();
  }

  /**
   * Enables loop mode.
   */
  protected void enableLoop() {
    this.timerMethod.enableLoop();
  }

  /**
   * Disables loop mode.
   */
  protected void disableLoop() {
    this.timerMethod.disableLoop();
  }

  /**
   * Starts the animation.
   */
  protected void start() {
    this.timerMethod.start();
  }

  /**
   * Adjusts the speed of the animation.
   *
   * @param t frames per second by which speed is to be adjusted
   */
  protected void adjustSpeed(int t) {
    this.framesPerSecond += t;
  }

  protected void downloadSVG() {
    this.view.downloadSVG();
  }


  /**
   * UpdateScreen is a TimerTask used to update the GuiView.
   */
  private class UpdateInteractiveScreen extends TimerTask {

    private int frame;
    private boolean hasBeenStarted;
    private boolean isPaused;
    private boolean isLooped;
    private AnimatorModel model;
    private IHybridView view;

    UpdateInteractiveScreen(AnimatorModel model, IHybridView view) {
      this.model = model;
      this.view = view;
      hasBeenStarted = false;
      isPaused = false;
      frame = 0;
    }

    private void start() {
      this.hasBeenStarted = true;
    }

    private void restart() {
      this.frame = 0;
    }

    private void pause() {
      this.isPaused = true;
    }

    private void resume() {
      this.isPaused = false;
    }

    private void enableLoop() {
      this.isLooped = true;
    }

    private void disableLoop() {
      this.isLooped = false;
    }

    @Override
    public void run() {
      if (hasBeenStarted) {
        if (frame <= model.getNumberOfFrames()) {
          if (!isPaused) {
            view.updateScreen(model.getCurrentSceneAt(frame));
            frame++;
          } else {
            view.updateScreen(model.getCurrentSceneAt(frame));
          }
        } else {
          if (isLooped) {
            this.frame = 0;
          } else {
            view.updateScreen(model.getCurrentSceneAt(model.getNumberOfFrames()));
          }
        }
      }
    }
  }
}