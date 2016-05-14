package ascriipt.lang.interpreter

import ascriipt.common.AscriiptException
import ascriipt.lang.ast.AstNode

abstract class InterpreterException extends AscriiptException {

}

case class DoubleBindingException(varName: String, scope: VarScope) extends InterpreterException {
    override def message = s"Variable '$varName' bound twice in scope $scope"
}

case class VariableNotResolvedException(varName: String, scope: VarScope) extends InterpreterException {
    override def message = s"Variable '$varName' cannot be resolved in scope $scope"
}

case class ModuleNotResolvedException(moduleId: String) extends InterpreterException {
    override def message = s"Module '$moduleId' cannot be resolved"
}

case class EvaluationException(astNode: AstNode) extends InterpreterException {
    override def message = s"Cannot evaluate AST node $astNode"
}