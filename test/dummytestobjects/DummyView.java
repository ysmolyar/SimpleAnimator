package dummytestobjects;


import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import cs3500.animator.view.GuiView;
import cs3500.animator.view.ISvgView;
import cs3500.animator.view.InteractiveGuiView;

public class DummyView extends InteractiveGuiView {

  protected ISvgView svgView;
  protected GuiView guiView;
  protected JPanel listOfButtons;
  protected JButton start;
  protected JToggleButton pause;
  protected JButton restart;
  protected JButton exportSvg;
  protected JToggleButton loop;
  protected JButton increment;
  protected JButton decrement;

  /**
   * Default constructor for interactiveGuiView.
   */
  public DummyView(int framesPerSecond, Appendable output) {
    super(framesPerSecond, output);
  }
}
