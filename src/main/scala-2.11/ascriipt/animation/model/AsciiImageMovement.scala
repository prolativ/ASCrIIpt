package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

class AsciiImageMovement(startX: Int,
                         startY: Int,
                         dx: Int,
                         dy: Int,
                         filling: Char,
                         asciiImage: AsciiImage
                        ) extends Animation {
  override def baseDuration: AnimationDuration = MinimalDuration(20)

  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    if(atTime < totalDuration) {
      val xMove = startX + (dx.toDouble * atTime / totalDuration).toInt
      val yMove = startY + (dy.toDouble * atTime / totalDuration).toInt
      val chars = asciiImage.chars
      val transparent = asciiImage.transparent

      for {
        (row, x) <- chars.zipWithIndex
        (char, y) <- row.zipWithIndex
      } {
        if(char != transparent) {
          canvas.drawChar(y+yMove, x+xMove, char)
        }
      }
    }
  }
}
