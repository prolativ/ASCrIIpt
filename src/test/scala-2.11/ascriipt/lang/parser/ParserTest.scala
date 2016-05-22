package ascriipt.lang.parser

import ascriipt.lang.ast._
import ascriipt.lang.common.{UntypedArgumentSlot, PatternWord, CommandSignature}
import org.scalatest._
import Matchers._
import ascriipt.lang.common.SignatureHelpers._

import scala.util.parsing.input.CharSequenceReader

class ParserTest extends AscriiptParser with FlatSpecLike {
    behavior of "AscriiptParser"

    private def parsing[T](s:String)(implicit p:Parser[T]):T = {
        val phraseParser = phrase(p)
        val input = new CharSequenceReader(s)
        phraseParser(input) match {
            case Success(t,_)     => t
            case NoSuccess(msg, inputLeft) => throw new IllegalArgumentException(
                s"Could not parse '$s': $msg")
        }
    }

    it should "parse an identifier" in {
        implicit val testedParser = ident
        val input = """abcd"""

        parsing(input) shouldEqual input
    }

    it should "parse an variable" in {
        implicit val testedParser = expression
        val input = """$a"""

        parsing(input) shouldEqual Variable("a")
    }

    it should "parse a string" in {
        implicit val testedParser = expression
        val input = " \"abc123\" "

        parsing(input) shouldEqual StringConst("abc123")
    }

    it should "parse an binary expression" in {
        implicit val testedParser = expression
        val input = """4 - 2"""

        parsing(input) shouldEqual Substraction(IntConst(4), IntConst(2))
    }

    it should "parse a complex expression" in {
        implicit val testedParser = expression
        val input = """2 + 3 * 4"""

        parsing(input) shouldEqual Addition(IntConst(2), Multiplication(IntConst(3), IntConst(4)))
    }

    it should "parse a command call" in {
        implicit val testedParser = commandCall

        parsing("foo") shouldEqual CommandCall(
            CommandSignature(Seq(PatternWord("foo"))),
            Seq()
        )

        parsing("foo $a") shouldEqual CommandCall(
            CommandSignature(Seq(PatternWord("foo"), UntypedArgumentSlot)),
            Seq(Variable("a"))
        )

        parsing("$a foo") shouldEqual CommandCall(
            CommandSignature(Seq(UntypedArgumentSlot, PatternWord("foo"))),
            Seq(Variable("a"))
        )

        parsing("$a foo $b") shouldEqual CommandCall(
            CommandSignature(Seq(UntypedArgumentSlot, PatternWord("foo"), UntypedArgumentSlot)),
            Seq(Variable("a"), Variable("b"))
        )
    }

    it should "parse a trivial command def" in {
        implicit val testedParser = commandDef

        parsing("foo $a = 1") shouldEqual CommandDef(
            CommandSignature(Seq(PatternWord("foo"), UntypedArgumentSlot)),
            Seq(Variable("a")),
            IntConst(1)
        )
    }

    it should "parse a command def using a var" in {
        implicit val testedParser = commandDef

        parsing("foo $a = $a") shouldEqual CommandDef(
            CommandSignature(Seq(PatternWord("foo"), UntypedArgumentSlot)),
            Seq(Variable("a")),
            Variable("a")
        )
    }

    it should "parse a command def calling another command" in {
        implicit val testedParser = commandDef

        parsing("foo $a = (bar 1)") shouldEqual CommandDef(
            CommandSignature(Seq(PatternWord("foo"), UntypedArgumentSlot)),
            Seq(Variable("a")),
            CommandCall(
                CommandSignature(Seq(PatternWord("bar"), UntypedArgumentSlot)),
                Seq(IntConst(1))
            )
        )
    }

    it should "parse a variable assignment" in {
        implicit val testedParser = assignment

        parsing("$a = 1") shouldEqual Assignment(
          Variable("a"),
          IntConst(1)
        )

      parsing("$a = foo 1") shouldEqual Assignment(
        Variable("a"),
        CommandCall(
            CommandSignature(Seq("foo", __)),
            Seq(IntConst(1))
        )
      )
    }
}
