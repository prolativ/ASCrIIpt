package ascriipt.lang.ast

import ascriipt.lang.common.CommandSignature

sealed trait AstNode

sealed trait Expression extends AstNode

case class IntConst(value: Int) extends Expression

case class StringConst(value: String) extends Expression

case class Variable(name: String) extends Expression

case class CommandCall(signature: CommandSignature, arguments: Seq[Expression]) extends Expression

case class CommandDef(signature: CommandSignature, arguments: Seq[Variable], returnVal: Expression) extends AstNode

case class Assignment(variable: Variable, value: Expression) extends AstNode

case class ExpressionWithBindings(assignments: Seq[Assignment], expression: Expression) extends Expression

case class ListObject(elements: Seq[Expression]) extends Expression

case class ObjectBody(bindings: Seq[Assignment], commandDefs: Seq[CommandDef]) extends Expression

sealed trait ImportStatement

//case class SelectiveImport(moduleName: String, commandPattern: String) extends ImportStatement

case class ModuleImport(moduleName: String) extends ImportStatement

case class ModuleBody(commandImports: Seq[ImportStatement], bindings: Seq[Assignment], commandDefs: Seq[CommandDef])
    extends AstNode


//sealed trait MathExpression extends Expression

case class Addition(left: Expression, right: Expression) extends Expression

case class Substraction(left: Expression, right: Expression) extends Expression

case class Multiplication(left: Expression, right: Expression) extends Expression

case class Division(left: Expression, right: Expression) extends Expression

case class UnaryMinus(value: Expression) extends Expression


