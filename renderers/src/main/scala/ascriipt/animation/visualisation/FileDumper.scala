package ascriipt.animation.visualisation

import java.io.{FileWriter, BufferedWriter, File}

class FileDumper extends RenderHook {
  val file = new File("output.txt")
  val bufferedWriter = new BufferedWriter(new FileWriter(file))

  override def onRender(screen: Screen): Unit = {
    for(line <- screen.getArray) {
      bufferedWriter.write(line.mkString(""))
      bufferedWriter.newLine()
    }
    bufferedWriter.write("=================")
    bufferedWriter.newLine()
  }
}
