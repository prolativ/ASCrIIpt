package ascriipt.lang.integration

import ascriipt.lang.ast._
import ascriipt.lang.interpreter._
import ascriipt.lang.parser.AscriiptParser
import org.scalatest._
import Matchers._

import scala.util.parsing.input.CharSequenceReader

class IntegrationTest extends AscriiptParser with FlatSpecLike {
    behavior of "AscriiptParser with Interpreter"

    private def parsing[T](s:String)(implicit p:Parser[T]):T = {
        val phraseParser = phrase(p)
        val input = new CharSequenceReader(s)
        phraseParser(input) match {
            case Success(t,_)     => t
            case NoSuccess(msg,_) => throw new IllegalArgumentException(
                s"Could not parse '$s': $msg")
        }
    }

    private val dependencyResolver = LangStdDependencyResolver
    private val evaluator = new BasicEvaluator(dependencyResolver)
    //private implicit val staticCommandScope =

    /*private def evalBlock(s: String): AscriiptObject = {
        implicit val testedParser = objectBody
        implicit val varScope = BasicEvaluator.defaultBaseVarScope

        val parsedExpression = parsing[ObjectBody](s)
        evaluator.eval(parsedExpression) match {
            case ascriiptObject: AscriiptObject => ascriiptObject
            case _ => throw EvaluationException(parsedExpression)
        }
    }

    private def evalExpr(s: String): Any = {
        implicit val testedParser = argumentlikeExpression
        implicit val varScope = BasicEvaluator.defaultBaseVarScope

        val parsedExpression = parsing[Expression](s)
        evaluator.eval(parsedExpression)
    }*/



    /*it should "add 2 variables" in {
        val input =
            """
              |$a = 1;
              |$b = 4;
              |$c = $a + $b;
            """.stripMargin
        val module = evalBlock(input)

        module.varScope.resolveVar("c") shouldEqual 5
    }

    it should "evaluate an empty list" in {
        val input = ""","""
        evalExpr(input) shouldEqual Seq()
    }

    it should "evaluate a one-element list" in {
        val input = """1,"""
        evalExpr(input) shouldEqual Seq(1)
    }

    it should "evaluate a two-element list" in {
        val input = """1, 2"""
        evalExpr(input) shouldEqual Seq(1, 2)
    }


    it should "evaluate a list of expressions" in {
        val input = """
              |$x = 2;
              |$l = 1 , $x + 3, 9;
            """.stripMargin
        val module = evalBlock(input)

        module.varScope.resolveVar("l") shouldEqual Seq(1, 5, 9)
    }

    it should "evaluate an empty module object" in {
        val input = """
                      |object()
                    """.stripMargin

        val result = evalExpr(input)
        result shouldBe a[AscriiptObject]
    }

    it should "evaluate an module object with variables" in {
        val input = """
                      |object(
                      |  $a = 2;
                      |  $b = $a + 1;
                      |)
                    """.stripMargin

        val result = evalExpr(input)
        result shouldBe a[AscriiptObject]
        val ascriiptObject = result.asInstanceOf[AscriiptObject]
        ascriiptObject.varScope.resolveVar("a") shouldEqual 2
        ascriiptObject.varScope.resolveVar("b") shouldEqual 3
    }

    it should "evaluate an module object with command definition" in {
        val input = """
                      |$bar = object(
                      | foo $this = 5;
                      |);
                      |$fooCall = foo $bar;
                    """.stripMargin

        val module = evalBlock(input)
        module.varScope.resolveVar("fooCall") shouldEqual 5
    }*/
}
