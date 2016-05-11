package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

trait Movable extends Animation {
  self: AnimationObject =>

  val startX: Int
  val startY: Int
  val endX: Int
  val endY: Int
  val filling: Char

  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    val dx = endX - startX
    val dy = endY - startY
    if(atTime < totalDuration) {
      val x = startX + (dx.toDouble * atTime / totalDuration).toInt
      val y = startY + (dy.toDouble * atTime / totalDuration).toInt
      self.draw((x, y))
    }
  }
}
