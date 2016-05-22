package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

case class SequentialAnimation(subanimations: Seq[Animation]) extends Animation {

  override def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas): Unit = {
    val durations = subanimations.map { animation =>
      animation.baseDuration match {
        case ExactDuration(exactDurationValue) => exactDurationValue
        case MinimalDuration(minDurationValue) => totalDuration - minDurationValue
        case _ => throw AnimationTimeException(
          s"Cannot calculate duration filling while drawing sequential animation: ${subanimations}"
        )
      }
    }
    val durationSubsums = durations.scanLeft(0L)(_ + _)
    val animationIndex = durationSubsums.indexWhere(_ > atTime) - 1
    val timeOffset = atTime - durationSubsums(animationIndex)
    subanimations(animationIndex).draw(timeOffset, durations(animationIndex))
  }

  override val baseDuration: AnimationDuration = {
    AnimationDuration.partition(subanimations.map(_.baseDuration)) match {
      case (exactDurations, Nil)
        if exactDurations.nonEmpty => ExactDuration(exactDurations.map(_.value).sum)

      case (exactDurations, minimalDuration :: Nil) =>
        val minDurationValue = exactDurations.map(_.value).sum + minimalDuration.value
        MinimalDuration(minDurationValue)

      case _ => throw AnimationTimeException(
        s"Cannot compute base duration for sequential animation with subanimations: ${subanimations}"
      )
    }
  }
}
