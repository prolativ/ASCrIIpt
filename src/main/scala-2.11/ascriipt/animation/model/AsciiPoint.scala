package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class AsciiPoint(x: Int, y: Int, filling: Char) extends StaticAnimation {
  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    canvas.setChar((x, y), filling)
  }
}
