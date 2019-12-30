package cs3500.animator.view;

import java.io.IOException;

/**
 * TextView implements IView functionality, for an Text description representation of an animation.
 */
public class TextView implements IView {

  Appendable ap;
  String contents;

  /**
   * Default constructor for TextView, outputs to System.out
   */
  public TextView() {
    ap = System.out;
    contents = "";
  }

  /**
   * Constructor for Textview, takes the appendable to which the view should display.
   * Appendable could be System.out, or a file.
   *
   * @param ap appendable
   */
  public TextView(Appendable ap) {
    this.ap = ap;
    this.contents = "";
  }

  @Override
  public void display() {
    try {
      ap.append(contents);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() {
    System.exit(0);
  }


  /**
   * Method for textView to recieve the description from the model.
   *
   * @param contents the String representation of the textview contents
   */
  public void updateContents(String contents) {
    this.contents = contents;
  }

  @Override
  public String toString() {
    return "TXT";
  }

}
