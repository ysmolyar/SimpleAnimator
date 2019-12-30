import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import cs3500.animator.animations.Animation;
import cs3500.animator.animations.ColorChange;
import cs3500.animator.animations.Move;
import cs3500.animator.animations.Scale;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.ITextableShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.shapes.TextableShapeImpl;
import cs3500.animator.util.AnimationFileReader;
import cs3500.animator.util.TweenModelBuilder;
import cs3500.animator.view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests textView, parameterizing it over alternate input and output sources.
 */
public class TextViewTest {

  TextView textView;
  IShape testRectangle;
  IShape testOval;
  Animation testMoveRect1;
  Animation testMoveOval1;
  Animation testChangeColorOval1;
  Animation testScaleRectangle1;
  Animation testMoveRect2;
  AnimatorModel testModelBuilder;
  AnimationFileReader fileReader;
  ArrayList<ITextableShape> shapes = new ArrayList<>();
  ArrayList<Animation> animations = new ArrayList<>();

  /**
   * Initializes the data.
   */
  @Before
  public void initData() {
    fileReader = new AnimationFileReader();
    testRectangle = new Rectangle("R", 200, 200, Color.RED,
            50, 100);
    testOval = new Oval("C", 500, 100, Color.BLUE,
            60, 30);
    shapes.add(new TextableShapeImpl(testRectangle, 1, 100));
    shapes.add(new TextableShapeImpl(testOval, 6, 100));
    testMoveRect1 = new Move("R", 10, 50, 200, 200, 300,
            300);
    testMoveOval1 = new Move("C", 20, 70, 500, 100, 500,
            400);
    testChangeColorOval1 = new ColorChange("C", 50, 80, Color.BLUE, Color.GREEN);
    testScaleRectangle1 = new Scale("R", 51, 70, 50, 100,
            25, 100);
    testMoveRect2 = new Move("R", 70, 100, 300, 300, 200,
            200);
    animations.add(testMoveRect1);
    animations.add(testMoveOval1);
    animations.add(testChangeColorOval1);
    animations.add(testScaleRectangle1);
    animations.add(testMoveRect2);
    testModelBuilder = new AnimatorModelImpl.Builder()
            .addRectangle("R", 200, 200, 50, 100,
                    1, 0, 0, 1, 100)
            .addOval("C", 500, 100, 60, 30,
                    0, 0, 1, 6, 100)
            .addMove("R", 200, 200, 300, 300,
                    10, 50)
            .addMove("C", 500, 100, 500, 400,
                    20, 70)
            .addColorChange("C", 0, 0, 1, 0, 1, 0,
                    50, 80)
            .addScaleToChange("R", 50, 100, 25, 100,
                    51, 70)
            .addMove("R", 300, 300, 200, 200,
                    70, 100)
            .build();
  }

  @Test
  public void testBasicShapesText() {
    TweenModelBuilder<AnimatorModel> builder = new AnimatorModelImpl.Builder();
    AnimatorModel model;
    Appendable output = new StringBuilder("");
    textView = new TextView(output);
    textView.updateContents(testModelBuilder.getAnimationDescription(10));
    textView.display();
    assertEquals(output.toString(),
            "Shapes:\n" +
                    "Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0)\n" +
                    "Appears at t=0.1s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)\n" +
                    "Appears at t=0.6s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Shape R moves from (200.0, 200.0) to (300.0, 300.0) from t = 1.0s to t =" +
                    " 5.0s\n" +
                    "Shape C moves from (500.0, 100.0) to (500.0, 400.0) from t = 2.0s to t = " +
                    "7.0s\n" +
                    "Shape C changes color from java.awt.Color[r=0,g=0,b=255] to " +
                    "java.awt.Color[r=0,g=255,b=0] from t = 5.0s to t = 8.0s\n" +
                    "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height:" +
                    " 100.0 from t = 5.0s to t = 7.0s\n" +
                    "Shape R moves from (300.0, 300.0) to (200.0, 200.0) from t = 7.0s to t = " +
                    "10.0s\n");

  }

