package ascriipt.lang.parser

import org.scalatest._
import Matchers._

import scala.util.parsing.input.CharSequenceReader

class ParserTest extends AscriiptParser with FlatSpecLike{
    behavior of "AscriiptParser"

    private def parsing[T](s:String)(implicit p:Parser[T]):T = {
        //wrap the parser in the phrase parse to make sure all input is consumed
        val phraseParser = phrase(p)
        //we need to wrap the string in a reader so our parser can digest it
        val input = new CharSequenceReader(s)
        phraseParser(input) match {
            case Success(t,_)     => t
            case NoSuccess(msg,_) => throw new IllegalArgumentException(
                "Could not parse '" + s + "': " + msg)
        }
    }

    it should "parse an identifier" in {
        implicit val testedParser = ident
        val input = """abcd"""

        parsing(input) shouldEqual input
    }
}
