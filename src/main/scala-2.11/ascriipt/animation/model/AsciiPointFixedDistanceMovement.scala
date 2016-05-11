package ascriipt.animation.model


case class AsciiPointFixedDistanceMovement(startX: Int,
                                           startY: Int,
                                           endX: Int,
                                           endY: Int,
                                           override val filling: Char
                                          ) extends AsciiPoint(startX, startY, filling) with Movable with Animation {

  override def baseDuration: AnimationDuration = MinimalDuration(0)
}
