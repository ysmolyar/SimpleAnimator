package cs3500.animator.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import cs3500.animator.animations.Animation;

public interface IShape extends Cloneable {

  /**
   * Get the description of the shape.
   *
   * @return string description of shape
   */
  String toString();

  /**
   * Get the type of the shape.
   *
   * @return String representation of shape type
   */
  String getType();

  /**
   * Get the x coordinate.
   *
   * @return x
   */
  float getX();

  /**
   * Get the y coordinate.
   *
   * @return y
   */
  float getY();

  /**
   * Get the width component of the shape.
   *
   * @return width of the shape
   */
  float getWidth();

  /**
   * Get the height component of the shape.
   *
   * @return height of the shape
   */
  float getHeight();

  /**
   * Get the largest x value occupied by the given shape.
   *
   * @return largest x-value occupied by a shape
   */
  float getMaxWidth();

  /**
   * Get the largest y value occupied by the given shape.
   *
   * @return largest y-value occupied by the shape
   */
  float getMaxHeight();

  /**
   * Get the color.
   *
   * @return color
   */
  Color getColor();

  /**
   * Get the full description of this shape.
   *
   * @return a Description describing shape
   */
  String getDescription();

  /**
   * Gets the name of this shape.
   *
   * @return name
   */
  String getName();

  /**
   * Accepts a visiting Animation.
   *
   * @param animation animation
   * @param t         the time at which the animation is applied
   * @return an updated IShape
   */
  IShape acceptAnimation(Animation animation, int t);

  /**
   * Get the coordinates of this shape.
   *
   * @return String
   */
  String getCoordinates();

  /**
   * Get the width and height of this shape.
   *
   * @return String
   */
  String getSize();

  /**
   * Returns identical IShape clone of this IShape.
   *
   * @return identical IShape clone
   */
  IShape clone();

  /**
   * Draws this shape on given graphics object.
   * EFFECT: sets the color to this shapes color then fills a shape of its type on the graphics.
   *
   * @param g graphics object
   */
  void draw(Graphics2D g);

}
