package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

trait MovementByVector extends Animation {
  self: AnimationObject =>

  val dx: Int
  val dy: Int
  val baseDuration: AnimationDuration = MinimalDuration(0)

  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    val (x, y) = self.point
    if (atTime < totalDuration) {
      val calcX = x + (dx.toDouble * atTime / totalDuration).toInt
      val calcY = y + (dy.toDouble * atTime / totalDuration).toInt
      self.draw((calcX, calcY))
    }
  }

}
