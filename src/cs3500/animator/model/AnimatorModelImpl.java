package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cs3500.animator.animations.Animation;
import cs3500.animator.animations.ColorChange;
import cs3500.animator.animations.Move;
import cs3500.animator.animations.Scale;
import cs3500.animator.shapes.IShape;
import cs3500.animator.shapes.ITextableAnimatedShape;
import cs3500.animator.shapes.ITextableShape;
import cs3500.animator.shapes.Oval;
import cs3500.animator.shapes.Rectangle;
import cs3500.animator.shapes.TextableAnimatedShapeImpl;
import cs3500.animator.shapes.TextableShapeImpl;
import cs3500.animator.util.TweenModelBuilder;

/**
 * A class to represent an implementation of a simple 2D animator model.
 */
public class AnimatorModelImpl implements AnimatorModel {

  private ArrayList<ITextableShape> shapes;
  private ArrayList<Animation> animations;
  private int frames;
  private HashMap<ITextableShape, ArrayList<Animation>> shapeToAnimationMap;
  private HashMap<Integer, ArrayList<IShape>> frameToScene;


  /**
   * A static Builder class for the AnimatorModelBuilder. Allows for the accumulation of
   * accepted shapes and animations, before the model is built. Builder is final such that
   * after the build() method is called, the animation model's shapes and animations
   * will never change.
   */
  public static final class Builder implements TweenModelBuilder<AnimatorModel> {

    private ArrayList<ITextableShape> shapes = new ArrayList<>();
    private ArrayList<Animation> animations = new ArrayList<>();

    /**
     * Empty constructor for a builder, which can be used with an AnimationFileReader to create
     * a new AnimatorModel.
     */
    public Builder() {
      //builder constructor is empty because before reading from file, animation is empty
    }

    @Override
    public TweenModelBuilder<AnimatorModel> addOval(String name, float cx, float cy,
                                                    float xRadius, float yRadius,
                                                    float red, float green, float blue,
                                                    int startOfLife, int endOfLife) {
      shapes.add(
              new TextableShapeImpl(new Oval(name, cx, cy,
                      new Color((int) (red * 255), (int) (green * 255), (int) (blue * 255)),
                      xRadius, yRadius), startOfLife, endOfLife));
      return this;
    }

    @Override
    public TweenModelBuilder<AnimatorModel> addRectangle(String name, float lx, float ly,
                                                         float width, float height,
                                                         float red, float green, float blue,
                                                         int startOfLife, int endOfLife) {
      shapes.add(
              new TextableShapeImpl(new Rectangle(name, lx, ly,
                      new Color((int) (red * 255), (int) (green * 255), (int) (blue * 255)),
                      width, height), startOfLife, endOfLife));
      return this;
    }

    @Override
    public TweenModelBuilder<AnimatorModel> addMove(String name, float moveFromX, float moveFromY,
                                                    float moveToX, float moveToY,
                                                    int startTime, int endTime) {
      animations.add(new Move(name, startTime, endTime,
              moveFromX, moveFromY, moveToX, moveToY));
      return this;
    }

    @Override
    public TweenModelBuilder<AnimatorModel> addColorChange(String name,
                                                           float oldR, float oldG, float oldB,
                                                           float newR, float newG, float newB,
                                                           int startTime, int endTime) {
      animations.add(new ColorChange(name, startTime, endTime,
              new Color((int) (oldR * 255), (int) (oldG * 255), (int) (oldB * 255)),
              new Color((int) (newR * 255), (int) (newG * 255), (int) (newB * 255))));
      return this;
    }

    @Override
    public TweenModelBuilder<AnimatorModel> addScaleToChange(String name,
                                                             float fromSx, float fromSy,
                                                             float toSx, float toSy,
                                                             int startTime, int endTime) {
      animations.add(new Scale(name, startTime, endTime, fromSx, fromSy, toSx, toSy));
      return this;
    }

    @Override
    public AnimatorModel build() {
      return new AnimatorModelImpl(this.shapes, this.animations);
    }
  }

  /**
   * A constructor to represent a simple 2D shape animator model.
   *
   * @param shapes     a list of shapes
   * @param animations a list of animations
   */

