package cs3500.animator.view;

import java.util.Collection;

import cs3500.animator.shapes.ITextableAnimatedShape;

public interface ISvgView extends IView {

  /**
   * Method for svgView to receive the information from the model.
   *
   * @param shapes the ArrayList of ITextableAnimatedShape
   */
  void updateContents(Collection<ITextableAnimatedShape> shapes);
}
