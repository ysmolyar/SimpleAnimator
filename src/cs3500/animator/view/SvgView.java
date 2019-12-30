package cs3500.animator.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import cs3500.animator.animations.Animation;
import cs3500.animator.animations.ColorChange;
import cs3500.animator.animations.Move;
import cs3500.animator.animations.Scale;
import cs3500.animator.shapes.ITextableAnimatedShape;

/**
 * SvgView implements IView functionality, for an SVG representation of an animation.
 */
public class SvgView implements ISvgView {

  private Appendable output;
  private ArrayList<ITextableAnimatedShape> animatedShapes;
  private int framesPerSecond;
  private final int defaultWidth = 800;
  private final int defaultHeight = 800;

  /**
   * Public constructor for producing SvgView, requires speed and the Appendable to
   * output to.
   *
   * @param framesPerSecond the speed, in frames per second
   * @param output          the output destination, whether a file or System.out
   */
  public SvgView(int framesPerSecond, Appendable output) {
    this.output = output;
    this.framesPerSecond = framesPerSecond;
  }

  /**
   * Convenience constructor for SvgView, takes just framesPerSecond and sets output
   * to default (System.out)
   *
   * @param framesPerSecond the speed of animation, in frames per second
   */
  public SvgView(int framesPerSecond) {
    this.output = System.out;
    this.framesPerSecond = framesPerSecond;
  }

  /**
   * Default constructor for SvgView.
   */
  public SvgView() {
    this.output = System.out;
    this.framesPerSecond = 1;
  }


  @Override
  public String toString() {
    return "SVG";
  }

