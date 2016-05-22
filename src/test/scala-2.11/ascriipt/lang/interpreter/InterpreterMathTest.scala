package ascriipt.lang.interpreter

import ascriipt.lang.ast.{Addition, IntConst}
import org.scalatest.{Matchers, FlatSpecLike}
import Matchers._

class InterpreterMathTest extends FlatSpecLike {
    behavior of "Interpreter"

    val interpreter = new BasicEvaluator(LangStdDependencyResolver)
    import interpreter.eval
    implicit def staticCommandScope = CommandScope.empty

    trait EmptyScopeContextFixture {
        implicit val varScope = RootScope(Map())
    }

    it should "add 2 integer constants" in new EmptyScopeContextFixture{
        eval(Addition(IntConst(3), IntConst(4))) shouldEqual 7
    }
}
