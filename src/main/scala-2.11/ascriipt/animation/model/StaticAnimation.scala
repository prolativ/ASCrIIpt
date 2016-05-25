package ascriipt.animation.model

trait StaticAnimation extends Animation {
  override def baseDuration = MinimalDuration(0)
}
