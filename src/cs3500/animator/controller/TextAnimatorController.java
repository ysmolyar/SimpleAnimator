package cs3500.animator.controller;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.TextView;

/**
 * A controller for TextView animations. Controller takes a model and a view and run speed.
 */
public class TextAnimatorController implements IAnimatorController {

  private AnimatorModel model;
  private TextView textView;
  private int framesPerSecond;

  /**
   * Public constructor for TextAnimatorController. Only to be constructed using TextViews
   *
   * @param model the animator model
   * @param textView the TextView view
   * @param framesPerSecond the speed in frames per second
   */
  public TextAnimatorController(AnimatorModel model, TextView textView, int framesPerSecond) {
    this.model = model;
    this.textView = textView;
    this.framesPerSecond = framesPerSecond;
  }

  @Override
  public void run() {
    textView.updateContents(model.getAnimationDescription(framesPerSecond));
    textView.display();
  }

  @Override
  public void run(int tps) {
    run();
  }
}
