
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;
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
import cs3500.animator.util.AnimationFileReader;
import cs3500.animator.util.TweenModelBuilder;
import cs3500.animator.view.IView;
import cs3500.animator.view.SvgView;

import static org.junit.Assert.assertEquals;

public class SvgViewTest {

  SvgView svgView;
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
  public void testBasicShapesSVG() {
    TweenModelBuilder<AnimatorModel> builder = new AnimatorModelImpl.Builder();
    AnimatorModel model;
    Appendable output = new StringBuilder("");
    svgView = new SvgView(10, output);
    svgView.updateContents(testModelBuilder.getListOfAnimatedShapes());
    svgView.display();
    assertEquals(output.toString(),
            "<svg width=\"800\" height=\"800\" version=\"1.1\" "
                    + "xmlns=\"http://www.w3.org/2000/svg\" >\n"
                    + "\n" + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" "
                    + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n" + "\n"
                    + "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" "
                    + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" "
                    + "fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"1000ms\" dur=\"4000ms\""
                    + " attributeName=\"x\" from=\"200\" to=\"300\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"1000ms\" dur=\"4000ms\" "
                    + "attributeName=\"y\" from=\"200\" to=\"300\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"5100ms\" dur=\"1900ms\" "
                    + "attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"5100ms\" dur=\"1900ms\" "
                    + "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"7000ms\" dur=\"3000ms\" "
                    + "attributeName=\"x\" from=\"300\" to=\"200\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"7000ms\" dur=\"3000ms\" "
                    + "attributeName=\"y\" from=\"300\" to=\"200\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"10000ms\" dur=\"9900ms\" "
                    + "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n"
                    + "</rect>\n" + "\n" + "<ellipse id=\"C\" cx=\"500\" cy=\"100\" rx=\"60\" "
                    + "ry=\"30\" fill=\"rgb(0,0,255)\" visibility=\"hidden\" >\n" + "\n"
                    + "    <animate attributeType=\"xml\" begin =\"600ms\" dur=\"1ms\" "
                    + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\""
                    + " />\n" + "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\""
                    + "5000ms\" attributeName=\"cy\" from=\"100\" to=\"400\" fill=\"freeze\" "
                    + "/>\n" + "    <animate attributeType=\"xml\" begin =\"5000ms\" dur=\"3000ms\""
                    + " attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,255,0)\""
                    + " fill=\"freeze\" />\n" + "    <animate attributeType=\"xml\" begin =\""
                    + "10000ms\" dur=\"9400ms\" attributeName=\"visibility\" from=\"visible\" "
                    + "to=\"hidden\" />\n" + "</ellipse>\n" + "\n" + "\n" + " </svg>");

  }

