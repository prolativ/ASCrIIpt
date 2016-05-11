package ascriipt.lang.interpreter

import ascriipt.lang.ast._

class BasicInterpreter {
    def eval(expression: Expression)(implicit varScope: VarScope): Any = expression match {
        case IntConst(value) => value
        case StringConst(value) => value
        case Variable(name) => varScope.resolveVar(name)

        case CommandCall(signature, arguments) =>
            val evaluatedArguments = arguments.map(eval)
            val result = evaluatedArguments.collectFirst{
                case AscriiptObject(varScope, commandScope) if commandScope.handles(signature) =>
                    commandScope.call(signature, evaluatedArguments)
            }
            result.getOrElse(throw EvaluationException(expression))

        case ExpressionWithBindings(assignments, expression) =>
            val newScope = assignments.foldLeft[VarScope](varScope.newChildScope){
                case (tmpScope, Assignment(variable, value)) =>
                    tmpScope.assign(variable.name, eval(value)(tmpScope))
            }
            eval(expression)(newScope)


        case ObjectBody(assignments, commandDefs) =>
            val objectScope = assignments.foldLeft[VarScope](varScope.newChildScope){
                case (tmpScope, Assignment(variable, value)) =>
                    tmpScope.assign(variable.name, eval(value)(tmpScope))
            }

            val patterns = commandDefs.map{
                case CommandDef(signature, argumentVars, returnVal) =>
                    val function = (arguments: Seq[Any]) => {
                        val scopeWithArgumentBindings = argumentVars.zip(arguments).foldLeft(objectScope) {
                            case (newObjectScope, (Variable(varName), argumentVal)) => newObjectScope.assign(varName, argumentVal)
                        }
                        eval(returnVal)(scopeWithArgumentBindings)
                    }
                    signature -> function
            }.toMap

            AscriiptObject(objectScope, CommandScope(patterns))

        case ListObject(elements) => elements.map(eval)


        case Addition(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal + rightVal
            case (leftVal: String, rightVal: String) => leftVal + rightVal
            case _ => throw EvaluationException(expression)
        }
        case Substraction(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal - rightVal
            case _ => throw EvaluationException(expression)
        }
        case Multiplication(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal * rightVal
            case _ => throw EvaluationException(expression)
        }
        case Division(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal / rightVal
            case _ => throw EvaluationException(expression)
        }
        case UnaryMinus(value) => eval(value) match {
            case numVal: Int => -numVal
            case _ => throw EvaluationException(expression)
        }
    }
}

object BasicInterpreter {
    val defaultBaseVarScope = RootScope(Map[String, Any]())
}
