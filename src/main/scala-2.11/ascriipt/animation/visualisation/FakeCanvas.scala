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
    val sequence = ParallelAnimation(Seq(
      TimedWaiting(6500),
      SequentialAnimation(Seq(
      ParallelAnimation(Seq(
        TimedWaiting(3000),
        new AsciiPoint('O', (20, 20)) with MovementByVector {val dx= -20; val dy= -20}
      )),
      TimedWaiting(500),
      ParallelAnimation(Seq(
        TimedWaiting(3000),
        new AsciiPoint('O') with MovementByVector {val dx=20; val dy=20; override val baseDuration=MinimalDuration(10)}
      ))
    )),
      new AsciiPoint('M', (5, 10)) with Still,
      new AsciiPoint('X', (40, 24)) with Still,
      new AsciiImage(Array(Array('z', 'x'), Array('c', 'b')), (1, 1)) with Still,
      new AsciiImage(Array(Array('z', 'x'), Array('c', 'b')), (6, 14)) with MovementByVector {val dx=20; val dy=15}
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
