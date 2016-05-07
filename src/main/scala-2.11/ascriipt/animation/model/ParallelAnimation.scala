package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class ParallelAnimation(subanimations: Seq[Animation]) extends Animation {
    override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
        subanimations.foreach(_.draw(atTime, totalDuration))
    }

    override def baseDuration: AnimationDuration = {
        AnimationDuration.partition(subanimations.map(_.baseDuration)) match {
            case (ExactDurations(exactDurationValue), minimalDurations)
                if minimalDurations.forall(_.value <= exactDurationValue) =>
                ExactDuration(exactDurationValue)

            case (Nil, minimalDurations) if minimalDurations.nonEmpty =>
                val minDurationValue = minimalDurations.map(_.value).max
                MinimalDuration(minDurationValue)

            case _ => throw AnimationTimeException(
                s"Cannot compute base duration for parallel animation with subanimations: ${subanimations}"
            )
        }
    }
}
