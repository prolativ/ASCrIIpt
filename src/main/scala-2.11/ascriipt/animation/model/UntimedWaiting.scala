package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case object UntimedWaiting extends Animation {
  override def baseDuration: AnimationDuration = MinimalDuration(0)

  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {}
}
