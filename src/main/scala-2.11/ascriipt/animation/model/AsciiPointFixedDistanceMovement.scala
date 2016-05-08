package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

class AsciiPointFixedDistanceMovement(
                          startX: Int,
                          startY: Int,
                          dx: Int,
                          dy: Int,
                          filling: Char
                          ) extends Animation {
    override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
        if(atTime < totalDuration) {
            val x = startX + (dx.toDouble * atTime / totalDuration).toInt
            val y = startY + (dy.toDouble * atTime / totalDuration).toInt
            canvas.drawChar(x, y, filling)
        }
    }

    override def baseDuration: AnimationDuration = MinimalDuration(20)
}
