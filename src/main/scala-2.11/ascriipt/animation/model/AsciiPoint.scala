package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

class AsciiPoint(x: Int, y: Int, filling: Char) extends Animation {
    override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit =  {
        canvas.drawChar(x, y, filling)
    }

    override def baseDuration = MinimalDuration(0)
}
