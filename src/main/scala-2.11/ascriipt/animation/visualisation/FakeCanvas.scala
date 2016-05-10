package ascriipt.animation.visualisation

import ascriipt.animation.model._
import com.googlecode.lanterna.TerminalFacade

class FakeCanvas(width: Int, height: Int) extends Canvas {
  var canvas: Array[Array[Char]] = Array()
  val screen = TerminalFacade.createScreen()
  screen.startScreen()
  clear()

  override def clear(): Unit = canvas = Array.fill(height, width)('.')

  override def drawChar(x: Int, y: Int, char: Char): Unit =
    if (y < height && x < width)
      canvas(y)(x) = char

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
  def main(args: Array[String]) {
    val image = Array(
      Array('\0', '\0', '-', '-', '\0', '\0'),
      Array('\0', '/', '\0', '\0', '\\', '\0'),
      Array('|', '\0', '\0', '\0', '\0', '|'),
      Array('|', '\0', '\0', '\0', '\0', '|'),
      Array('\0', '\\', '\0', '\0', '/', '\0'),
      Array('\0', '\0', '-', '-', '\0', '\0')
    )
//    val sequence = ParallelAnimation(Seq(
//      TimedWaiting(10),
//      AsciiPointFixedDistanceMovement(0, 0, 4, 4, 'O'),
//      AsciiPointFixedDistanceMovement(8, 8, -7, -7, 'X'),
//      AsciiPointFixedDistanceMovement(1, 2, 3, 7, 'A'),
//      AsciiImageMovement(4, 4, 10, 10, '.', new AsciiImage(image))
//    ))

    val sequence = SequentialAnimation(Seq(
      TimedWaiting(3),
      ParallelAnimation(Seq(
        TimedWaiting(4),
        AsciiPointFixedDistanceMovement(0, 0, 4, 4, 'O')
      )),
      TimedWaiting(3),
      ParallelAnimation(Seq(
        TimedWaiting(4),
        AsciiPointFixedDistanceMovement(4, 4, -5, -5, 'O')
      ))
    ))

    val seq1 = ParallelAnimation(Seq(
      TimedWaiting(10),
      SequentialAnimation(Seq(
        AsciiPointFixedDistanceMovement(4, 4, -5, -5, 'O')
      ))
    ))

    val seq2 = ParallelAnimation(Seq(
      TimedWaiting(20),
      SequentialAnimation(Seq(
        ParallelAnimation(Seq(
          TimedWaiting(7),
          AsciiPointFixedDistanceMovement(0, 0, 4, 4, 'O')
        )),
        UntimedWaiting,
        ParallelAnimation(Seq(
          TimedWaiting(7),
          AsciiPointFixedDistanceMovement(4, 4, -5, -5, 'O')
        ))
      ))
    ))

    val seq3 = ParallelAnimation(Seq(
      TimedWaiting(20),
      AsciiImageMovement(0, 0, 8, 20, '.', new AsciiImage(image)),
      AsciiImageMovement(20, 0, -8, 20, '.', new AsciiImage(image)),
      AsciiImageMovement(0, 54, 8, -20, '.', new AsciiImage(image)),
      AsciiImageMovement(20, 54, -8, -20, '.', new AsciiImage(image))

    ))


//    drawAnimation(sequence)
//    drawAnimation(seq1)
//    drawAnimation(seq2)
    drawAnimation(seq3)
  }

  def drawAnimation(animation: Animation): Unit = {
    implicit val canvas = new FakeCanvas(60, 25)

    while (true) {
      animation.baseDuration match {
        case ExactDuration(totalDuration) => {
          val timeStep = 1
          (0L until totalDuration by timeStep).foreach { atTime =>
            animation.draw(atTime, totalDuration)
            canvas.show()
            Thread.sleep(200)
            canvas.clear()
          }
        }
        case _ => throw AnimationTimeException("Root animation has to have exact duration given.")
      }
    }
  }
}
