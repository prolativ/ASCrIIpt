package ascriipt.animation.model

sealed trait AnimationDuration extends Any

object AnimationDuration {
  def partition(durations: Seq[AnimationDuration]): (Seq[ExactDuration], Seq[MinimalDuration]) = {
    val exactDurations = durations.collect { case duration: ExactDuration => duration }
    val minimalDurations = durations.collect { case duration: MinimalDuration => duration }
    (exactDurations, minimalDurations)
  }
}

case class ExactDuration(value: Long) extends AnyVal with AnimationDuration

case class MinimalDuration(value: Long) extends AnyVal with AnimationDuration


object ExactDurations {
  def unapply(durations: Seq[ExactDuration]): Option[Long] = {
    durations match {
      case head :: tail if tail.forall(_ == head) => Some(head.value)
      case _ => None
    }
  }
}