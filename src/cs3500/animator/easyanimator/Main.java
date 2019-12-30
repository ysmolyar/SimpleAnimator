package cs3500.animator.easyanimator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import cs3500.animator.controller.GuiAnimatorController;
import cs3500.animator.controller.IAnimatorController;
import cs3500.animator.controller.InteractiveGuiController;
import cs3500.animator.controller.SvgViewController;
import cs3500.animator.controller.TextAnimatorController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.view.GuiView;
import cs3500.animator.view.IHybridView;
import cs3500.animator.view.IView;
import cs3500.animator.view.InteractiveGuiView;
import cs3500.animator.view.SvgView;
import cs3500.animator.view.TextView;
import cs3500.animator.util.AnimationFileReader;
import cs3500.animator.util.TweenModelBuilder;

/**
 * Main entry into MVC animator.
 */
public class Main {

  /**
   * Main method which takes command line arguments to run the animator program
   * -if is followed by the name of the file
   * -iv is followed by the view type
   * -o is the output destination (file or System.out)
   * -speed sets the ticks per second, default is 1
   *
   * @param args String arguments
   */
  public static void main(String[] args) {
    String viewType = "";
    String animationFileName = "";
    String output = "";
    Appendable outputDestination = System.out;
    int ticksPerSecond = 1;

    try {
      for (int i = 0; i < args.length; i++) {
        switch (args[i]) {
          case "-if":
            animationFileName = args[i + 1];
            break;
          case "-iv":
            viewType = args[i + 1];
            break;
          case "-o":
            output = args[i + 1];
            break;
          case "-speed":
            ticksPerSecond = Integer.parseInt(args[i + 1]);
            break;
          default: //throw popup error
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid arguments!");
    }

    //PARSING THE OUTPUT DESTINATION FROM USER, if it is to a file
    OutputStream outputStream = null;
    if (output.contains(".txt") || output.contains(".svg")) {
      try {
        outputStream = new FileOutputStream(new File(output));
        outputDestination = new PrintWriter(outputStream);
      } catch (FileNotFoundException e) {
        // throw popup error
      }
    }


    //Creating the model from the AnimiationFileReader of the input, and then assigning the
    //controller and view based on inputs from the user
    AnimationFileReader fileReader = new AnimationFileReader();
    TweenModelBuilder<AnimatorModel> builder = new AnimatorModelImpl.Builder();
    AnimatorModel model;
    IAnimatorController controller;
    IView view;

    try {
      model = fileReader.readFile(animationFileName, builder);
    } catch (Exception e) {
      throw new IllegalArgumentException("Cannot read file!");
    }


    //delegating views depending on user input
    switch (viewType) {
      case "visual":
        view = new GuiView();
        controller = new GuiAnimatorController(model, (GuiView) view, ticksPerSecond);
        controller.run();
        break;
      case "text":
        view = new TextView(outputDestination);
        controller = new TextAnimatorController(model, (TextView) view, ticksPerSecond);
        controller.run();
        break;
      case "svg":
        view = new SvgView(ticksPerSecond, outputDestination);
        controller = new SvgViewController(model, (SvgView) view);
        controller.run();
        break;
      case "interactive":
        view = new InteractiveGuiView(ticksPerSecond, outputDestination);
        controller = new InteractiveGuiController(model, (IHybridView) view, ticksPerSecond);
        controller.run();
        break;
      default:
        if (outputDestination instanceof PrintWriter) {
          ((PrintWriter) outputDestination).close();
        }
    }

  }
}

