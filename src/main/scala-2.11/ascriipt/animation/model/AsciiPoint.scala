package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

class AsciiPoint(x: Int, y: Int, filling: Char) extends AnimationObject {
    override def draw(startingPoint: (Int, Int))(implicit canvas: Canvas): Unit =  {
        canvas.drawChar(startingPoint, filling)
    }
}
