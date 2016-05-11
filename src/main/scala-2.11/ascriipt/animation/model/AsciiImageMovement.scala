package ascriipt.animation.model

case class AsciiImageMovement(startX: Int,
                              startY: Int,
                              endX: Int,
                              endY: Int,
                              filling: Char,
                              asciiImage: AsciiImage
                             ) extends AsciiImage(asciiImage.chars, asciiImage.transparent) with Movable with Animation {
  override def baseDuration: AnimationDuration = MinimalDuration(0)
}
