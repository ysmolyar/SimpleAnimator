package dummytestobjects;

import java.awt.event.ActionEvent;

import cs3500.animator.controller.InteractiveGuiController;
import cs3500.animator.model.AnimatorModel;


public class DummyController extends InteractiveGuiController {

  private String buttonDescription = "";

  /**
   * Public constructor for Dummy Controller.
   *
   * @param model           a null animator model
   * @param dumbView        the DummyView object
   * @param framesPerSecond the speed in frames per second of the animation
   */
  public DummyController(AnimatorModel model, DummyView dumbView, int framesPerSecond) {
    super(model, dumbView, framesPerSecond);
    dumbView.setListeners(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String string;
    switch (e.getActionCommand()) {
      case "start":
        this.buttonDescription = "start-button-clicked";
        break;
      case "svg":
        this.buttonDescription = "svg-button-clicked";
        break;
      case "restart":
        this.buttonDescription = "restart-button-clicked";
        break;
      case "pause":
        this.buttonDescription = "pause-button-clicked";
        break;
      case "resume":
        this.buttonDescription = "resume-button-clicked";
        break;
      case "enableLoop":
        this.buttonDescription = "loop-button-clicked";
        break;
      case "disableLoop":
        this.buttonDescription = "loop-button-unclicked";
        break;
      case "decrement":
        this.buttonDescription = "decrement-button-clicked";
        break;
      case "increment":
        this.buttonDescription = "increment-button-clicked";
        break;
      default:
        throw new IllegalArgumentException("Unsupported operation!");
    }

  }


  /**
   * Method to return the description of the dummy button click.
   */
  public String getButtonDescription() {
    return this.buttonDescription;
  }
}
