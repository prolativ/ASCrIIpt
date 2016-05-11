package ascriipt.lang.parser

import scala.util.parsing.combinator.JavaTokenParsers
import ascriipt.lang.ast._

class AscriiptParser extends JavaTokenParsers {

    /*private def rep2sep[A]: Parser[Seq[A]] = {

    }*/

    //private val newLine = sys.props("line.separator")

    override def ident = """[a-zA-Z]+""".r //"""[a-zA-Z]|(?:[a-zA-Z_][a-zA-Z0-9_])+""".r
    //override protected val whiteSpace = """ |\t""".r

    val variableMarker = "$"
    val assignmentOper = "="
    val listSeparator = ","
    val leftParen = "("
    val rightParen = ")"
    val signatureArgumentMarker = "_"
    val statementSeparator = ";"
    val objectKeyword = "object"
    def programModule: Parser[ObjectBody] = objectBody
    def objectDef: Parser[ObjectBody] = objectKeyword ~> leftParen ~> objectBody <~ rightParen
    def objectBody: Parser[ObjectBody] = {(
        //(repsep(assignment, statementSeparator) <~ opt(statementSeparator)) ~
            //(repsep(commandDef, statementSeparator) <~ opt(statementSeparator))
        rep(assignment <~ statementSeparator) ~ rep(commandDef <~ statementSeparator)
        ) ^^ { case assignments ~ commandDefs => ObjectBody(assignments, commandDefs) }
    }

    def assignment: Parser[Assignment] = variable ~ (assignmentOper ~> expression) ^^ {
        case variable ~ expression => Assignment(variable, expression)
    }
    def emptyList: Parser[ListObject] = listSeparator ^^^ ListObject(List())
    def oneElementList: Parser[ListObject] = additiveExpression <~ listSeparator ^^ (expr => ListObject(List(expr)))
    def mulitElementList: Parser[ListObject] = {
        additiveExpression ~ listSeparator ~ rep1sep(additiveExpression, listSeparator) <~ opt(listSeparator) ^^ {
            case elem ~ _ ~ elems => ListObject(elem +: elems)
        }
    }
    def list: Parser[ListObject] = mulitElementList | oneElementList | emptyList

    def expression: Parser[Expression] = commandCall | argumentlikeExpression
    def argumentlikeExpression: Parser[Expression] = list | additiveExpression
    def additiveExpression: Parser[Expression] = chainl1(multiplicativeExpression, "+" ^^^ Addition | "-" ^^^ Substraction)
    def multiplicativeExpression: Parser[Expression] = chainl1(atomicExpression, "*" ^^^ Multiplication | "/" ^^^ Division)
    def atomicExpression: Parser[Expression] = (
        wholeNumber ^^ (n => IntConst(n.toInt)) |
            stringLiteral ^^ (s => StringConst(s)) |
            objectDef |
            variable |
            leftParen ~> argumentlikeExpression <~ rightParen
        )

    /*def expression: Parser[Expression] = (
        wholeNumber ^^ (n => IntConst(n.toInt)) |
            stringLiteral ^^ (s => StringConst(s)) |
            variable |
            binaryOperation |
            commandCall |
            objectDef |
            expressionWithBindings |
            list |
            leftParen ~> expression <~ rightParen
        )*/

    def expressionWithBindings = rep(assignment) ~ additiveExpression ^^ {
        case assignments ~ expr => ExpressionWithBindings(assignments, expr)
    }


    def commandCall: Parser[CommandCall] = {
        def mkSignatureWithArgs(slots: Seq[(SignatureElement, Option[Expression])]): (CommandSignature, Seq[Expression]) = {
            val keywords = slots.map(_._1)
            val arguments = slots.flatMap(_._2)
            (CommandSignature(keywords), arguments)
        }

        def signatureWithArgs = {
            (
                ident ^^ (id => (PatternWord(id), None))
                    | argumentlikeExpression ^^ (expr => (ArgumentSlot, Some(expr)))
                ) ~
            rep1(
                ident ^^ (id => (PatternWord(id), None))
                    | argumentlikeExpression ^^ (expr => (ArgumentSlot, Some(expr)))
            ) ^^ { case elem ~ elems => mkSignatureWithArgs(elem +: elems) }
        }

        signatureWithArgs ^^ {
            case (signature, arguments) => CommandCall(signature, arguments)
        }
    }

    def commandDef: Parser[CommandDef] = {
        def mkSignatureWithArgs(slots: Seq[(SignatureElement, Option[Variable])]): (CommandSignature, Seq[Variable]) = {
            val keywords = slots.map(_._1)
            val arguments = slots.flatMap(_._2)
            (CommandSignature(keywords), arguments)
        }

        def signatureWithArgs = {
            (
                ident ^^ (id => (PatternWord(id), None))
                    | variable ^^ (varId => (ArgumentSlot, Some(varId)))
                ) ~
            rep1(
                ident ^^ (id => (PatternWord(id), None))
                | variable ^^ (varId => (ArgumentSlot, Some(varId)))
            ) ^^ { case elem ~ elems => mkSignatureWithArgs(elem +: elems) }
        }

        signatureWithArgs ~ assignmentOper ~ additiveExpression ^^ {
            case (signature, arguments) ~ _ ~ returnResult => CommandDef(signature, arguments, returnResult)
        }
    }

    def variable: Parser[Variable] = variableMarker ~> ident ^^ Variable
}
