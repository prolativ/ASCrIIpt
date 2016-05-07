package ascriipt.lang.interpreter

import ascriipt.lang.ast._

class BasicInterpreter {
    def eval(expression: Expression)(implicit context: EvaluationContext): Any = expression match {
        case IntConst(value) => value
        case StringConst(value) => value
        case Variable(name) => context.scope.resolveVar(name)
        case Add(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal + rightVal
            case _ => throw EvaluationException(expression)
        }
        case Substract(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal - rightVal
            case _ => throw EvaluationException(expression)
        }
        case Multiply(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal * rightVal
            case _ => throw EvaluationException(expression)
        }
        case Divide(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal / rightVal
            case _ => throw EvaluationException(expression)
        }
    }
}
