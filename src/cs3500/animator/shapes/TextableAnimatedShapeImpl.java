package cs3500.animator.shapes;

import java.util.ArrayList;

import cs3500.animator.animations.Animation;

/**
 * A decorator implementation class for a textableshape. This class contains an ArrayList
 * of all animations which will act on this shape.
 */
public class TextableAnimatedShapeImpl extends TextableShapeImpl implements ITextableAnimatedShape {

  private IShape shape;
  private ArrayList<Animation> animations;
  private float appearsTime;
  private float disappearsTime;

  /**
   * Constructor for TextableAnimatedShapeImpl decorator.
   *
   * @param shape          shape to decorate
   * @param animations     list of animations
   * @param appearsTime    time shape appears
   * @param disappearsTime time shape disappears
   */
  public TextableAnimatedShapeImpl(IShape shape, ArrayList<Animation> animations,
                                   float appearsTime, float disappearsTime) {
    super(shape, appearsTime, disappearsTime);
    this.animations = animations;
  }

  /**
   * Convenience constructor for TextableAnimatedShapeImpl. Because this is a decorator for
   * a TextableShape, this constructor allows, for convenience, a TextableAnimatedShapeImpl
   * to be constructed using the prior TextableShape and a new ArrayList of Animations on that
   * TextableShape.
   *
   * @param shape      the TextableShapeImpl which this class is decorating
   * @param animations the animations which apply to that shape during the animation
   */
  public TextableAnimatedShapeImpl(ITextableShape shape, ArrayList<Animation> animations) {
    super(shape.getShape(), shape.getAppearsTime(), shape.getDisappearsTime());
    this.animations = animations;
  }

  @Override
  public ArrayList<Animation> getListOfAnimations() {
    ArrayList<Animation> animationList = new ArrayList<>();
    for (Animation animation : animations) {
      animationList.add(animation.clone());
    }
    return animationList;
  }

  @Override
  public ITextableAnimatedShape clone() {
    return new TextableAnimatedShapeImpl(shape.clone(), this.getListOfAnimations(),
            this.appearsTime, this.disappearsTime);
  }
}
