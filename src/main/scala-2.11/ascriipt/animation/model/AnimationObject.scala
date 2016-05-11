package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

trait AnimationObject {
  def draw(startingPoint: (Int, Int))(implicit canvas: Canvas)
}
