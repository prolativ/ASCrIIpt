package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class Triangle(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int, filling: Char) extends StaticAnimation {
  private val minX = Seq(x1, x2, x3).min
  private val maxX = Seq(x1, x2, x3).max
  private val minY = Seq(y1, y2, y3).min
  private val maxY = Seq(y1, y2, y3).max

  private def orient2d(ax: Int, ay: Int, bx: Int, by: Int, cx: Int, cy: Int): Int = {
    (bx - ax) * (cy - ay) - (by - ay) * (cx - ax)
  }

  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    for {
      x <- minX to maxX
      y <- minY to maxY
    } {
      val w1 = orient2d(x2, y2, x3, y3, x, y)
      val w2 = orient2d(x3, y3, x1, y1, x, y)
      val w3 = orient2d(x1, y1, x2, y2, x, y)
      if (w1 >= 0 && w2 >= 0 && w3 >= 0) {
        canvas.setChar((x, y), filling)
      }
    }
  }

}
