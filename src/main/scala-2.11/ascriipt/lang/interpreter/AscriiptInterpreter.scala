package ascriipt.lang.interpreter

import java.io.File

trait AscriiptInterpreter {
  def eval(expressionStr: String, currentFile: File): Any
}
