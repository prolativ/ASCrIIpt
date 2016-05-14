package ascriipt.lang.interpreter

object AscriiptApp extends App {

    args match {
        case Array("eval", expressionStr: String) =>
            val interpreter = BasicInterpreter()
            println(interpreter.eval(expressionStr))
        case Array("eval", "-f", basePath: String, expressionStr: String) =>
            val interpreter = BasicInterpreter(basePath)
            println(interpreter.eval(expressionStr))

        case _ =>
            println(s"ASCrIIpt cannot handle arguments: ${args.mkString(", ")}")
    }
}