  @Test
  public void testSmallDemoText() {

    AnimationFileReader fileReader = new AnimationFileReader();
    TweenModelBuilder<AnimatorModel> builder = new AnimatorModelImpl.Builder();
    AnimatorModel model;
    TextView view;
    Appendable output = new StringBuilder();

    try {
      model = fileReader.readFile("resources/smalldemo.txt", builder);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read file!");
    }
    view = new TextView(output);
    view.updateContents(model.getAnimationDescription(10));
    view.display();
    assertEquals(output.toString(),
            "Shapes:\n" +
                    "Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0)\n" +
                    "Appears at t=0.1s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)\n" +
                    "Appears at t=0.6s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Shape R moves from (200.0, 200.0) to (300.0, 300.0) from t = 1.0s to t " +
                    "= 5.0s\n" +
                    "Shape C moves from (500.0, 100.0) to (500.0, 400.0) from t = 2.0s to t" +
                    " = 7.0s\n" +
                    "Shape C changes color from java.awt.Color[r=0,g=0,b=255] to " +
                    "java.awt.Color[r=0,g=255,b=0] from t = 5.0s to t = 8.0s\n" +
                    "Shape R moves from (300.0, 300.0) to (200.0, 200.0) from t = 7.0s to " +
                    "t = 10.0s\n" +
                    "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height:" +
                    " 100.0 from t = 5.0s to t = 7.0s\n");

  }


