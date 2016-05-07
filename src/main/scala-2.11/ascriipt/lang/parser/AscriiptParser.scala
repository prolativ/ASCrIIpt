package ascriipt.lang.parser

import java.io.Serializable
import scala.util.parsing.combinator.JavaTokenParsers

class AscriiptParser extends JavaTokenParsers {
    def variableMarker = "$"
    def assignmentOper = "="
    def listSeparator = ","
    def leftParen = "("
    def rightParen = ")"
    def objectKeyword = "object"
    /*def program = objectBody
    def objectDef = objectKeyword ~> leftParen ~> objectBody <~ rightParen
    def objectBody = rep(assignment | commandDef)
    def assignment = variable ~ assignmentOper ~> expression
    def emptyList: Parser[List[Expression]] = listSeparator ^^^ List()
    def oneElementList: Parser[List[Expression]] = expression <~ listSeparator ^^ (expr => List(expr))
    def mulitElementList: Parser[List[Expression]] = rep1sep(expression, listSeparator) <~ opt(listSeparator)
    def list: Parser[List] = emptyList | oneElementList | mulitElementList
    def expression: Parser[Expression] = (
        wholeNumber ^^ (n => IntConst(n.toInt) |
            stringLiteral ^^ (s => StringConst(s))) |
            variable |
            comandCall |
            objectDef |
            rep(assignment) ~ expression |
            leftParen ~> expression <~ rightParen

    def commandSignature: Parser[List[String]] = {
        rep1(
            ident ^^
            | variable ^^
        )
    }
    def commandCall: Parser[CommandCall]
    def commandDef: Parser[CommandDef] = rep1(ident | variable) ~ assignmentOper ~> expression
    def variable: Parser[Variable] = variableMarker ~> ident ^^ Variable*/
}
