package ascriipt.lang.interpreter

import java.io.File

import ascriipt.lang.parser.AscriiptParser

class BasicInterpreter extends AscriiptInterpreter {
  val parser = new AscriiptParser
  lazy val evaluator: AscriiptEvaluator = new BasicEvaluator(generalDependencyResolver)
  lazy val nativeDependencyResolver: NativeDependencyResolver = new NativeDependencyResolver(parser, evaluator)
  lazy val dependencySubresolvers = Seq(LangStdDependencyResolver, nativeDependencyResolver)
  lazy val generalDependencyResolver: GeneralDependencyResolver = {
    new GeneralDependencyResolver(dependencySubresolvers: _*)
  }

  override def eval(expressionStr: String, currentFile: File): Any = {
    val ast = parser.parseHappy(parser.expression, expressionStr)
    implicit val scriptFile = currentFile
    implicit val varScope = RootScope.empty
    implicit val staticCommandScope = CommandScope(
      generalDependencyResolver.implicitlyAvailableCommands ++ nativeDependencyResolver.commandsFromModuleFile(currentFile)
    )
    evaluator.eval(ast)
  }
}
