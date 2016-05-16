package ascriipt.lang.interpreter

import ascriipt.animation.model.Animation
import ascriipt.animation.visualisation.FakeCanvas

object AscriiptApp extends App {

  args match {
    case Array("eval", expressionStr: String) =>
      val interpreter = BasicInterpreter()
      interpreter.eval(expressionStr) match {
        case animation: Animation =>
          println(animation)
          FakeCanvas.drawAnimation(animation)
        case result =>
          println(result)
      }

    case Array("eval", "-f", basePath: String, expressionStr: String) =>
      val interpreter = BasicInterpreter(basePath)
      interpreter.eval(expressionStr) match {
        case animation: Animation =>
          println(animation)
          FakeCanvas.drawAnimation(animation)
        case result =>
          println(result)
      }

    case _ =>
      println(s"ASCrIIpt cannot handle arguments: ${args.mkString(", ")}")
  }
}
