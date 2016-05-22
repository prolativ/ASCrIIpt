package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class AsciiPoint(x: Int, y: Int, filling: Char) extends Animation {
  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    canvas.setChar((x, y), filling)
  }

  override def baseDuration = MinimalDuration(0)
}
