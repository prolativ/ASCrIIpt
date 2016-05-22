package ascriipt.animation.visualisation

import ascriipt.animation.model._
import com.googlecode.lanterna.TerminalFacade
import jline.console.ConsoleReader

class FakeScreen(width: Int, height: Int) extends Screen {
  var canvas: Array[Array[Char]] = Array()
  val consoleReader = new ConsoleReader(System.in, System.out)
  clear()

  override def clear(): Unit = canvas = Array.fill(height, width)(' ')

  override def setChar(point: (Int, Int), char: Char): Unit = {
    val (x, y) = point
    if (x >= 0 && x < width && y >= 0 && y < height) {
      canvas(y)(x) = char
    }
  }

  override def show(): Unit = {
    consoleReader.clearScreen()
    for (line <- canvas) {
      consoleReader.println(line)
    }
    consoleReader.flush()
  }
}

object FakeScreen {
  def main(args: Array[String]) {
    val sequence = ParallelAnimation(Seq(
      TimedWaiting(6500),
      SequentialAnimation(Seq(
        ParallelAnimation(Seq(
          TimedWaiting(3000),
          MovementByVector(AsciiPoint(20, 20, 'O'), -20, -20)
        )),
        TimedWaiting(500),
        ParallelAnimation(Seq(
          TimedWaiting(3000),
          MovementByVector(AsciiPoint(0, 0, 'O'), 20, 20)
        ))
      )),
        AsciiPoint(5, 10, 'M'),
        AsciiPoint(40, 24, 'X'),
        AsciiImage(10, 10, Array(Array('z', 'x'), Array('c', 'b'))),
        MovementByVector(AsciiImage(2, 2, Array(Array('z', 'x'), Array('c', 'b'))), 20, 15)
    ))
    drawAnimation(sequence)
  }

  def drawAnimation(animation: Animation): Unit = {
    implicit val canvas = new FakeScreen(60, 25)

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
  }
}
