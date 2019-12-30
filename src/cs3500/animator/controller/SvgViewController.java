package cs3500.animator.controller;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.SvgView;

/**
 * A controller for SvgView animations. Controller takes a model and a view and run speed.
 */
public class SvgViewController implements IAnimatorController {

  private AnimatorModel model;
  private SvgView svgView;

  /**
   * Public constructor for SvgViewController. Only to be constructed using SvgView.
   *
   * @param model           the animator model
   * @param svgView         the SvgView view
   */
  public SvgViewController(AnimatorModel model, SvgView svgView) {
    this.model = model;
    this.svgView = svgView;
  }

  @Override
  public void run() {
    svgView.updateContents(model.getListOfAnimatedShapes());
    svgView.display();
  }

  @Override
  public void run(int tps) {
    svgView.updateContents(model.getListOfAnimatedShapes());
    svgView.display();
  }

}

