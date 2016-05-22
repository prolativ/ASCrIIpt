package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

trait AnimationObject {
  val point: (Int, Int)
  def draw(startingPoint: (Int, Int))(implicit canvas: Canvas)
}
