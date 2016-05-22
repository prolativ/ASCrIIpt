package ascriipt.animation.visualisation

trait Canvas {
    def clear(): Unit
    def show(): Unit
    def setChar(point: (Int, Int), char: Char): Unit //implementation should ignore points outside the canvas
}
