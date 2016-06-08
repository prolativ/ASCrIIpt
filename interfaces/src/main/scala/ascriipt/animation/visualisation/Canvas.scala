package ascriipt.animation.visualisation

trait Canvas {
  def setChar(point: (Int, Int), char: Char): Unit //implementation should ignore points outside the canvas
}
