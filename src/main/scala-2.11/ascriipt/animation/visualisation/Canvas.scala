package ascriipt.animation.visualisation

trait Canvas {
  def clear(): Unit

  def show(): Unit

  def drawChar(x: Int, y: Int, char: Char): Unit //implementation should ignore points outside the canvas
}
