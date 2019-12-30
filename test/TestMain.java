import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import cs3500.animator.controller.IAnimatorController;
import cs3500.animator.controller.InteractiveGuiController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.util.AnimationFileReader;
import cs3500.animator.util.TweenModelBuilder;
import cs3500.animator.view.InteractiveGuiView;

/**
 * Test for interactive entry to program.
 */
public class TestMain {

  /**
   * Main method for test entry to interactive view.
   * @param args the arguments to the program, from the client or user.
   */
  public static void main(String[] args) {

    AnimatorModel model;
    AnimationFileReader fileReader = new AnimationFileReader();
    TweenModelBuilder<AnimatorModel> builder = new AnimatorModelImpl.Builder();

    try {
      model = fileReader.readFile("resources/toh-8.txt", builder);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read file!");
    }

    Appendable outputDestination;
    OutputStream outputStream = null;
    PrintWriter printWriter = null;
    try {
      outputStream = new FileOutputStream(new File("testExport.svg"), false);
      printWriter = new PrintWriter(outputStream);
      printWriter.flush();
      outputDestination = printWriter;

    } catch (FileNotFoundException e) {
      outputDestination = System.out;
    }
    finally {
      try {
        outputStream.flush();
      }
      catch (IOException e) {
        //close stream silently
      }
    }

    IAnimatorController controller = new InteractiveGuiController(model,
            new InteractiveGuiView(20, outputDestination),
            20);
    controller.run();



  }
}
