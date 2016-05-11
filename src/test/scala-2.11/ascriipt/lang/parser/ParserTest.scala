package ascriipt.lang.parser

import ascriipt.lang.ast._
import org.scalatest._
import Matchers._

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

    it should "parse an binary expression" in {
        implicit val testedParser = expression
        val input = """4 - 2"""

        parsing(input) shouldEqual Substraction(IntConst(4), IntConst(2))
    }

    it should "parse a command call" in {
        implicit val testedParser = commandCall
        val input = """foo $a"""

        parsing(input) shouldEqual CommandCall(
            CommandSignature(Seq(PatternWord("foo"), ArgumentSlot)),
            Seq(Variable("a"))
        )
    }
}
