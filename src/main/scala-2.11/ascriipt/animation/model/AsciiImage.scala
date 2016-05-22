package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

class AsciiImage(val chars: Array[Array[Char]], val transparent: Char = '\0') extends AnimationObject {
    override def draw(startingPoint: (Int, Int))(implicit canvas: Canvas): Unit = {
        val (startX, startY) = startingPoint
        for {
            (row, x) <- chars.zipWithIndex
            (char, y) <- row.zipWithIndex
        } {
            if(char != transparent) {
                canvas.setChar((startY + y, startX + x), char)
            }
        }
    }
}