  @Test
  public void testSmallDemo() {

    AnimationFileReader fileReader = new AnimationFileReader();
    TweenModelBuilder<AnimatorModel> builder = new AnimatorModelImpl.Builder();
    AnimatorModel model;
    IView view;
    Appendable output = new StringBuilder();

    try {
      model = fileReader.readFile("resources/smalldemo.txt", builder);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read file!");
    }
    view = new SvgView(10, output);
    ((SvgView) view).updateContents(model.getListOfAnimatedShapes());
    view.display();
    assertEquals(output.toString(),
            "<svg width=\"800\" height=\"800\" version=\"1.1\" "
                    + "xmlns=\"http://www.w3.org/2000/svg\" >\n"
                    + "\n" + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" "
                    + "fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
                    + "\n" + "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\""
                    + " attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\""
                    + " />\n" + "    <animate attributeType=\"xml\" begin =\"1000ms\" "
                    + "dur=\"4000ms\" attributeName=\"x\" from=\"200\" to=\"300\" fill=\"freeze\""
                    + " />\n" + "    <animate attributeType=\"xml\" begin =\"1000ms\" "
                    + "dur=\"4000ms\" attributeName=\"y\" from=\"200\" to=\"300\" fill=\"freeze\""
                    + " />\n" + "    <animate attributeType=\"xml\" begin =\"7000ms\" dur=\""
                    + "3000ms\" attributeName=\"x\" from=\"300\" to=\"200\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"7000ms\" dur=\"3000ms\" "
                    + "attributeName=\"y\" from=\"300\" to=\"200\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"5100ms\" dur=\"1900ms\" "
                    + "attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"5100ms\" dur=\"1900ms\" "
                    + "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"10000ms\" dur=\"9900ms\" "
                    + "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n"
                    + "</rect>\n" + "\n" + "<ellipse id=\"C\" cx=\"500\" cy=\"100\" rx=\"60\" "
                    + "ry=\"30\" fill=\"rgb(0,0,255)\" visibility=\"hidden\" >\n"
                    + "\n" + "    <animate attributeType=\"xml\" begin =\"600ms\" dur=\"1ms\" "
                    + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\""
                    + "freeze\" />\n" + "    <animate attributeType=\"xml\" begin =\"2000ms\" "
                    + "dur=\"5000ms\" attributeName=\"cy\" from=\"100\" to=\"400\" fill=\"freeze\""
                    + " />\n" + "    <animate attributeType=\"xml\" begin =\"5000ms\" "
                    + "dur=\"3000ms\" attributeName=\"fill\" from=\"rgb(0,0,255)\" "
                    + "to=\"rgb(0,255,0)\" fill=\"freeze\" />\n" + "    <animate attributeType=\""
                    + "xml\" begin =\"10000ms\" dur=\"9400ms\" attributeName=\"visibility\" "
                    + "from=\"visible\" to=\"hidden\" />\n" + "</ellipse>\n" + "\n" + "\n"
                    + " </svg>");

  }


