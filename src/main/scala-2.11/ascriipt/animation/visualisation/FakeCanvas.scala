package ascriipt.animation.visualisation

import ascriipt.animation.model._
import com.googlecode.lanterna.TerminalFacade
import jline.console.ConsoleReader

class FakeCanvas(width: Int, height: Int) extends Canvas {
  var canvas: Array[Array[Char]] = Array()
  val screen = TerminalFacade.createScreen()
  screen.startScreen()
  clear()

  override def clear(): Unit = canvas = Array.fill(height, width)(' ')

  override def drawChar(startingPoint: (Int, Int), char: Char): Unit = {
    val (x, y) = startingPoint
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

object FakeCanvas {
  def main(args: Array[String]): Unit = {
    val c: ConsoleReader = new ConsoleReader(System.in, System.out)
    while(true) {
      c.println("asvdsfas")
      c.println("asvdsfas")
      c.println("asvdsfas")
      c.flush()
      Thread.sleep(500)
      c.clearScreen()
      c.flush()
      Thread.sleep(500)

      c.println("zxcasd")
      c.println("!@!@#")
      c.flush()
      Thread.sleep(500)
    }

  }

  def drawAnimation(animation: Animation): Unit = {
    implicit val canvas = new FakeCanvas(60, 25)

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
