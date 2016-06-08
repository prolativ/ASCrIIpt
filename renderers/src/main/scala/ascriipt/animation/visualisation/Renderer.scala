package ascriipt.animation.visualisation

import jline.console.ConsoleReader

import scala.util.Random

class Renderer extends RenderHook {
  val consoleReader = new ConsoleReader(System.in, System.out)

  override def onRender(screen: Screen): Unit = {
    consoleReader.clearScreen()
    for (line <- screen.getArray) {
      consoleReader.println(line.mkString(""))// + " " + new Random().nextPrintableChar())
    }
    consoleReader.flush()
  }
}