package ascriipt.animation.visualisation

import ascriipt.animation.model._
import com.googlecode.lanterna.TerminalFacade

class FakeCanvas(width: Int, height: Int) extends Canvas {
  var canvas: Array[Array[Char]] = Array()
  val screen = TerminalFacade.createScreen()
  screen.startScreen()
  clear()

  override def clear(): Unit = canvas = Array.fill(height, width)(' ')

  override def drawChar(x: Int, y: Int, char: Char): Unit = canvas(y)(x) = char

  override def show(): Unit = {
    val terminal = screen.getTerminal
    for (x <- 0 until width) {
      for (y <- 0 until height) {
        terminal.moveCursor(x, y)
        terminal.putCharacter(canvas(y)(x))
      }
    }
  }
}

object FakeCanvas {
  /*def main(args: Array[String]) {
    val image = Array(
      Array('\0', '\0', '-', '-', '\0', '\0'),
      Array('\0', '/', '\0', '\0', '\\', '\0'),
      Array('|', '\0', '\0', '\0', '\0', '|'),
      Array('|', '\0', '\0', '\0', '\0', '|'),
      Array('\0', '\\', '\0', '\0', '/', '\0'),
      Array('\0', '\0', '-', '-', '\0', '\0')
    )
    val movementsSeq = Seq(
      new AsciiPointFixedDistanceMovement(0, 0, 4, 4, 'Z'),
      new AsciiPointFixedDistanceMovement(8, 8, -7, -7, 'X'),
      new AsciiImageMovement(4, 4, 10, 10, '.', new AsciiImage(image))
    )

    drawAnimation(new ParallelAnimation(movementsSeq))
  }

  def drawAnimation(animation: Animation): Unit = {
    implicit val canvas = new FakeCanvas(60, 25)

    while (true) {
      animation.baseDuration match {
        case ExactDuration(totalDuration) => {
          val timeStep = 100
          (0L until totalDuration by timeStep).foreach { atTime =>
            animation.draw(atTime, totalDuration)
          }
        }
        case MinimalDuration(totalDuration) =>
          val timeStep = 1
          (0L until totalDuration by timeStep).foreach { atTime =>
            animation.draw(atTime, totalDuration)
            canvas.show()
            Thread.sleep(200)
            canvas.clear()
          }
        case _ => throw AnimationTimeException("Root animation has to have exact duration given.")
      }
    }
  }*/
}
