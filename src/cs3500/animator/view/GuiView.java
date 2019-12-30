package cs3500.animator.view;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import cs3500.animator.shapes.IShape;

/**
 * Gui implements IView functionality, visual representation.
 * GuiView extends JFrame and uses java.swing.
 */
public class GuiView extends JFrame implements IGuiView {

  protected DrawPanel drawPanel;
  protected JScrollPane jScrollPane;

  /**
   * Default constructor for GuiView.
   */
  public GuiView() {
    setTitle("Easy 2D Animator");
    setSize(1000, 1000);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    drawPanel = new DrawPanel();
    jScrollPane = new JScrollPane(drawPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    jScrollPane.setBounds(0, 0, drawPanel.getWidth(), drawPanel.getHeight());
    add(jScrollPane);
  }

  /**
   * Method for GuiView to recieve the shapes for the next frame from the model.
   *
   * @param shapes the shapes for the next frame
   */
  public void updateScreen(ArrayList<IShape> shapes) {
    this.drawPanel.updateScreen(shapes);
  }

  @Override
  public void display() {
    this.jScrollPane.setVisible(true);
  }

  @Override
  public void close() {
    this.jScrollPane.setVisible(false);
  }

  @Override
  public String toString() {
    return "GUI";
  }

  @Override
  public boolean isVisible() {
    return this.jScrollPane.isVisible();
  }


}
