package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

class Waiting extends Animation {
    override def baseDuration: AnimationDuration = MinimalDuration(0)

    override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {}
}
