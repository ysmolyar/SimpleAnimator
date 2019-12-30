
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.animations.Animation;
import cs3500.animator.animations.ColorChange;
import cs3500.animator.animations.Move;
import cs3500.animator.animations.Scale;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.ITextableShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.shapes.TextableShapeImpl;

import static org.junit.Assert.assertEquals;

/**
 * A class to test an Animator Model implementation.
 */
public class AnimatorModelImplTest {

  IShape testRectangle;
  IShape testOval;
  Animation testMoveRect1;
  Animation testMoveOval1;
  Animation testChangeColorOval1;
  Animation testScaleRectangle1;
  Animation testMoveRect2;
  AnimatorModel testModel;
  AnimatorModel testModelBuilder;
  ArrayList<ITextableShape> shapes = new ArrayList<>();
  ArrayList<Animation> animations = new ArrayList<>();

  /**
   * Initializes the data.
   */
  @Before
  public void initData() {
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
    testModel = new AnimatorModelImpl(shapes, animations);
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
  public void testGetFrames() {
    assertEquals(100, testModel.getNumberOfFrames());
  }

  @Test
  public void getAnimationDescription() {
    assertEquals("Shapes:\n" +
                    "Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0)\n" +
                    "Appears at t=1.0s\n" +
                    "Disappears at t=100.0s\n" +
                    "\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)\n" +
                    "Appears at t=6.0s\n" +
                    "Disappears at t=100.0s\n" +
                    "\n" +
                    "Shape R moves from (200.0, 200.0) to (300.0, 300.0) from t = 10.0s to t =" +
                    " 50.0s\n" +
                    "Shape C moves from (500.0, 100.0) to (500.0, 400.0) from t = 20.0s to t = " +
                    "70.0s\n" +
                    "Shape C changes color from java.awt.Color[r=0,g=0,b=255] to java.awt." +
                    "Color[r=0,g=255,b=0] from t = 50.0s to t = 80.0s\n" +
                    "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: " +
                    "100.0 from t = 51.0s to t = 70.0s\n" +
                    "Shape R moves from (300.0, 300.0) to (200.0, 200.0) from t = 70.0s to t = " +
                    "100.0s\n",
            testModel.getAnimationDescription(1));
  }

  @Test
  public void getCurrentSceneAt() {
    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0)]",
            testModel.getCurrentSceneAt(2).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0), " +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)]",
            testModel.getCurrentSceneAt(6).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (212.5, 212.5), Width: 50.0, Height: 100.0, Color: (255,0,0), " +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)]",
            testModel.getCurrentSceneAt(15).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (300.0, 300.0), Width: 30.263157, Height: 100.0, Color: (255,0,0)," +
                    " Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 376.0), X radius: 60.0, Y radius: 30.0, Color: (0,136,119)]",
            testModel.getCurrentSceneAt(66).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (270.0, 270.0), Width: 25.0, Height: 100.0, Color: (255,0,0), " +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 400.0), X radius: 60.0, Y radius: 30.0, Color: (0,246,8)]",
            testModel.getCurrentSceneAt(79).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (240.0, 240.0), Width: 25.0, Height: 100.0, " +
                    "Color: (255,0,0), Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 400.0), X radius: 60.0, Y radius: 30.0, Color: (0,255,0)]",
            testModel.getCurrentSceneAt(88).toString());
  }

  @Test
  public void printListOfAnimations() {
    assertEquals("Shape R moves from (200.0, 200.0) to (300.0, 300.0) from t " +
                    "= 10.0s to t = 50.0s\n" +
                    "Shape C moves from (500.0, 100.0) to (500.0, 400.0) from t = 20.0s" +
                    " to t = 70.0s\n" +
                    "Shape C changes color from java.awt.Color[r=0,g=0,b=255] to java.awt" +
                    ".Color[r=0,g=255,b=0] from t = 50.0s to t = 80.0s\n" +
                    "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height:" +
                    " 100.0 from t = 51.0s to t = 70.0s\n" +
                    "Shape R moves from (300.0, 300.0) to (200.0, 200.0) from t = 70.0s to t" +
                    " = 100.0s\n"
            , testModel.printListOfAnimations(1));
  }

  @Test
  public void printListOfShapes() {
    assertEquals("Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0)\n" +
            "Appears at t=1.0s\n" +
            "Disappears at t=100.0s\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)\n" +
            "Appears at t=6.0s\n" +
            "Disappears at t=100.0s\n", testModel.printListOfShapes(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getInvalidNegativeTime() {
    testModel.getCurrentSceneAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getInvalidPositiveTime() {
    testModel.getCurrentSceneAt(101);
  }

  @Test
  public void testGetFramesBuilder() {
    assertEquals(100,
            testModelBuilder.getNumberOfFrames());
  }

  @Test
  public void getAnimationDescriptionBuilder() {
    assertEquals("Shapes:\n" +
                    "Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0)\n" +
                    "Appears at t=1.0s\n" +
                    "Disappears at t=100.0s\n" +
                    "\n" +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)\n" +
                    "Appears at t=6.0s\n" +
                    "Disappears at t=100.0s\n" +
                    "\n" +
                    "Shape R moves from (200.0, 200.0) to (300.0, 300.0) from t = 10.0s to t " +
                    "= 50.0s\n" +
                    "Shape C moves from (500.0, 100.0) to (500.0, 400.0) from t = 20.0s to t =" +
                    " 70.0s\n" +
                    "Shape C changes color from java.awt.Color[r=0,g=0,b=255] to java.awt.Color" +
                    "[r=0,g=255,b=0] from t = 50.0s to t = 80.0s\n" +
                    "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0" +
                    " from t = 51.0s to t = 70.0s\n" +
                    "Shape R moves from (300.0, 300.0) to (200.0, 200.0) from t = 70.0s to t " +
                    "= 100.0s\n"
            , testModelBuilder.getAnimationDescription(1));
  }


  @Test
  public void getCurrentSceneAtBuilder() {
    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0)]",
            testModelBuilder.getCurrentSceneAt(2).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0), " +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)]",
            testModelBuilder.getCurrentSceneAt(6).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (212.5, 212.5), Width: 50.0, Height: 100.0, Color: (255,0,0), " +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)]",
            testModelBuilder.getCurrentSceneAt(15).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (300.0, 300.0), Width: 30.263157, Height: 100.0, Color: (255,0,0)," +
                    " Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 376.0), X radius: 60.0, Y radius: 30.0, Color: (0,136,119)]",
            testModelBuilder.getCurrentSceneAt(66).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (270.0, 270.0), Width: 25.0, Height: 100.0, Color: (255,0,0)," +
                    " Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 400.0), X radius: 60.0, Y radius: 30.0, Color: (0,246,8)]",
            testModelBuilder.getCurrentSceneAt(79).toString());

    assertEquals("[Name: R\n" +
                    "Type: rectangle\n" +
                    "Corner: (240.0, 240.0), Width: 25.0, Height: 100.0, Color: (255,0,0), " +
                    "Name: C\n" +
                    "Type: oval\n" +
                    "Center: (500.0, 400.0), X radius: 60.0, Y radius: 30.0, Color: (0,255,0)]",
            testModelBuilder.getCurrentSceneAt(88).toString());
  }

  @Test
  public void printListOfAnimationsBuilder() {
    assertEquals(
            "Shape R moves from (200.0, 200.0) to (300.0, 300.0) from t = 10.0s to" +
                    " t = 50.0s\n" +
                    "Shape C moves from (500.0, 100.0) to (500.0, 400.0) from t = 20.0s to " +
                    "t = 70.0s\n" +
                    "Shape C changes color from java.awt.Color[r=0,g=0,b=255] to java.awt.Color" +
                    "[r=0,g=255,b=0] from t = 50.0s to t = 80.0s\n" +
                    "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0" +
                    " from t = 51.0s to t = 70.0s\n" +
                    "Shape R moves from (300.0, 300.0) to (200.0, 200.0) from t = 70.0s to " +
                    "t = 100.0s\n"
            , testModelBuilder.printListOfAnimations(1));
  }

  @Test
  public void printListOfShapesBuilder() {
    assertEquals("Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Corner: (200.0, 200.0), Width: 50.0, Height: 100.0, Color: (255,0,0)\n" +
            "Appears at t=1.0s\n" +
            "Disappears at t=100.0s\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0, 100.0), X radius: 60.0, Y radius: 30.0, Color: (0,0,255)\n" +
            "Appears at t=6.0s\n" +
            "Disappears at t=100.0s\n", testModelBuilder.printListOfShapes(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getInvalidNegativeTimeBuilder() {
    testModelBuilder.getCurrentSceneAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getInvalidPositiveTimeBuilder() {
    testModelBuilder.getCurrentSceneAt(101);
  }
}