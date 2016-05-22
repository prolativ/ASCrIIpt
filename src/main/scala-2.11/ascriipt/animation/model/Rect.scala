package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class Rect(width: Int, height: Int, leftX: Int, topY: Int, filling: Char) extends Animation {
  override def baseDuration: AnimationDuration = MinimalDuration(0)

  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    for {
      x <- leftX until leftX + width
      y <- topY until topY + height
    } {
      canvas.setChar((x, y), filling)
    }
  }
}
