package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class MovementByVector(subanimation: Animation, dx: Int, dy: Int) extends Animation {
    override def baseDuration: AnimationDuration = subanimation.baseDuration
    override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
        val xOffset = (dx.toDouble * atTime / totalDuration).toInt
        val yOffset = (dy.toDouble * atTime / totalDuration).toInt

        val intermediateCanvas = new Canvas {
            override def drawChar(x: Int, y: Int, char: Char): Unit = {
                canvas.drawChar(x + xOffset, y + yOffset, char)
            }
        }

        subanimation.draw(atTime, totalDuration)(intermediateCanvas)
    }
}
