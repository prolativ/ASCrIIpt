package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class AsciiImage(leftX: Int, topY: Int, chars: Array[Array[Char]], transparent: Char = '\0') extends Animation {
  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    for {
      (row, x) <- chars.zipWithIndex
      (char, y) <- row.zipWithIndex
    } {
      if (char != transparent) {
        canvas.setChar((x + leftX, y + topY), char)
      }
    }
  }

  override def baseDuration = MinimalDuration(0)
}
