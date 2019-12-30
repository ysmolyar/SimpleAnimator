# Simple Animator
We would like to thank not only our mothers, 
but also Vidoje Mihajlovik for the opportunity today to
present to you a library for an animator model just one day late. 

Created by Yonatan Smolyar and Anthony Dominianni.

## Edits Made

##### AnimatorModel:
* Changed `getAnimationDescription();` to `getAnimationDescription(int fps);` 
because the description changes as frames per second changes
* Changed field `shapeToAnimationMap` from `HashMap<IShape, ArrayList<Animation>>` to 
``HashMap<ITextableShape, ArrayList<Animation>>`` for convenience - an `ITextableShape`
is an `IShape` so this change is backwards compatible.                                             

##### Shapes:
* Added interfaces `ITextableAnimatedShape` and `ITextableShape` which 
extend IShape because IShapes did not have enough information for SVG and text views.
* Deleted `MtShape`, not necessary

##### Animation:
* Deleted `Appears` because `ITextableShape` knows when its start time and end time is.

## Functionality

#### AnimatorModel

An `AnimatorModel` is an object that promises to provide necessary
functionality for animating 2D shapes.

A user can:
* Get a description of the contents in the model
* Get the description of all active shapes at time `t`
* Calculate the number of frames in the animation

#### Animations

For the purposes of this assignment,there are 4 `Animation` implementations:
* `Appears`
* `ColorChange`
* `Move`
* `Scale`

Each Animation contains a `String` value indicating the name of the associated `IShape` object.

#### Shapes

There are 2 Shape implementations:
* `Oval`
* `Rectangle`

All `IShapes` must be named uniquely. An `IShape` cannot contain negative dimensions.

There are 2 additional Interfaces:
* `ITextableShape` (knows when it appears and dissapears)
* `ITextableAnimatedShape` (knows when it appears, dissapears, and has a list of its animations)

These interfaces are implemented in
* `TextableShapeImpl`, and 
* `TextableAnimatedShapeImpl` respectively. 

#### View

There are 3 views, all of which are `IView`s:
* `GuiView` for visual animations
* `TextView` for text representations of animations
* `SvgView` for SVG representations of animations

A `TextView` and `SvgView` are given an `Appendable` to append the interpretation of the model to: 
this may be a new file to write to, or `System.out`, or any other `Appendable`.

A `TextView` appends a `String` describing the animation, while an `SvgView` appends an XML file, 
representing the Scalable Vector Graphic.

There are 2 operations supported by `IView`: `display()` and `close()`. 

#### Controller

There are 3 implementations of `IAnimatorController`:
* `GuiAnimatorController`
* `SvgViewController`
* `TextAnimatorController`

Each Controller takes a `AnimatorModel` and a `IView`.

There is only one method `go()`, which simply facilitates the transfer of relevant information from 
the model to the view.


## Logic 

The `AnimatorModelImpl` will iterate through its given `ArrayList<IShapes>` and `ArrayList<Animations>`
during construction to: 
* Determine the frame length, which is simply the largest `t1` of any given `Animation`
* Link all `IShape`s to their corresponding `Animation`s (by name)
* Create a `HashMap<Integer, ArrayList<IShape>` where each `Integer` represents a frame
and its corresponding value set is the result of applying all animations to their shapes at time `t`.

Users cannot modify the contents of the 
`AnimatorModel` after instantiation. 


