package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.IHybridView;

/**
 * The implementation of an InteractiveGuiController.
 *
 */
public class InteractiveGuiController extends AdvancedGuiController implements ActionListener,
        InteractiveAnimatorController {

  /**
   * Public constructor for GuiAnimatorController.
   *
   * @param model           the animator model
   * @param guiView            the GuiView
   * @param framesPerSecond the speed in frames per second of the animation
   *
   */
  public InteractiveGuiController(AnimatorModel model, IHybridView guiView,
                                  int framesPerSecond) {
    super(model, guiView, framesPerSecond);
    super.view.updateContents(model.getListOfAnimatedShapes());
    super.view.setListeners(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "start":
        this.start();
        break;
      case "svg":
        view.downloadSVG();
        view.closeSVG();
        break;
      case "restart":
        this.restart();
        break;
      case "pause":
        this.pause();
        break;
      case "resume":
        this.resume();
        break;
      case "enableLoop":
        this.enableLoop();
        break;
      case "disableLoop":
        this.disableLoop();
        break;
      case "decrement":
        this.adjustSpeed(-1);
        break;
      case "increment":
        this.adjustSpeed(1);
        break;
      default:
        throw new IllegalArgumentException("Unsupported operation!");
    }
  }

  @Override
  public void restart() {
    super.restart();
  }

  @Override
  public void pause() {
    super.pause();
  }

  @Override
  public void resume() {
    super.resume();
  }

  @Override
  public void enableLoop() {
    super.enableLoop();
  }

  @Override
  public void disableLoop() {
    super.disableLoop();
  }

  @Override
  public void start() {
    super.start();
  }

  @Override
  public void adjustSpeed(int t) {
    super.adjustSpeed(t);
  }

  @Override
  public void downloadSVG() {
    super.downloadSVG();
  }

  @Override
  public void run(int t) {
    super.run(t);
  }

  @Override
  public void run() {
    super.run(framesPerSecond);
  }
}
