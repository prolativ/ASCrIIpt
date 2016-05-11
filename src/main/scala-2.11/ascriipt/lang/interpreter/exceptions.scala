package ascriipt.lang.interpreter

import ascriipt.common.AscriiptException
import ascriipt.lang.ast.Expression

abstract class InterpreterException extends AscriiptException {

}

case class DoubleBindingException(varName: String, scope: VarScope) extends InterpreterException {
    override def message = s"Variable '$varName' bound twice in scope $scope"
}

case class VariableNotResolvedException(varName: String, scope: VarScope) extends InterpreterException {
    override def message = s"Variable '$varName' cannot be resolved in scope $scope"
}

case class EvaluationException(expression: Expression) extends InterpreterException {
    override def message = s"Cannot evaluate expression $expression"
}