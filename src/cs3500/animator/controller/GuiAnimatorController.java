package cs3500.animator.controller;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.IGuiView;

/**
 * A controller for GUI view. Controller takes a model and a view and run speed.
 */
public class GuiAnimatorController implements IAnimatorController {

  protected AnimatorModel model;
  protected IGuiView view;
  protected int framesPerSecond;
  protected Timer timer = new Timer();

  /**
   * Public constructor for GuiAnimatorController.
   *
   * @param model           the animator model
   * @param view            the GuiView
   * @param framesPerSecond the speed in frames per second of the animation
   */
  public GuiAnimatorController(AnimatorModel model, IGuiView view, int framesPerSecond) {
    Objects.requireNonNull(view);
    this.model = model;
    this.view = view;
    this.framesPerSecond = framesPerSecond;
  }

  @Override
  public void run() {
    float f = (1 / (float) framesPerSecond) * 1000;
    long l = (long) f;
    timer.scheduleAtFixedRate(new UpdateScreen(model, view), 0, l);
    view.display();
  }

  @Override
  public void run(int tps) {
    float f = (1 / (float) tps) * 1000;
    long l = (long) f;
    timer.scheduleAtFixedRate(new UpdateScreen(model, view), 0, l);
    view.display();
  }

  /**
   * UpdateScreen is a TimerTask used to update the GuiView.
   */
  private class UpdateScreen extends TimerTask {

    private int frame;
    private AnimatorModel model;
    private IGuiView view;

    UpdateScreen(AnimatorModel model, IGuiView view) {
      this.model = model;
      this.view = view;
      frame = 0;
    }

    @Override
    public void run() {
      if (frame <= model.getNumberOfFrames()) {
        view.updateScreen(model.getCurrentSceneAt(frame));
        frame++;
      } else {
        view.updateScreen(model.getCurrentSceneAt(model.getNumberOfFrames()));
      }
    }
  }
}
