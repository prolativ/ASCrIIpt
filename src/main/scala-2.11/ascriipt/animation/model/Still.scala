package ascriipt.animation.model

trait Still extends MovementByVector {
  self: AnimationObject =>
  override val dx: Int = 0
  override val dy: Int = 0
  override val baseDuration: AnimationDuration = MinimalDuration(0)
}
