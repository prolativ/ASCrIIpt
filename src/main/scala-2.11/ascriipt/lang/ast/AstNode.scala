package ascriipt.lang.ast

sealed trait AstNode

sealed trait Expression extends AstNode

case class IntConst(value: Int) extends Expression

case class StringConst(value: String) extends Expression

sealed trait SignatureElement

case class Variable(name: String) extends Expression with SignatureElement

case class ClassObject(bindings: List[Assignment], commandDefs: List[CommandDef]) extends AstNode

case class ListObject(elements: List[Expression]) extends Expression

case class PatternWord(value: String) extends SignatureElement

case class CommandSignature(patternWords: List[String]) extends AstNode

case class CommandCall(signature: CommandSignature, arguments: List[Expression]) extends Expression

case class CommandDef(signature: CommandSignature, arguments: List[Variable], returnVal: Expression) extends AstNode

case class Assignment(variable: Variable, value: Expression) extends AstNode

//sealed trait MathExpression extends Expression

case class Add(left: Expression, right: Expression) extends Expression

case class Substract(left: Expression, right: Expression) extends Expression

case class Multiply(left: Expression, right: Expression) extends Expression

case class Divide(left: Expression, right: Expression) extends Expression

case class Negate(value: Expression) extends Expression


