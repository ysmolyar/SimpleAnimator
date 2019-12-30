package cs3500.animator.view;

import java.util.ArrayList;

import javax.swing.SwingConstants;

import cs3500.animator.shapes.IShape;

public interface IGuiView extends IView, SwingConstants {

  /**
   * Method for GuiView to recieve the shapes for the next frame from the model.
   *
   * @param shapes the IShapes for the next frame
   */
  void updateScreen(ArrayList<IShape> shapes);

}