  public AnimatorModelImpl(ArrayList<ITextableShape> shapes,
                           ArrayList<Animation> animations) {
    this.shapes = shapes;
    this.animations = animations;
    this.frames = calculateFrames();
    this.shapeToAnimationMap = initAttachments();
    this.frameToScene = initMap();
  }


  @Override
  public String getAnimationDescription(int fps) {
    return this.printListOfShapes(fps) + "\n" + this.printListOfAnimations(fps);
  }

  @Override
  public ArrayList<IShape> getCurrentSceneAt(int t) throws IllegalArgumentException {
    if (t >= 0 && t <= this.frames) {
      return frameToScene.get(t);
    } else {
      throw new IllegalArgumentException("invalid time");
    }
  }

  @Override
  public ArrayList<ITextableAnimatedShape> getListOfAnimatedShapes() {
    ArrayList<ITextableAnimatedShape> animatedShapeList = new ArrayList<>();
    for (ITextableShape shape : shapeToAnimationMap.keySet()) {
      ITextableAnimatedShape thisAnimatedShape = new
              TextableAnimatedShapeImpl(shape.clone(), shapeToAnimationMap.get(shape));
      animatedShapeList.add(thisAnimatedShape);
    }
    return animatedShapeList;
  }

  @Override
  public String printListOfAnimations(int fps) {
    StringBuilder sb = new StringBuilder();
    for (Animation animation : animations) {
      sb.append(animation.toStringWithFPS(fps) + "\n");
    }
    return sb.toString();
  }

  @Override
  public String printListOfShapes(int fps) {
    StringBuilder sb = new StringBuilder("Shapes:");
    for (ITextableShape shape : shapes) {
      sb.append("\n" + shape.toStringWithFPS(fps));
    }
    return sb.toString();
  }

  @Override
  public int getNumberOfFrames() {
    return this.frames;
  }

  /**
   * Creates the scene at time t by applying every list of animations to the shape to be animated.
   *
   * @param t time in seconds
   * @return the scene
   */
  private ArrayList<IShape> createSceneAtTimeT(int t) {
    ArrayList<IShape> newAnimations = new ArrayList<IShape>();
    for (ITextableShape shape : shapeToAnimationMap.keySet()) {
      if (shape.getAppearsTime() <= t && shape.getDisappearsTime() >= t) {
        IShape shapeToBeAnimated = shape;
        for (Animation animation : shapeToAnimationMap.get(shape)) {
          shapeToBeAnimated = shapeToBeAnimated.acceptAnimation(animation, t);
        }
        newAnimations.add(shapeToBeAnimated);
      }
    }
    return newAnimations;
  }

  /**
   * Links each shape to a list of animations.
   *
   * @return A HashMap with shape as key and a list of animations as values
   */
  private HashMap<ITextableShape, ArrayList<Animation>> initAttachments() {
    HashMap<ITextableShape, ArrayList<Animation>> map = new LinkedHashMap<>();
    for (ITextableShape shape : shapes) {
      ArrayList<Animation> shapeSpecific = new ArrayList<>();
      for (Animation animation : animations) {
        if (shape.getName().equals(animation.getName())
                && (animation.getStartTime() >= shape.getAppearsTime())
                && (animation.getEndTime() <= shape.getDisappearsTime())) {
          shapeSpecific.add(animation);
        }
      }
      map.put(shape, shapeSpecific);
    }
    return map;
  }

  /**
   * Creates the animation.
   *
   * @return a map of frame to scene
   */
  private HashMap<Integer, ArrayList<IShape>> initMap() {
    HashMap<Integer, ArrayList<IShape>> map = new LinkedHashMap<>();
    for (int i = 0; i < this.frames; i++) {
      map.put(i, this.createSceneAtTimeT(i));
    }
    return map;
  }

  /**
   * Iterates through animations to find total frames in scene.
   *
   * @return an int
   */
  private int calculateFrames() {
    int t = 0;
    for (Animation a : animations) {
      if (a.getEndTime() > t) {
        t = a.getEndTime();
      }
    }
    return t;
  }


}