  @Test
  public void testBuildings() {

    AnimationFileReader fileReader = new AnimationFileReader();
    TweenModelBuilder<AnimatorModel> builder = new AnimatorModelImpl.Builder();
    AnimatorModel model;
    TextView view;
    Appendable output = new StringBuilder();

    try {
      model = fileReader.readFile("resources/buildings.txt", builder);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read file!");
    }
    view = new TextView( output);
    view.updateContents(model.getAnimationDescription(20));
    view.display();
    assertEquals(output.toString(),
            "Shapes:\n" +
                    "Name: background\n" +
                    "Type: rectangle\n" +
                    "Corner: (0.0, 0.0), Width: 800.0, Height: 800.0, Color: (33,94,248)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: B0\n" +
                    "Type: rectangle\n" +
                    "Corner: (80.0, 424.0), Width: 100.0, Height: 326.0, Color: (0,0,0)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: B1\n" +
                    "Type: rectangle\n" +
                    "Corner: (260.0, 365.0), Width: 100.0, Height: 385.0, Color: (0,0,0)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: B2\n" +
                    "Type: rectangle\n" +
                    "Corner: (440.0, 375.0), Width: 100.0, Height: 375.0, Color: (0,0,0)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: B3\n" +
                    "Type: rectangle\n" +
                    "Corner: (620.0, 445.0), Width: 100.0, Height: 305.0, Color: (0,0,0)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window000\n" +
                    "Type: rectangle\n" +
                    "Corner: (100.0, 850.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window001\n" +
                    "Type: rectangle\n" +
                    "Corner: (140.0, 850.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window010\n" +
                    "Type: rectangle\n" +
                    "Corner: (100.0, 890.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window011\n" +
                    "Type: rectangle\n" +
                    "Corner: (140.0, 890.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window020\n" +
                    "Type: rectangle\n" +
                    "Corner: (100.0, 930.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window021\n" +
                    "Type: rectangle\n" +
                    "Corner: (140.0, 930.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window030\n" +
                    "Type: rectangle\n" +
                    "Corner: (100.0, 970.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window031\n" +
                    "Type: rectangle\n" +
                    "Corner: (140.0, 970.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window040\n" +
                    "Type: rectangle\n" +
                    "Corner: (100.0, 1010.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window041\n" +
                    "Type: rectangle\n" +
                    "Corner: (140.0, 1010.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window050\n" +
                    "Type: rectangle\n" +
                    "Corner: (100.0, 1050.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window051\n" +
                    "Type: rectangle\n" +
                    "Corner: (140.0, 1050.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window060\n" +
                    "Type: rectangle\n" +
                    "Corner: (100.0, 1090.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window061\n" +
                    "Type: rectangle\n" +
                    "Corner: (140.0, 1090.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window100\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -750.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window101\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -750.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window110\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -710.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window111\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -710.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window120\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -670.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window121\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -670.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window130\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -630.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window131\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -630.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window140\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -590.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window141\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -590.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window150\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -550.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window151\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -550.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window160\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -510.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window161\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -510.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window170\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -470.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window171\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -470.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window180\n" +
                    "Type: rectangle\n" +
                    "Corner: (280.0, -430.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window181\n" +
                    "Type: rectangle\n" +
                    "Corner: (320.0, -430.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window200\n" +
                    "Type: rectangle\n" +
                    "Corner: (460.0, 850.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window201\n" +
                    "Type: rectangle\n" +
                    "Corner: (500.0, 850.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window210\n" +
                    "Type: rectangle\n" +
                    "Corner: (460.0, 890.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window211\n" +
                    "Type: rectangle\n" +
                    "Corner: (500.0, 890.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window220\n" +
                    "Type: rectangle\n" +
                    "Corner: (460.0, 930.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window221\n" +
                    "Type: rectangle\n" +
                    "Corner: (500.0, 930.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window230\n" +
                    "Type: rectangle\n" +
                    "Corner: (460.0, 970.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window231\n" +
                    "Type: rectangle\n" +
                    "Corner: (500.0, 970.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window240\n" +
                    "Type: rectangle\n" +
                    "Corner: (460.0, 1010.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window241\n" +
                    "Type: rectangle\n" +
                    "Corner: (500.0, 1010.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window250\n" +
                    "Type: rectangle\n" +
                    "Corner: (460.0, 1050.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window251\n" +
                    "Type: rectangle\n" +
                    "Corner: (500.0, 1050.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window260\n" +
                    "Type: rectangle\n" +
                    "Corner: (460.0, 1090.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window261\n" +
                    "Type: rectangle\n" +
                    "Corner: (500.0, 1090.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window270\n" +
                    "Type: rectangle\n" +
                    "Corner: (460.0, 1130.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window271\n" +
                    "Type: rectangle\n" +
                    "Corner: (500.0, 1130.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window300\n" +
                    "Type: rectangle\n" +
                    "Corner: (640.0, -750.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window301\n" +
                    "Type: rectangle\n" +
                    "Corner: (680.0, -750.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window310\n" +
                    "Type: rectangle\n" +
                    "Corner: (640.0, -710.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window311\n" +
                    "Type: rectangle\n" +
                    "Corner: (680.0, -710.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window320\n" +
                    "Type: rectangle\n" +
                    "Corner: (640.0, -670.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window321\n" +
                    "Type: rectangle\n" +
                    "Corner: (680.0, -670.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window330\n" +
                    "Type: rectangle\n" +
                    "Corner: (640.0, -630.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window331\n" +
                    "Type: rectangle\n" +
                    "Corner: (680.0, -630.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window340\n" +
                    "Type: rectangle\n" +
                    "Corner: (640.0, -590.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window341\n" +
                    "Type: rectangle\n" +
                    "Corner: (680.0, -590.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window350\n" +
                    "Type: rectangle\n" +
                    "Corner: (640.0, -550.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window351\n" +
                    "Type: rectangle\n" +
                    "Corner: (680.0, -550.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window360\n" +
                    "Type: rectangle\n" +
                    "Corner: (640.0, -510.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: window361\n" +
                    "Type: rectangle\n" +
                    "Corner: (680.0, -510.0), Width: 20.0, Height: 20.0, Color: (255,255,255)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: moon\n" +
                    "Type: oval\n" +
                    "Center: (250.0, 250.0), X radius: 50.0, Y radius: 50.0, Color: (229,229,255)" +
                    "\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: eclipse\n" +
                    "Type: oval\n" +
                    "Center: (450.0, 50.0), X radius: 50.0, Y radius: 50.0, Color: (33,94,248)\n" +
                    "Appears at t=0.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star0\n" +
                    "Type: oval\n" +
                    "Center: (226.0, 69.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.4s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star1\n" +
                    "Type: oval\n" +
                    "Center: (588.0, 214.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=6.0s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star2\n" +
                    "Type: oval\n" +
                    "Center: (492.0, 80.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.5s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star3\n" +
                    "Type: oval\n" +
                    "Center: (377.0, 289.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.65s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star4\n" +
                    "Type: oval\n" +
                    "Center: (711.0, 284.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.15s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star5\n" +
                    "Type: oval\n" +
                    "Center: (511.0, 263.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.85s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star6\n" +
                    "Type: oval\n" +
                    "Center: (532.0, 73.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.6s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star7\n" +
                    "Type: oval\n" +
                    "Center: (335.0, 68.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.85s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star8\n" +
                    "Type: oval\n" +
                    "Center: (314.0, 150.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.3s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star9\n" +
                    "Type: oval\n" +
                    "Center: (173.0, 284.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.4s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star10\n" +
                    "Type: oval\n" +
                    "Center: (722.0, 105.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.5s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star11\n" +
                    "Type: oval\n" +
                    "Center: (527.0, 267.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=6.45s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star12\n" +
                    "Type: oval\n" +
                    "Center: (771.0, 197.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.3s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star13\n" +
                    "Type: oval\n" +
                    "Center: (769.0, 182.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.65s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star14\n" +
                    "Type: oval\n" +
                    "Center: (513.0, 81.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.9s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star15\n" +
                    "Type: oval\n" +
                    "Center: (624.0, 152.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.7s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star16\n" +
                    "Type: oval\n" +
                    "Center: (494.0, 255.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.3s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star17\n" +
                    "Type: oval\n" +
                    "Center: (408.0, 66.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.45s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star18\n" +
                    "Type: oval\n" +
                    "Center: (553.0, 270.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.65s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star19\n" +
                    "Type: oval\n" +
                    "Center: (111.0, 200.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.7s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star20\n" +
                    "Type: oval\n" +
                    "Center: (740.0, 81.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.15s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star21\n" +
                    "Type: oval\n" +
                    "Center: (798.0, 140.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=6.3s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star22\n" +
                    "Type: oval\n" +
                    "Center: (187.0, 128.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.45s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star23\n" +
                    "Type: oval\n" +
                    "Center: (137.0, 233.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.8s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star24\n" +
                    "Type: oval\n" +
                    "Center: (247.0, 156.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=6.0s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star25\n" +
                    "Type: oval\n" +
                    "Center: (262.0, 122.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.6s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star26\n" +
                    "Type: oval\n" +
                    "Center: (325.0, 272.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.3s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star27\n" +
                    "Type: oval\n" +
                    "Center: (415.0, 185.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.85s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star28\n" +
                    "Type: oval\n" +
                    "Center: (677.0, 140.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.95s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star29\n" +
                    "Type: oval\n" +
                    "Center: (49.0, 249.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.8s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star30\n" +
                    "Type: oval\n" +
                    "Center: (391.0, 318.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=6.15s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star31\n" +
                    "Type: oval\n" +
                    "Center: (188.0, 239.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=6.05s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star32\n" +
                    "Type: oval\n" +
                    "Center: (553.0, 235.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.45s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star33\n" +
                    "Type: oval\n" +
                    "Center: (659.0, 104.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.7s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star34\n" +
                    "Type: oval\n" +
                    "Center: (286.0, 114.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.2s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star35\n" +
                    "Type: oval\n" +
                    "Center: (652.0, 329.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.6s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star36\n" +
                    "Type: oval\n" +
                    "Center: (694.0, 270.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=5.75s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star37\n" +
                    "Type: oval\n" +
                    "Center: (116.0, 279.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.95s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star38\n" +
                    "Type: oval\n" +
                    "Center: (607.0, 305.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=4.95s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Name: star39\n" +
                    "Type: oval\n" +
                    "Center: (465.0, 165.0), X radius: 3.0, Y radius: 3.0, Color: (255,255,255)\n" +
                    "Appears at t=6.35s\n" +
                    "Disappears at t=10.0s\n" +
                    "\n" +
                    "Shape window000 moves from (100.0, 850.0) to (100.0, 710.0) from t = 1.0s to" +
                    " t = 3.0s\n" +
                    "Shape window001 moves from (140.0, 850.0) to (140.0, 710.0) from t = 1.0s to" +
                    " t = 3.0s\n" +
                    "Shape window010 moves from (100.0, 890.0) to (100.0, 670.0) from t = 1.0s to" +
                    " t = 3.0s\n" +
                    "Shape window011 moves from (140.0, 890.0) to (140.0, 670.0) from t = 1.0s to" +
                    " t = 3.0s\n" +
                    "Shape window020 moves from (100.0, 930.0) to (100.0, 630.0) from t = 1.0s to" +
                    " t = 3.0s\n" +
                    "Shape window021 moves from (140.0, 930.0) to (140.0, 630.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window030 moves from (100.0, 970.0) to (100.0, 590.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window031 moves from (140.0, 970.0) to (140.0, 590.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window040 moves from (100.0, 1010.0) to (100.0, 550.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window041 moves from (140.0, 1010.0) to (140.0, 550.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window050 moves from (100.0, 1050.0) to (100.0, 510.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window051 moves from (140.0, 1050.0) to (140.0, 510.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window060 moves from (100.0, 1090.0) to (100.0, 470.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window061 moves from (140.0, 1090.0) to (140.0, 470.0) from t = 1.0s to"
                    + " t = 3.0s\n" + "Shape window100 moves from (280.0, -750.0) to " +
                    "(280.0, 710.0) from t = 1.0s to" + " t = 3.0s\n" +
                    "Shape window101 moves from (320.0, -750.0) to (320.0, 710.0) from t = 1.0s to"
                    + " t = 3.0s\n" +
                    "Shape window110 moves from (280.0, -710.0) to (280.0, 670.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window111 moves from (320.0, -710.0) to (320.0, 670.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window120 moves from (280.0, -670.0) to (280.0, 630.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window121 moves from (320.0, -670.0) to (320.0, 630.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window130 moves from (280.0, -630.0) to (280.0, 590.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window131 moves from (320.0, -630.0) to (320.0, 590.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window140 moves from (280.0, -590.0) to (280.0, 550.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window141 moves from (320.0, -590.0) to (320.0, 550.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window150 moves from (280.0, -550.0) to (280.0, 510.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window151 moves from (320.0, -550.0) to (320.0, 510.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window160 moves from (280.0, -510.0) to (280.0, 470.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window161 moves from (320.0, -510.0) to (320.0, 470.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window170 moves from (280.0, -470.0) to (280.0, 430.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window171 moves from (320.0, -470.0) to (320.0, 430.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window180 moves from (280.0, -430.0) to (280.0, 390.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window181 moves from (320.0, -430.0) to (320.0, 390.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window200 moves from (460.0, 850.0) to (460.0, 710.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window201 moves from (500.0, 850.0) to (500.0, 710.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window210 moves from (460.0, 890.0) to (460.0, 670.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window211 moves from (500.0, 890.0) to (500.0, 670.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window220 moves from (460.0, 930.0) to (460.0, 630.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window221 moves from (500.0, 930.0) to (500.0, 630.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window230 moves from (460.0, 970.0) to (460.0, 590.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window231 moves from (500.0, 970.0) to (500.0, 590.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window240 moves from (460.0, 1010.0) to (460.0, 550.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window241 moves from (500.0, 1010.0) to (500.0, 550.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window250 moves from (460.0, 1050.0) to (460.0, 510.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window251 moves from (500.0, 1050.0) to (500.0, 510.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window260 moves from (460.0, 1090.0) to (460.0, 470.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window261 moves from (500.0, 1090.0) to (500.0, 470.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window270 moves from (460.0, 1130.0) to (460.0, 430.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window271 moves from (500.0, 1130.0) to (500.0, 430.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window300 moves from (640.0, -750.0) to (640.0, 710.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window301 moves from (680.0, -750.0) to (680.0, 710.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window310 moves from (640.0, -710.0) to (640.0, 670.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window311 moves from (680.0, -710.0) to (680.0, 670.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window320 moves from (640.0, -670.0) to (640.0, 630.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window321 moves from (680.0, -670.0) to (680.0, 630.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window330 moves from (640.0, -630.0) to (640.0, 590.0) from t = 1.0s " +
                    "to t = 3.0s\n" +
                    "Shape window331 moves from (680.0, -630.0) to (680.0, 590.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window340 moves from (640.0, -590.0) to (640.0, 550.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window341 moves from (680.0, -590.0) to (680.0, 550.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window350 moves from (640.0, -550.0) to (640.0, 510.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window351 moves from (680.0, -550.0) to (680.0, 510.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window360 moves from (640.0, -510.0) to (640.0, 470.0) from t = 1.0s" +
                    " to t = 3.0s\n" +
                    "Shape window361 moves from (680.0, -510.0) to (680.0, 470.0) from t = 1.0s" +
                    " to" +
                    " t = 3.0s\n" +
                    "Shape eclipse moves from (450.0, 50.0) to (280.0, 230.0) from t = 2.0s " +
                    "to t = 4.0s\n" +
                    "Shape background changes color from java.awt.Color[r=33,g=94,b=248] to " +
                    "java.awt.Color[r=16,g=45,b=248] from t = 2.0s to t = 4.0s\n" +
                    "Shape eclipse changes color from java.awt.Color[r=33,g=94,b=248] to" +
                    " java.awt.Color[r=16,g=45,b=248] from t = 2.0s to t = 4.0s\n" +
                    "Shape window021 changes color from java.awt.Color[r=255,g=255,b=255] to j" +
                    "ava.awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 4.0s\n" +
                    "Shape window041 changes color from java.awt.Color[r=255,g=255,b=255] to ja" +
                    "va.awt.Color[r=255,g=255,b=0] from t = 7.0s to t = 7.0s\n" +
                    "Shape window060 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 7.0s to t = 7.0s\n" +
                    "Shape window061 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 4.0s\n" +
                    "Shape window101 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 5.0s to t = 5.0s\n" +
                    "Shape window110 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 5.0s\n" +
                    "Shape window131 changes color from java.awt.Color[r=255,g=255,b=255] to java" +
                    ".awt.Color[r=255,g=255,b=0] from t = 7.0s to t = 7.0s\n" +
                    "Shape window151 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 5.0s\n" +
                    "Shape window200 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 6.0s to t = 6.0s\n" +
                    "Shape window210 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 4.0s\n" +
                    "Shape window241 changes color from java.awt.Color[r=255,g=255,b=255] to ja" +
                    "va.awt.Color[r=255,g=255,b=0] from t = 5.0s to t = 5.0s\n" +
                    "Shape window250 changes color from java.awt.Color[r=255,g=255,b=255] to ja" +
                    "va.awt.Color[r=255,g=255,b=0] from t = 6.0s to t = 6.0s\n" +
                    "Shape window261 changes color from java.awt.Color[r=255,g=255,b=255] to java" +
                    ".awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 5.0s\n" +
                    "Shape window301 changes color from java.awt.Color[r=255,g=255,b=255] to java" +
                    ".awt.Color[r=255,g=255,b=0] from t = 6.0s to t = 7.0s\n" +
                    "Shape window310 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a." +
                    "awt.Color[r=255,g=255,b=0] from t = 5.0s to t = 5.0s\n" +
                    "Shape window330 changes color from java.awt.Color[r=255,g=255,b=255] to java" +
                    ".awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 4.0s\n" +
                    "Shape window331 changes color from java.awt.Color[r=255,g=255,b=255] to ja" +
                    "va.awt.Color[r=255,g=255,b=0] from t = 5.0s to t = 6.0s\n" +
                    "Shape window351 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a." +
                    "awt.Color[r=255,g=255,b=0] from t = 5.0s to t = 5.0s\n" +
                    "Shape window360 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 5.0s\n" +
                    "Shape window361 changes color from java.awt.Color[r=255,g=255,b=255] to jav" +
                    "a.awt.Color[r=255,g=255,b=0] from t = 4.0s to t = 5.0s\n");
  }

}
