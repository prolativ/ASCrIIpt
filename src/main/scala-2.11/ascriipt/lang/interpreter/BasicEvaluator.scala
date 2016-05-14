package ascriipt.lang.interpreter

import ascriipt.lang.ast._
import ascriipt.lang.common.Command

class BasicEvaluator(
                      dependencyResolver: => DependencyResolver
                          ) extends AscriiptEvaluator {
    def eval(astNode: AstNode)(implicit varScope: VarScope, staticCommandScope: CommandScope): Any = astNode match {
        case IntConst(value) => value
        case StringConst(value) => value
        case Variable(name) => varScope.resolveVar(name)

        case CommandCall(signature, arguments) =>
            val evaluatedArguments = arguments.map(eval)

            println(s"Calling $signature")
            println(s"in command scope ${staticCommandScope.commands}")

            if(staticCommandScope.handles(signature)) {
                staticCommandScope.call(signature, evaluatedArguments)
            } else {
                evaluatedArguments.collectFirst{
                    case AscriiptObject(varScope, commandScope) if commandScope.handles(signature) =>
                        commandScope.call(signature, evaluatedArguments)
                }.getOrElse(throw EvaluationException(astNode))
            }

        case ExpressionWithBindings(assignments, expression) =>
            val newScope = evalAssignments(assignments)
            eval(expression)(newScope, staticCommandScope)

        case ModuleBody(commandImports, assignments, commandDefs) =>
            val importedCommandScope = resolveImports(commandImports)
            val innerVarScope = evalAssignments(assignments)
            val definedCommandScope = evalCommandDefs(commandDefs)(innerVarScope, staticCommandScope ++ importedCommandScope)
            AscriiptModule(definedCommandScope)


        case ObjectBody(assignments, commandDefs) =>
            val innerVarScope = evalAssignments(assignments)
            val definedCommandScope = evalCommandDefs(commandDefs)(innerVarScope, staticCommandScope)
            AscriiptObject(innerVarScope, definedCommandScope)

        case ListObject(elements) => elements.map(eval)


        case Addition(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal + rightVal
            case (leftVal: String, rightVal: String) => leftVal + rightVal
            case _ => throw EvaluationException(astNode)
        }
        case Substraction(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal - rightVal
            case _ => throw EvaluationException(astNode)
        }
        case Multiplication(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal * rightVal
            case _ => throw EvaluationException(astNode)
        }
        case Division(left, right) => (eval(left), eval(right)) match {
            case (leftVal: Int, rightVal: Int) => leftVal / rightVal
            case _ => throw EvaluationException(astNode)
        }
        case UnaryMinus(value) => eval(value) match {
            case numVal: Int => -numVal
            case _ => throw EvaluationException(astNode)
        }
    }

    def evalExternal(astNode: AstNode): Any = {
        implicit val varScope = RootScope.empty
        implicit val staticCommandScope = CommandScope(dependencyResolver.implicitlyAvailableCommands)
        eval(astNode)
    }

    private def evalAssignments(
                                   assignments: Seq[Assignment]
                                   )(implicit varScope: VarScope, staticCommandScope: CommandScope): VarScope = {
        assignments.foldLeft[VarScope](varScope.newChildScope){
            case (tmpScope, Assignment(variable, value)) =>
                tmpScope.assign(variable.name, eval(value)(tmpScope, staticCommandScope))
        }
    }

    private def resolveImports(imports: Seq[ImportStatement]): CommandScope = {
        val importedCommands = imports.flatMap{
            case ModuleImport(moduleName) =>
                dependencyResolver.commandsByModuleId(moduleName)
        }
        CommandScope(importedCommands)
    }

    private def evalCommandDefs(
                               commandDefs: Seq[CommandDef]
                               )(varScope: VarScope, importedCommandScope: CommandScope): CommandScope = {
        val commands = commandDefs.map{
            case CommandDef(signature, argumentVars, returnVal) =>
                val operation = (arguments: Seq[Any]) => {
                    val scopeWithArgumentBindings = argumentVars.zip(arguments).foldLeft(varScope) {
                        case (newObjectScope, (Variable(varName), argumentVal)) => newObjectScope.assign(varName, argumentVal)
                    }
                    eval(returnVal)(scopeWithArgumentBindings, importedCommandScope)
                }
                Command(signature, operation)
        }
        CommandScope(commands)
    }

}

object BasicEvaluator {
    val defaultBaseVarScope = RootScope(Map[String, Any]())
}
