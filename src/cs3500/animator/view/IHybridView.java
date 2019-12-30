package cs3500.animator.view;

import java.awt.event.ActionListener;

/**
 * A hybrid view interface. A hybrid view should set listeners and be able to download and close
 * svg
 */
public interface IHybridView extends IGuiView, ISvgView  {

  /**
   * Appends the SVG XML to this IHybridView's appendable.
   */
  void downloadSVG();

  /**
   * Closes the SVG.
   */
  void closeSVG();

  /**
   * Sets the mouseListeners to their respective ActionListeners.
   */
  void setListeners(ActionListener clicks);
}
