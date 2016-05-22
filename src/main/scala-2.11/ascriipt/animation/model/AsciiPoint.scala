package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class AsciiPoint(filling: Char, val point: (Int, Int) = (0, 0)) extends AnimationObject {
    override def draw(startingPoint: (Int, Int))(implicit canvas: Canvas): Unit =  {
        canvas.setChar(startingPoint, filling)
    }
}
