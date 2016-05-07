package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class TimedWaiting(duration: Long) extends Animation {
    override def baseDuration: AnimationDuration = ExactDuration(duration)

    override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {}
}
