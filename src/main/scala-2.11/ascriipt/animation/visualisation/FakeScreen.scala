package ascriipt.animation.visualisation

import ascriipt.animation.model._
import com.googlecode.lanterna.TerminalFacade

class FakeScreen(width: Int, height: Int) extends Screen {
  var canvas: Array[Array[Char]] = Array()
  val screen = TerminalFacade.createScreen()
  screen.startScreen()
  clear()

  override def clear(): Unit = canvas = Array.fill(height, width)(' ')

  override def drawChar(x: Int, y: Int, char: Char): Unit = {
    if(x >= 0 && x < width && y >= 0 && y < height) {
      canvas(y)(x) = char
    }
  }

  override def show(): Unit = {
    val terminal = screen.getTerminal

    for {
      x <- 0 until width
      y <- 0 until height
    } {
      terminal.moveCursor(y, x)
      terminal.putCharacter(canvas(y)(x))
    }

  }

  def close(): Unit = {
    screen.stopScreen()
  }
}

object FakeScreen {

  def drawAnimation(animation: Animation): Unit = {
    implicit val canvas = new FakeScreen(60, 25)

    try {
      animation.baseDuration match {
        case ExactDuration(totalDuration) => {
          val timeStep = 100
          (0L until totalDuration by timeStep).foreach { atTime =>
            animation.draw(atTime, totalDuration)
            canvas.show()
            Thread.sleep(timeStep)
            canvas.clear()
          }
        }
        case _ => throw AnimationTimeException("Root animation has to have exact duration given.")
      }
    } finally {
      canvas.close()
    }
  }
}
