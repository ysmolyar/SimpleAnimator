package cs3500.animator.shapes;

/**
 * Read-Only Decorator interface for IShape, includes time
 * the shape appears and disappears from animation.
 * ITextableShape is intended for use by TextView and SvgView.
 */
public interface ITextableShape extends IShape {

  /**
   * Returns deep copied shape in this ITextableShape.
   *
   * @return shape contained by this wrapper
   */
  IShape getShape();

  /**
   * Gets time at which shape disappears from screen.
   *
   * @return disappears time
   */
  float getDisappearsTime();

  /**
   * Gets time at which shape appears on screen.
   *
   * @return appears time
   */
  float getAppearsTime();

  /**
   * Returns the lifetime of a textable shape.
   *
   * @param framesPerSecond given frames per second
   * @return a String description of shape, appears time, and disappears time.
   */
  String toStringWithFPS(int framesPerSecond);

  /**
   * Returns SVG description of this shape's X-coordinate.
   *
   * @return SVG- x coordinate
   */
  String getSvgX();

  /**
   * Returns SVG description of this shape's Y-coordinate.
   *
   * @return SVG- y coordinate
   */
  String getSvgY();

  /**
   * Returns SVG description of this shape's width.
   *
   * @return SVG- width name
   */
  String getSvgWidth();

  /**
   * Returns SVG description of this shape's height.
   *
   * @return SVG- height name
   */
  String getSvgHeight();

  /**
   * Returns clone of this ITextableShape.
   *
   * @return ITextableShape clone of this shape
   */
  ITextableShape clone();

}
