package ascriipt.lang.interpreter

import ascriipt.common.AscriiptException
import ascriipt.lang.ast.AstNode

abstract class InterpreterException(message: String) extends AscriiptException(message) {

}

case class DoubleBindingException(varName: String, scope: VarScope) extends {
  val message = s"Variable '$varName' bound twice in scope $scope"
} with InterpreterException(message)

case class VariableNotResolvedException(varName: String, scope: VarScope) extends {
  val message = s"Variable '$varName' cannot be resolved in scope $scope"
} with InterpreterException(message)

case class ModuleNotResolvedException(moduleId: String) extends {
  val message = s"Module '$moduleId' cannot be resolved"
} with InterpreterException(message)

case class EvaluationException(
                              astNode: AstNode
                              )(implicit varScope: VarScope, commandScope: CommandScope) extends {
  val message = s"Cannot evaluate AST node $astNode in variable scope $varScope and command scope $commandScope"
} with InterpreterException(message)