  @Test
  public void testBuildings() {

    AnimationFileReader fileReader = new AnimationFileReader();
    TweenModelBuilder<AnimatorModel> builder = new AnimatorModelImpl.Builder();
    AnimatorModel model;
    IView view;
    Appendable output = new StringBuilder();

    try {
      model = fileReader.readFile("resources/buildings.txt", builder);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read file!");
    }
    view = new SvgView(10, output);
    ((SvgView) view).updateContents(model.getListOfAnimatedShapes());
    view.display();
    assertEquals(output.toString(),
            "<svg width=\"800\" height=\"800\" version=\"1.1\" " +
                    "xmlns=\"http://www.w3.org/2000/svg\" >\n" +
                    "\n" +
                    "<rect id=\"background\" x=\"0\" y=\"0\" width=\"800\" height=\"800\"" +
                    " fill=\"rgb(33,94,248)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"5000ms\" dur=\"4000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(33,94,248)\" to=\"rgb(16,45,248)\" fill=\"" +
                    "freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"B0\" x=\"80\" y=\"424\" width=\"100\" height=\"326\" fill=\"" +
                    "rgb(0,0,0)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"B1\" x=\"260\" y=\"365\" width=\"100\" height=\"385\" fill=\"" +
                    "rgb(0,0,0)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"B2\" x=\"440\" y=\"375\" width=\"100\" height=\"375\" fill=\"" +
                    "rgb(0,0,0)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"B3\" x=\"620\" y=\"445\" width=\"100\" height=\"305\" " +
                    "fill=\"rgb(0,0,0)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window000\" x=\"100\" y=\"850\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" + "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" "
                    + "attributeName=\"y\" from=\"850\" to=\"710\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window001\" x=\"140\" y=\"850\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"850\" to=\"710\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window010\" x=\"100\" y=\"890\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"890\" to=\"670\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window011\" x=\"140\" y=\"890\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"890\" to=\"670\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window020\" x=\"100\" y=\"930\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"930\" to=\"630\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window021\" x=\"140\" y=\"930\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"930\" to=\"630\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"8200ms\" dur=\"500ms\"" +
                    " attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\"" +
                    " fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window030\" x=\"100\" y=\"970\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"970\" to=\"590\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window031\" x=\"140\" y=\"970\" width=\"20\" height=\"20\" fill=" +
                    "\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"970\" to=\"590\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window040\" x=\"100\" y=\"1010\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"" +
                    "freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"1010\" to=\"550\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window041\" x=\"140\" y=\"1010\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"1010\" to=\"550\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"14700ms\" dur=\"500ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window050\" x=\"100\" y=\"1050\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"1050\" to=\"510\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window051\" x=\"140\" y=\"1050\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\"" +
                    " attributeName=\"y\" from=\"1050\" to=\"510\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window060\" x=\"100\" y=\"1090\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\"" +
                    " attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\"" +
                    " attributeName=\"y\" from=\"1090\" to=\"470\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"14700ms\" dur=\"500ms\"" +
                    " attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window061\" x=\"140\" y=\"1090\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"1090\" to=\"470\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"9300ms\" dur=\"500ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window100\" x=\"280\" y=\"-750\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"" +
                    "freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-750\" to=\"710\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window101\" x=\"320\" y=\"-750\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"" +
                    "freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-750\" to=\"710\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"10800ms\" dur=\"500ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window110\" x=\"280\" y=\"-710\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-710\" to=\"670\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"9500ms\" dur=\"500ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window111\" x=\"320\" y=\"-710\" width=\"20\" " +
                    "height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" " +
                    "dur=\"4000ms\" attributeName=\"y\" from=\"-710\" to=\"670\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\"" +
                    " to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window120\" x=\"280\" y=\"-670\" width=\"20\" " +
                    "height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" " +
                    "dur=\"4000ms\" attributeName=\"y\" from=\"-670\" to=\"630\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" " +
                    "/>\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window121\" x=\"320\" y=\"-670\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-670\" to=\"630\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window130\" x=\"280\" y=\"-630\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-630\" to=\"590\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window131\" x=\"320\" y=\"-630\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-630\" to=\"590\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"14800ms\" dur=\"500ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window140\" x=\"280\" y=\"-590\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\"" +
                    " attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" "
                    + "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-590\" to=\"550\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window141\" x=\"320\" y=\"-590\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\"" +
                    " attributeName=\"y\" from=\"-590\" to=\"550\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window150\" x=\"280\" y=\"-550\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"" +
                    "freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-550\" to=\"510\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window151\" x=\"320\" y=\"-550\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"" +
                    "freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-550\" to=\"510\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"9900ms\" dur=\"500ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window160\" x=\"280\" y=\"-510\" width=\"20\" height=" +
                    "\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\"" +
                    " attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"" +
                    "freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"400" +
                    "0ms\" attributeName=\"y\" from=\"-510\" to=\"470\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"1990" +
                    "0ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window161\" x=\"320\" y=\"-510\" width=\"20\" heigh" +
                    "t=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" att" +
                    "ributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n"
                    + "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" attrib" +
                    "uteName=\"y\" from=\"-510\" to=\"470\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" at" +
                    "tributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window170\" x=\"280\" y=\"-470\" width=\"20\" height=\"20\" fil" +
                    "l=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" attributeN" +
                    "ame=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" attrib" +
                    "uteName=\"y\" from=\"-470\" to=\"430\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" attr" +
                    "ibuteName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window171\" x=\"320\" y=\"-470\" width=\"20\" height=\"20\" fill" +
                    "=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" attributeN" +
                    "ame=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" attrib" +
                    "uteName=\"y\" from=\"-470\" to=\"430\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window180\" x=\"280\" y=\"-430\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-430\" to=\"390\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window181\" x=\"320\" y=\"-430\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\"" +
                    " attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"" +
                    "freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-430\" to=\"390\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window200\" x=\"460\" y=\"850\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/" +
                    ">\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"850\" to=\"710\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"12700ms\" dur=\"500ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window201\" x=\"500\" y=\"850\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"850\" to=\"710\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window210\" x=\"460\" y=\"890\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\"" +
                    " attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\"" +
                    " attributeName=\"y\" from=\"890\" to=\"670\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"8300ms\" dur=\"500ms\"" +
                    " attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rgb(255,255,0)\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window211\" x=\"500\" y=\"890\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"890\" to=\"670\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window220\" x=\"460\" y=\"930\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fil" +
                    "l=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"930\" to=\"630\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window221\" x=\"500\" y=\"930\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\"" +
                    " attributeName=\"visibility\" from=\"hidden\" to=\"visibl" +
                    "e\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\"" +
                    " attributeName=\"y\" from=\"930\" to=\"630\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window230\" x=\"460\" y=\"970\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\"" +
                    " attributeName=\"visibility\" from=\"hidden\" to=\"visible\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\"" +
                    " attributeName=\"y\" from=\"970\" to=\"590\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window231\" x=\"500\" y=\"970\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\"" +
                    " attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\"" +
                    " attributeName=\"y\" from=\"970\" to=\"590\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window240\" x=\"460\" y=\"1010\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" f" +
                    "ill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\"" +
                    " attributeName=\"y\" from=\"1010\" to=\"550\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window241\" x=\"500\" y=\"1010\" width=\"20\" " +
                    "height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"1010\" to=\"550\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"11200ms\" dur=\"500ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"" +
                    "rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window250\" x=\"460\" y=\"1050\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\"" +
                    " dur=\"4000ms\" attributeName=\"y\" from=\"1050\" to=\"510\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"12800ms\"" +
                    " dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\" " +
                    "to=\"rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\"" +
                    " to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window251\" x=\"500\" y=\"1050\" " +
                    "width=\"20\" height=\"20\" fill=\"rgb(255,255,255)\"" +
                    " visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\"" +
                    " dur=\"4000ms\" attributeName=\"y\" from=\"1050\" to=\"510\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\"" +
                    " to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window260\" x=\"460\" y=\"1090\" " +
                    "width=\"20\" height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"" +
                    "hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\"" +
                    " dur=\"4000ms\" attributeName=\"y\" from=\"1090\" to=\"470\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\"" +
                    " />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window261\" x=\"500\" y=\"1090\" width=\"20\"" +
                    " height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\"" +
                    " dur=\"4000ms\" attributeName=\"y\" from=\"1090\" to=\"470\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"9600ms\" " +
                    "dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\"" +
                    " to=\"rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window270\" x=\"460\" y=\"1130\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\"" +
                    " dur=\"4000ms\" attributeName=\"y\" from=\"1130\" to=\"430\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window271\" x=\"500\" y=\"1130\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=" +
                    "\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"2000ms\"" +
                    " dur=\"4000ms\" attributeName=\"y\" from=" +
                    "\"1130\" to=\"430\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"20000ms\"" +
                    " dur=\"19900ms\" attributeName=\"visibility" +
                    "\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window300\" x=\"640\" y=\"-750\" width=\"20\" height=\"20\" fill=" +
                    "\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"100ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"" +
                    "visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"2000ms\" dur=\"4000ms\" attributeName=\"y\" from=\"-750\" to=\"710\" f" +
                    "ill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"20000ms\" dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" t" +
                    "o=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window301\" x=\"680\" y=\"-750\" width=\"20\" height=\"20\" fi" +
                    "ll=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"100ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"" +
                    "visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"2000ms\" dur=\"4000ms\" attributeName=\"y\" from=\"-750\" to=\"710\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"13500ms\" dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)" +
                    "\" to=\"rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "" +
                    "\"20000ms\" dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window310\" x=\"640\" y=\"-710\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"100ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"2000ms\" dur=\"4000ms\" attributeName=\"y\" from=\"-710\" to=\"670\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"10300ms\" dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\"" +
                    " to=\"rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"20000ms\" dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window311\" x=\"680\" y=\"-710\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"100ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"2000ms\" dur=\"4000ms\" attributeName=\"y\" from=\"-710\" " +
                    "to=\"670\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"20000ms\" dur=\"19900ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window320\" x=\"640\" y=\"-670\" width=\"20\" " +
                    "height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"100ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"2000ms\" dur=\"4000ms\" attributeName=\"y\" from=\"-670\" to=\"630\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =" +
                    "\"20000ms\" dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window321\" x=\"680\" y=\"-670\"" +
                    " width=\"20\" height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\"" +
                    " >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "100ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "2000ms\" dur=\"4000ms\" attributeName=\"y\" from=\"-670\" to=\"630\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "20000ms\" dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window330\" x=\"640\" y=\"-630\" width=\"20\" height=\"20\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "100ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "2000ms\" dur=\"4000ms\" attributeName=\"y\" from=\"-630\" to=\"590\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "8100ms\" dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\" " +
                    "to=\"rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "20000ms\" dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\"" +
                    " to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window331\" x=\"680\" y=\"-630\"" +
                    " width=\"20\" height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"" +
                    "hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "100ms\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "2000ms\" dur=\"4000ms\" attributeName=\"y\" from=\"-630\" to=\"590\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "11900ms\" dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\" " +
                    "to=\"rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"" +
                    "20000ms\" dur=\"" +
                    "19900ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window340\" x=\"640\" y=\"-590\" width=\"20\" " +
                    "height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"" +
                    "1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"" +
                    "4000ms\" attributeName=\"y\" from=\"-590\" to=\"550\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=" +
                    "\"19900ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window341\" x=\"680\" y=\"-590\" width=\"20\" " +
                    "height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=" +
                    "\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"" +
                    "4000ms\" attributeName=\"y\" from=\"-590\" to=\"550\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"" +
                    "19900ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window350\" x=\"640\" y=\"-550\" width=\"20\" height=\"20\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" dur=\"4000ms\" " +
                    "attributeName=\"y\" from=\"-550\" to=\"510\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"19900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window351\" x=\"680\" y=\"-550\" width=\"20\" " +
                    "height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" " +
                    "dur=\"4000ms\" attributeName=\"y\" from=\"-550\" to=\"510\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"10000ms\" " +
                    "dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"" +
                    "rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\"" +
                    " />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window360\" x=\"640\" y=\"-510\" width=\"20\" height=\"20\" fill=" +
                    "\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\" " +
                    "dur=\"4000ms\" attributeName=\"y\" from=\"-510\" to=\"470\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"9900ms\" " +
                    "dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\" to=" +
                    "\"rgb(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" to=" +
                    "\"hidden\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<rect id=\"window361\" x=\"680\" y=\"-510\" " +
                    "width=\"20\" height=\"20\" fill=\"rgb(255,255,255)\" visibility=\"" +
                    "hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"" +
                    "visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"2000ms\"" +
                    " dur=\"4000ms\" attributeName=\"y\" from=\"-510\" to=\"470\" f" +
                    "ill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"9900ms\" " +
                    "dur=\"500ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\" to=\"rg" +
                    "b(255,255,0)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidde" +
                    "n\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<ellipse id=\"moon\" cx=\"250\" cy=\"250\" rx=\"50\" " +
                    "ry=\"50\" fill=\"rgb(229,229,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" to=\"hid" +
                    "den\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"eclipse\" cx=\"450\" cy=\"50\" rx=\"50\" " +
                    "ry=\"50\" fill=\"rgb(33,94,248)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"100ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"5000ms\"" +
                    " dur=\"4000ms\" attributeName=\"cx\" from=\"450\" to=\"280\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"5000ms\"" +
                    " dur=\"4000ms\" attributeName=\"cy\" from=\"50\" to=\"230\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"5000ms\" " +
                    "dur=\"4000ms\" attributeName=\"fill\" from=\"rgb(33,94,248)\" " +
                    "to=\"rgb(16,45,248)\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"19900ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star0\" cx=\"226\" cy=\"69\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"10800ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"9200ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star1\" cx=\"588\" cy=\"214\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"12000ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"8000ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star2\" cx=\"492\" cy=\"80\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11000ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"9000ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star3\" cx=\"377\" cy=\"289\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9300ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"10700ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star4\" cx=\"711\" cy=\"284\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"8300ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"11700ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star5\" cx=\"511\" cy=\"263\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9700ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"10300ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star6\" cx=\"532\" cy=\"73\" rx=\"3\" ry=" +
                    "\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9200ms\" dur=" +
                    "\"1ms\" attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur" +
                    "=\"10800ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star7\" cx=\"335\" cy=\"68\" " +
                    "rx=\"3\" ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11700ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"8300ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star8\" cx=\"314\" cy=\"150\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"8600ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"11400ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star9\" cx=\"173\" cy=\"284\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"8800ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"11200ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star10\" cx=\"722\" cy=\"105\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9000ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"11000ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star11\" cx=\"527\" cy=\"267\" " +
                    "rx=\"3\" ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"12900ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"7" +
                    "100ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star12\" cx=\"771\" cy=\"197\" rx=\"3\" ry" +
                    "=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"8600ms\" d" +
                    "ur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"11400ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star13\" cx=\"769\" cy=\"182\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11300ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"8700ms\" attributeName=\"visibility\"" +
                    " from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star14\" cx=\"513\" cy=\"81\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9800ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"10200ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star15\" cx=\"624\" cy=\"152\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11400ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"8600ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star16\" cx=\"494\" cy=\"255\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"10600ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"9400ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star17\" cx=\"408\" cy=\"66\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"8900ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"11100ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star18\" cx=\"553\" cy=\"270\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11300ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"8700ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star19\" cx=\"111\" cy=\"200\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9400ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"10600ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star20\" cx=\"740\" cy=\"81\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"10300ms\"" +
                    " dur=\"1ms\" " +
                    "attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"9700ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star21\" cx=\"798\" cy=\"140\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"12600ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"7400ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star22\" cx=\"187\" cy=\"128\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"8900ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"11100ms\" attributeName=\"visibility\"" +
                    " from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star23\" cx=\"137\" cy=\"233\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9600ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"10400ms\" attributeName=\"visibility\"" +
                    " from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star24\" cx=\"247\" cy=\"156\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"12000ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"8000ms\" attributeName=\"visibility\" " +
                    "from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star25\" cx=\"262\" cy=\"122\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11200ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"8800ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star26\" cx=\"325\" cy=\"272\" rx=\"3\"" +
                    " ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"10600ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"9400ms\" attributeName=\"visibility\" from=\"visible\"" +
                    " to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star27\" cx=\"415\" cy=\"185\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11700ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"8300ms\" attributeName=\"visibility\"" +
                    " from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star28\" cx=\"677\" cy=\"140\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11900ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"8100ms\" attributeName=\"visibility\" from=\"visible\"" +
                    " to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star29\" cx=\"49\" cy=\"249\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9600ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" " +
                    "to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"10400ms\" attributeName=\"visibility\" from=\"visible\"" +
                    " to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star30\" cx=\"391\" cy=\"318\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"12300ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\"" +
                    " to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"7700ms\" attributeName=\"visibility\" from=\"visible\"" +
                    " to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star31\" cx=\"188\" cy=\"239\" rx=\"3\" ry=\"3\"" +
                    " fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"12100ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" " +
                    "from=\"hidden\" to=\"visible\" fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"7900ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star32\" cx=\"553\" cy=\"235\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"10900ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"9100ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star33\" cx=\"659\" cy=\"104\"" +
                    " rx=\"3\" ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9400ms\"" +
                    " dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"10600ms\" attributeName=\"visibility\" from=\"visible\" " +
                    "to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star34\" cx=\"286\" cy=\"114\" rx=\"3\" " +
                    "ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"8400ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\"" +
                    " dur=\"11600ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\"" +
                    " " +
                    "/>\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star35\" cx=\"652\" cy=\"329\" " +
                    "rx=\"3\" ry=\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9200ms\" " +
                    "dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\"" +
                    " fill=\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" " +
                    "dur=\"10800ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" " +
                    "/>\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star36\" cx=\"694\" cy=\"270\" rx=\"3\" ry=\"3\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"11500ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\" " +
                    "/>\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"8500ms\"" +
                    " attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star37\" cx=\"116\" cy=\"279\" rx=\"3\" ry=" +
                    "\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9900ms\" dur=" +
                    "\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=" +
                    "\"freeze\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=" +
                    "\"10100ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star38\" cx=\"607\" cy=\"305\" rx=\"3\" ry=" +
                    "\"3\" fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"9900ms\" dur=\"" +
                    "1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze" +
                    "\" />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"" +
                    "10100ms\" attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "<ellipse id=\"star39\" cx=\"465\" cy=\"165\" rx=\"3\" ry=\"3\" " +
                    "fill=\"rgb(255,255,255)\" visibility=\"hidden\" >\n" +
                    "\n" +
                    "    <animate attributeType=\"xml\" begin =\"12700ms\" dur=\"1ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"" +
                    " />\n" +
                    "    <animate attributeType=\"xml\" begin =\"20000ms\" dur=\"7300ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"hidden\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "\n" +
                    " </svg>");
  }

}
