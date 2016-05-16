package ascriipt.lang.parser

import java.io.FileReader

import ascriipt.lang.common.{UntypedArgumentSlot, PatternWord, CommandSignature, SignatureElement}
import ascriipt.lang.interpreter.AscriiptModule

import scala.util.parsing.combinator.JavaTokenParsers
import ascriipt.lang.ast._

import scala.util.parsing.input.CharSequenceReader

class AscriiptParser extends JavaTokenParsers {

  //private val newLine = sys.props("line.separator")

  override def ident = """[a-zA-Z][a-zA-Z0-9_]*""".r //"""[a-zA-Z]|(?:[a-zA-Z_][a-zA-Z0-9_])+""".r
  //override protected val whiteSpace = """ |\t""".r

  val variableMarker = "$"
  val assignmentOper = "="
  val listSeparator = ","
  val leftParen = "("
  val rightParen = ")"
  val leftBrace = "{"
  val rightBrace = "}"
  val signatureArgumentMarker = "_"
  val statementSeparator = ";"
  val importKeyword = "import"
  val importFromKeyword = "from"

  def programModule: Parser[ModuleBody] = {
    rep(importStatement <~ statementSeparator) ~
        rep(assignment <~ statementSeparator) ~ rep(commandDef <~ statementSeparator) ^^ {
      case importStatements ~ assignments ~ commandDefs =>
        ModuleBody(importStatements, assignments, commandDefs)
    }
  }

  def objectDef: Parser[ObjectBody] = leftBrace ~> objectBody <~ rightBrace

  def objectBody: Parser[ObjectBody] = {
    (
        //(repsep(assignment, statementSeparator) <~ opt(statementSeparator)) ~
        //(repsep(commandDef, statementSeparator) <~ opt(statementSeparator))
        rep(assignment <~ statementSeparator) ~ rep(commandDef <~ statementSeparator)
        ) ^^ { case assignments ~ commandDefs => ObjectBody(assignments, commandDefs) }
  }

  def importStatement: Parser[ImportStatement] = /*selectiveImport |*/ moduleImport

  /*def selectiveImport: Parser[SelectiveImport] = {
      (importFromKeyword -> moduleName) ~ (importKeyword ~> commandPattern)
  }*/
  def moduleImport: Parser[ModuleImport] = importKeyword ~> moduleId ^^ ModuleImport

  def moduleId = quotedString

  def assignment: Parser[Assignment] = variable ~ (assignmentOper ~> expression) ^^ {
    case variable ~ expression => Assignment(variable, expression)
  }

  def emptyList: Parser[ListObject] = listSeparator ^^^ ListObject(List())

  def oneElementList: Parser[ListObject] = listElement <~ listSeparator ^^ (expr => ListObject(List(expr)))

  def mulitElementList: Parser[ListObject] = {
    listElement ~ listSeparator ~ rep1sep(listElement, listSeparator) <~ opt(listSeparator) ^^ {
      case elem ~ _ ~ elems => ListObject(elem +: elems)
    }
  }

  def list: Parser[ListObject] = mulitElementList | oneElementList | emptyList

  def expression = list | listElement

  def listElement: Parser[Expression] = commandCall | argumentlikeExpression

  def argumentlikeExpression: Parser[Expression] = additiveExpression

  def additiveExpression: Parser[Expression] = chainl1(multiplicativeExpression, "+" ^^^ Addition | "-" ^^^ Substraction)

  def multiplicativeExpression: Parser[Expression] = chainl1(atomicExpression, "*" ^^^ Multiplication | "/" ^^^ Division)

  def atomicExpression: Parser[Expression] = (
      intConst |
          stringConst |
          objectDef |
          variable |
          leftParen ~> expression <~ rightParen
      )
  
  def intConst: Parser[Expression] = wholeNumber ^^ (n => IntConst(n.toInt))
  def stringConst: Parser[Expression] = quotedString ^^ StringConst
  def quotedString: Parser[String] = stringLiteral ^^ (_.init.tail)

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

  def expressionWithBindings = rep(assignment) ~ expression ^^ {
    case assignments ~ expr => ExpressionWithBindings(assignments, expr)
  }

  def signaturePatternWord = ident ^^ (id => (PatternWord(id), None))

  def signatureArgument = argumentlikeExpression ^^ (expr => (UntypedArgumentSlot, Some(expr)))

  def signatureVariable = variable ^^ (varId => (UntypedArgumentSlot, Some(varId)))

  def signatureCallElement = signaturePatternWord | signatureArgument

  def signatureDefElement = signaturePatternWord | signatureVariable

  def commandCall: Parser[CommandCall] = {
    def mkSignatureWithArgs(slots: Seq[(SignatureElement, Option[Expression])]): (CommandSignature, Seq[Expression]) = {
      val keywords = slots.map(_._1)
      val arguments = slots.flatMap(_._2)
      (CommandSignature(keywords), arguments)
    }

    def signatureWithArgs = {
      (
        signatureCallElement ~ rep1(signatureCallElement) ^^ {
          case elem ~ elems => mkSignatureWithArgs(elem +: elems)
        } | signaturePatternWord ^^ { elem => mkSignatureWithArgs(Seq(elem)) }
       )
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
        signatureDefElement ~ rep1(signatureDefElement) ^^ {
          case elem ~ elems => mkSignatureWithArgs(elem +: elems)
        } | signaturePatternWord ^^ { elem => mkSignatureWithArgs(Seq(elem)) }
      )
    }

    signatureWithArgs ~ assignmentOper ~ expression ^^ {
      case (signature, arguments) ~ _ ~ returnResult => CommandDef(signature, arguments, returnResult)
    }
  }

  def variable: Parser[Variable] = variableMarker ~> ident ^^ Variable


  def parseModule(input: FileReader): ParseResult[AstNode] = parseAll(phrase(programModule), input)

  def parseModule(input: String): ParseResult[AstNode] = parseAll(phrase(programModule), input)

  def parseHappy[T](parser: Parser[T], input: String): T = {
    val phraseParser = phrase(parser)
    val reader = new CharSequenceReader(input)
    phraseParser(reader) match {
      case Success(t, _) => t
      case NoSuccess(msg, inputLeft) => throw new IllegalArgumentException(
        s"Could not parse '$input': $msg")
    }
  }
}
