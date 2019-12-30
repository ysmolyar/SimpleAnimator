package cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import cs3500.animator.shapes.IShape;

/**
 * A class to represent a DrawPanel for a GuiView.
 */
public class DrawPanel extends JPanel {

  ArrayList<IShape> shapes;

  /**
   * Default constructor for a DrawPanel.
   */
  DrawPanel() {
    shapes = new ArrayList<>();
    setBackground(Color.WHITE);
    setPreferredSize(new Dimension(700, 700));
  }

  /**
   * Constructor for DrawPanel that takes a list of shapes to draw.
   * @param shapesToDraw array list of shapes to draw
   */
  DrawPanel(ArrayList<IShape> shapesToDraw) {
    shapes = shapesToDraw;
    setBackground(Color.WHITE);
    setPreferredSize(new Dimension(700, 700));
  }

  /**
   * Overrides JComponent paint method.
   * @param g graphics card.
   */
  public void paint(Graphics g) {

    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g2);

    for (IShape shape : shapes) {
      shape.draw(g2);
    }

  }

  /**
   * Updates the screen.
   * EFFECT: changes reference of this.shape to given array list.
   * @param newShapes array list of IShapes
   */
  public void updateScreen(ArrayList<IShape> newShapes) {
    if (newShapes != null) {
      shapes = newShapes;
      repaint();
    }
  }

}
