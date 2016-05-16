package ascriipt.lang.interpreter

import ascriipt.lang.parser.AscriiptParser

import scala.util.{Failure, Success, Try}

class BasicInterpreter(basePathOpt: Option[String]) extends AscriiptInterpreter {
  val parser = new AscriiptParser
  lazy val evaluator: AscriiptEvaluator = new BasicEvaluator(generalDependencyResolver)
  lazy val nativeDependencyResolverOpt: Option[NativeDependencyResolver] = {
    basePathOpt.map(basePath => new NativeDependencyResolver(parser, evaluator, basePath))
  }
  lazy val dependencySubresolvers = Seq(LangStdDependencyResolver) ++ nativeDependencyResolverOpt
  lazy val generalDependencyResolver: GeneralDependencyResolver = new GeneralDependencyResolver(
    dependencySubresolvers: _*
  )

  override def eval(expressionStr: String): Any = {
    val ast = parser.parseHappy(parser.expression, expressionStr)
    implicit val varScope = RootScope.empty
    implicit val staticCommandScope = CommandScope(
      generalDependencyResolver.implicitlyAvailableCommands ++ nativeDependencyResolverOpt.toSeq.flatMap(_.baseModuleCommands)
    )
    evaluator.eval(ast)
  }
}

object BasicInterpreter {
  def apply() = new BasicInterpreter(None)

  def apply(basePath: String) = new BasicInterpreter(Some(basePath))
}