  @Override
  public void display() {
    try {
      output.append(makeSVG().toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() {
    if (output instanceof PrintWriter) {
      ((PrintWriter) output).close();
    }
  }

  @Override
  public void updateContents(Collection<ITextableAnimatedShape> shapes) {
    this.animatedShapes = new ArrayList<>(shapes);
  }

  /**
   * The makeSVG() method is the master-method to creating SVG text-based representations
   * of an animation. This method dispatches to lower private methods, which handle specific
   * parts of SVG construction. Returns the SVG as an Appendable.
   *
   * @return svg representation of the animation, as an Appendable
   */
  private Appendable makeSVG() {
    Appendable svg = new StringBuilder("<svg");
    String width = Integer.toString(this.defaultWidth);
    String height = Integer.toString(this.defaultHeight);

    try {
      svg.append(" width=\"").append(width).append("\"");
      svg.append(" height=\"").append(height).append("\"");
      svg.append(" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" >").append("\n\n");

      for (ITextableAnimatedShape animatedShape : this.animatedShapes) {
        //all of below block is header tag of shape SVG
        svg.append("<").append(animatedShape.getType());
        svg.append(" id=\"").append(animatedShape.getName()).append("\"");
        svg.append(" " + animatedShape.getSvgX()).append("=\"")
                .append(Integer.toString((int) animatedShape.getX())).append("\"");
        svg.append(" " + animatedShape.getSvgY()).append("=\"")
                .append(Integer.toString((int) animatedShape.getY())).append("\"");
        svg.append(" " + animatedShape.getSvgWidth()).append("=\"")
                .append(Integer.toString((int) animatedShape.getWidth())).append("\"");
        svg.append(" " + animatedShape.getSvgHeight()).append("=\"")
                .append(Integer.toString((int) animatedShape.getHeight())).append("\"");
        svg.append(" fill=\"rgb(").append(Integer.toString(animatedShape.getColor().getRed()))
                .append(",").append(Integer.toString(animatedShape.getColor().getGreen()))
                .append(",").append(Integer.toString(animatedShape.getColor().getBlue()))
                .append(")\"");
        svg.append(" visibility=\"hidden\" >").append("\n\n");

        //first animation is always appears
        svg.append("    <animate").append(" attributeType=\"xml\"");
        svg.append(" begin =\"").append(Integer.toString((int)
                (animatedShape.getAppearsTime() * 1000) / framesPerSecond)).append("ms\"");
        svg.append(" dur=\"")
                .append(Integer.toString(1)).append("ms\"");
        svg.append(" attributeName=\"").append("visibility").append("\"");
        svg.append(" from=\"").append("hidden").append("\"");
        svg.append(" to=\"").append("visible").append("\"");
        svg.append(" fill=\"").append("freeze").append("\"");
        svg.append(" />\n");

        //Creates <animate/> tags for each shape, based on its given ArrayList<Animation>
        for (Animation animation : animatedShape.getListOfAnimations()) {
          //Switch between supported animation types, and dispatch to respective methods
          //to handle the creation of the animate tag.
          switch (animation.getAnimationName()) {
            case "Move":
              svg.append(this.makeSVGMove(animation, animatedShape));
              break;
            case "ColorChange":
              svg.append(this.makeSVGColorChange(animation));
              break;
            case "Scale":
              svg.append(this.makeSVGScale(animation, animatedShape));
              break;
            default:
              throw new IllegalArgumentException("Unsupported animation type!");
          }


        }
        //last animation is always disappears
        svg.append("    <animate").append(" attributeType=\"xml\"");
        svg.append(" begin =\"").append(Integer.toString((int)
                (animatedShape.getDisappearsTime() * 1000) / framesPerSecond)).append("ms\"");
        svg.append(" dur=\"")
                .append(Integer.toString(((int)
                        (animatedShape.getDisappearsTime() - animatedShape.getAppearsTime()) * 1000)
                        / framesPerSecond)).append("ms\"");
        svg.append(" attributeName=\"").append("visibility").append("\"");
        svg.append(" from=\"").append("visible").append("\"");
        svg.append(" to=\"").append("hidden").append("\"");
        svg.append(" />\n");

        svg.append("</").append(animatedShape.getType()).append(">\n\n");
      }
      svg.append("\n </svg>");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return svg;
  }

  /**
   * Creates <animate/> tag for a move, and decomposes the move into two SEPARATE
   * <animate/> tags, one for each horizontal and vertical movement.
   *
   * @return String of the svg animate tags, which will be appended to the svg
   */
  private String makeSVGMove(Animation move, ITextableAnimatedShape animatedShape) {
    Move thisMove = (Move) move;
    StringBuilder makeMove = new StringBuilder();

    try {
      if (thisMove.getFromX() != thisMove.getToX()) {
        StringBuilder horizontalMove = new StringBuilder();
        horizontalMove.append("    <animate").append(" attributeType=\"xml\"");
        horizontalMove.append(" begin =\"").append(Integer.toString((move.getStartTime() * 1000)
                / framesPerSecond)).append("ms\"");
        horizontalMove.append(" dur=\"").append(Integer.toString(
                ((move.getEndTime() - move.getStartTime()) * 1000)
                        / framesPerSecond)).append("ms\"");
        horizontalMove.append(" attributeName=\"").append(animatedShape.getSvgX()).append("\"");
        horizontalMove.append(" from=\"").append(Integer.toString(thisMove.getFromX()))
                .append("\"");
        horizontalMove.append(" to=\"").append(Integer.toString(thisMove.getToX())).append("\"");
        horizontalMove.append(" fill=\"").append("freeze").append("\"");
        horizontalMove.append(" />\n");
        makeMove.append(horizontalMove);
      }
      if (thisMove.getFromY() != thisMove.getToY()) {
        StringBuilder verticalMove = new StringBuilder();
        verticalMove.append("    <animate").append(" attributeType=\"xml\"");
        verticalMove.append(" begin =\"").append(Integer.toString((move.getStartTime() * 1000)
                / framesPerSecond)).append("ms\"");
        verticalMove.append(" dur=\"").append(Integer.toString(
                ((move.getEndTime() - move.getStartTime()) * 1000)
                        / framesPerSecond)).append("ms\"");
        verticalMove.append(" attributeName=\"").append(animatedShape.getSvgY()).append("\"");
        verticalMove.append(" from=\"").append(Integer.toString(thisMove.getFromY())).append("\"");
        verticalMove.append(" to=\"").append(Integer.toString(thisMove.getToY())).append("\"");
        verticalMove.append(" fill=\"").append("freeze").append("\"");
        verticalMove.append(" />\n");
        makeMove.append(verticalMove);
      }


    } catch (Exception e) {
      //throws popup error
    }

    return makeMove.toString();
  }


  /**
   * Creates <animate/> tag for a color change animation.
   *
   * @return String of the svg animate tags, which will be appended to the svg
   */
  private String makeSVGColorChange(Animation colorChange) {
    ColorChange thisColorChange = (ColorChange) colorChange;
    StringBuilder svgColorChange = new StringBuilder();
    try {
      if (!thisColorChange.getColorFrom().equals(thisColorChange.getColorTo())) {
        svgColorChange.append("    <animate").append(" attributeType=\"xml\"");
        svgColorChange.append(" begin =\"").append(Integer.toString(
                (colorChange.getStartTime() * 1000) / framesPerSecond)).append("ms\"");
        svgColorChange.append(" dur=\"").append(Integer.toString(
                ((colorChange.getEndTime() - colorChange.getStartTime()) * 1000)
                        / framesPerSecond)).append("ms\"");
        svgColorChange.append(" attributeName=\"").append("fill").append("\"");
        svgColorChange.append(" from=\"rgb(").append(
                Integer.toString(thisColorChange.getColorFrom().getRed())).append(",");
        svgColorChange.append(Integer.toString(thisColorChange.getColorFrom().getGreen()));
        svgColorChange.append(",")
                .append(Integer.toString(thisColorChange.getColorFrom().getBlue()));
        svgColorChange.append(")\"");
        svgColorChange.append(" to=\"rgb(").append(
                Integer.toString(thisColorChange.getColorTo().getRed())).append(",");
        svgColorChange.append(Integer.toString(thisColorChange.getColorTo().getGreen()));
        svgColorChange.append(",")
                .append(Integer.toString(thisColorChange.getColorTo().getBlue()));
        svgColorChange.append(")\"");
        svgColorChange.append(" fill=\"").append("freeze").append("\"");
        svgColorChange.append(" />\n");
      }
    } catch (Exception e) {
      //throws popup error
    }
    return svgColorChange.toString();
  }


  /**
   * Creates <animate/> tag for a scale, and decomposes the scale into two SEPARATE
   * <animate/> tags, if necessary, one for each horizontal and vertical scaling.
   *
   * @return String of the svg scale tags, which will be appended to the svg
   */
  private String makeSVGScale(Animation scale, ITextableAnimatedShape animatedShape) {
    Scale thisScale = (Scale) scale;
    StringBuilder makeScale = new StringBuilder();

    try {
      if (thisScale.getScaleX() != thisScale.getStartWidth()) {
        StringBuilder horizontalScale = new StringBuilder();
        horizontalScale.append("    <animate").append(" attributeType=\"xml\"");
        horizontalScale.append(" begin =\"")
                .append(Integer.toString((scale.getStartTime() * 1000)
                        / framesPerSecond)).append("ms\"");
        horizontalScale.append(" dur=\"").append(Integer.toString(
                ((scale.getEndTime() - scale.getStartTime()) * 1000)
                        / framesPerSecond)).append("ms\"");
        horizontalScale.append(" attributeName=\"")
                .append(animatedShape.getSvgWidth()).append("\"");
        horizontalScale.append(" from=\"")
                .append(Integer.toString((int) thisScale.getStartWidth())).append("\"");
        horizontalScale.append(" to=\"")
                .append(Integer.toString((int) (thisScale.getScaleX()))).append("\"");
        horizontalScale.append(" fill=\"").append("freeze").append("\"");
        horizontalScale.append(" />\n");
        makeScale.append(horizontalScale);
      }
      if (thisScale.getScaleY() != 1) {
        StringBuilder verticalScale = new StringBuilder();
        verticalScale.append("    <animate").append(" attributeType=\"xml\"");
        verticalScale.append(" begin =\"").append(Integer.toString((scale.getStartTime() * 1000)
                / framesPerSecond)).append("ms\"");
        verticalScale.append(" dur=\"").append(Integer.toString(
                ((scale.getEndTime() - scale.getStartTime()) * 1000)
                        / framesPerSecond)).append("ms\"");
        verticalScale.append(" attributeName=\"").append(animatedShape.getSvgHeight()).append("\"");
        verticalScale.append(" from=\"")
                .append(Integer.toString((int) thisScale.getStartHeight())).append("\"");
        verticalScale.append(" to=\"")
                .append(Integer.toString((int) (thisScale.getScaleY()))).append("\"");
        verticalScale.append(" fill=\"").append("freeze").append("\"");
        verticalScale.append(" />\n");
        makeScale.append(verticalScale);
      }


    } catch (Exception e) {
      //throws popup error
    }

    return makeScale.toString();
  }

}
