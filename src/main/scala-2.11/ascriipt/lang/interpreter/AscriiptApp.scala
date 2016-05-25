package ascriipt.lang.interpreter

import java.io.File

import ascriipt.animation.model.Animation
import ascriipt.animation.visualisation.FakeScreen

object AscriiptApp extends App {

  args match {
    case Array("eval", expressionStr: String) =>
      val file = new File(System.getProperty("user.dir"))
      evalExpression(expressionStr, file)

    case Array("eval", "-f", basePath: String, expressionStr: String) =>
      val file = new File(basePath)
      evalExpression(expressionStr, file)

    case _ =>
      println(s"ASCrIIpt cannot handle arguments: ${args.mkString(", ")}")
  }

  private def evalExpression(expressionStr: String, file: File) = {
    val interpreter = new BasicInterpreter
    interpreter.eval(expressionStr, file) match {
      case animation: Animation =>
        println(animation)
        FakeScreen.drawAnimation(animation)
      case result =>
        println(result)
    }
  }
}
