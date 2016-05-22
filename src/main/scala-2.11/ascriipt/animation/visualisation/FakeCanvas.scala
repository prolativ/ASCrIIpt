package ascriipt.animation.visualisation

import ascriipt.animation.model._
import jline.console.ConsoleReader

class FakeCanvas(width: Int, height: Int) extends Canvas {
  var canvas: Array[Array[Char]] = Array()
  val consoleReader = new ConsoleReader(System.in, System.out)
  clear()

  override def clear(): Unit = canvas = Array.fill(height, width)(' ')

  override def setChar(point: (Int, Int), char: Char): Unit = {
    val (x, y) = point
    if(x >= 0 && x < width && y >= 0 && y < height) {
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

object FakeCanvas {
  def main(args: Array[String]): Unit = {
    val sequence = SequentialAnimation(Seq(
      TimedWaiting(1000),
      ParallelAnimation(Seq(
        TimedWaiting(300),
        AsciiPointFixedDistanceMovement(0, 0, 4, 4, 'O')
      )),
      TimedWaiting(1000),
      ParallelAnimation(Seq(
        TimedWaiting(300),
        AsciiPointFixedDistanceMovement(4, 4, 0, 0, 'O')
      ))
    ))
    drawAnimation(sequence)

  }

  def drawAnimation(animation: Animation): Unit = {
    implicit val canvas = new FakeCanvas(60, 25)

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
